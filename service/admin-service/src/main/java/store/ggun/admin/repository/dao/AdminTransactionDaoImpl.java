package store.ggun.admin.repository.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ggun.admin.domain.dto.QAdminTransactionDto;
import store.ggun.admin.domain.dto.AdminTransactionDto;
import store.ggun.admin.domain.dto.TradeMetrics;
import store.ggun.admin.domain.model.QAdminTransactionModel;

import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class AdminTransactionDaoImpl implements AdminTransactionDao {

    private final QAdminTransactionModel transaction = QAdminTransactionModel.adminTransactionModel;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countAllTransactions() {
        return jpaQueryFactory.select(transaction.count())
                .from(transaction)
                .fetchFirst();
    }

    @Override
    public List<AdminTransactionDto> getAllTransactions() {
        return jpaQueryFactory.select(
                        new QAdminTransactionDto(
                                transaction.id,
                                transaction.accountId,
                                transaction.ordDvsnName,
                                transaction.ordDvsnCd,
                                transaction.sllBuyDvsnCd,
                                transaction.pdno,
                                transaction.prdtName,
                                transaction.ordQty,
                                transaction.ccldPrvs,
                                transaction.tradeType,
                                transaction.sellingFee,
                                transaction.sellingTax,
                                transaction.standardFee,
                                transaction.baseTax,
                                transaction.color,
                                transaction.regDate.stringValue(),
                                transaction.modDate.stringValue()))
                .from(transaction)
                .fetch();
    }

    @Override
    public Map<String, Map<String, TradeMetrics>> getTotalByDate() {
        // 문자열을 숫자로 변환
        NumberExpression<Double> sellingFeeAsDouble = Expressions.numberTemplate(Double.class, "CAST({0} AS DOUBLE)", transaction.sellingFee);
        NumberExpression<Double> sellingTaxAsDouble = Expressions.numberTemplate(Double.class, "CAST({0} AS DOUBLE)", transaction.sellingTax);

        // 변환된 숫자 값을 더하기
        NumberExpression<Double> tradeTotalAsDouble = sellingFeeAsDouble.add(sellingTaxAsDouble);

        // 거래 건수 집계
        NumberExpression<Long> transactionCountAsLong = Expressions.numberTemplate(Long.class, "COUNT(1)");

        // 날짜만 추출하기 위한 SQL 함수 사용 (MySQL의 DATE_FORMAT 함수 사용)
        StringExpression regDateAsDate = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", transaction.regDate);

        // 쿼리 실행 및 결과 수집
        List<Tuple> tuples = jpaQueryFactory
                .select(regDateAsDate, transaction.tradeType, tradeTotalAsDouble.sum(), transactionCountAsLong)
                .from(transaction)
                .groupBy(regDateAsDate, transaction.tradeType)
                .fetch();

        // 결과를 Map 형태로 변환
        Map<String, Map<String, TradeMetrics>> result = tuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(regDateAsDate), // 날짜별로 그룹화
                        Collectors.toMap(
                                tuple -> tuple.get(transaction.tradeType),
                                tuple -> new TradeMetrics(
                                        tuple.get(tradeTotalAsDouble.sum()), // 총액
                                        tuple.get(transactionCountAsLong)    // 거래 건수
                                ),
                                (existing, replacement) -> new TradeMetrics(
                                        existing.getTotalAmount() + replacement.getTotalAmount(),
                                        existing.getTransactionCount() + replacement.getTransactionCount()
                                )
                        )
                ));

        // 날짜별로 오름차순 정렬하고, 0 총액은 제외
        return result.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().entrySet()
                                .stream()
                                .filter(e -> e.getValue().getTotalAmount() > 0) // 0보다 큰 총액만 필터링
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue
                                )),
                        (existing, replacement) -> existing,
                        TreeMap::new // 날짜별로 오름차순 정렬
                ));
    }

    @Override
    public Map<String, Map<String, Long>> getNetProfitByDate() {
        String dateFormat = "%Y-%m-%d"; // 날짜 포맷 설정

        return jpaQueryFactory
                .select(
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", transaction.regDate, dateFormat),
                        transaction.tradeType,
                        transaction.sellingFee.sum()
                )
                .from(transaction)
                .groupBy(transaction.regDate, transaction.tradeType)
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(Expressions.stringTemplate("DATE_FORMAT({0}, {1})", transaction.regDate, dateFormat)).toString(),
                        TreeMap::new,
                        Collectors.toMap(
                                tuple -> tuple.get(transaction.tradeType),
                                tuple -> tuple.get(transaction.sellingFee.sum()),
                                Long::sum
                        )
                ));
    }

    @Override
    public Map<String, Map<String, Map<String, Integer>>> getProductByDate() {
        String dateFormat = "%Y-%m-%d"; // 날짜 포맷 설정
        NumberExpression<Integer> ordQtyAsInteger = Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", transaction.ordQty);
        NumberExpression<Integer> totalAmountAsInteger = Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", transaction.ccldPrvs.multiply(transaction.ordQty));

        List<Tuple> results = jpaQueryFactory
                .select(
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", transaction.regDate, dateFormat),
                        transaction.prdtName,
                        ordQtyAsInteger.sum(),
                        totalAmountAsInteger.sum()
                )
                .from(transaction)
                .groupBy(transaction.regDate, transaction.prdtName)
                .orderBy(transaction.regDate.asc())
                .fetch();

        return results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(Expressions.stringTemplate("DATE_FORMAT({0}, {1})", transaction.regDate, dateFormat)).toString(),
                        TreeMap::new,
                        Collectors.groupingBy(
                                tuple -> tuple.get(transaction.prdtName),
                                TreeMap::new,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list -> {
                                            Map<String, Integer> map = new TreeMap<>();
                                            int totalQuantity = list.stream().mapToInt(t -> t.get(ordQtyAsInteger.sum())).sum();
                                            int totalAmount = list.stream().mapToInt(t -> t.get(totalAmountAsInteger.sum())).sum();
                                            map.put("totalQuantity", totalQuantity);
                                            map.put("totalAmount", totalAmount);
                                            return map;
                                        }
                                )
                        )
                ));
    }


    @Override
    public Map<String, Map<String, Integer>> getQuantityByDate() {
        String dateFormat = "%Y-%m-%d"; // 날짜 포맷 설정
        NumberExpression<Integer> ordQtyAsInteger = Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", transaction.ordQty);

        Map<String, Map<String, Integer>> result = jpaQueryFactory
                .select(
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", transaction.regDate, dateFormat),
                        transaction.tradeType,
                        ordQtyAsInteger.sum()
                )
                .from(transaction)
                .groupBy(transaction.regDate, transaction.tradeType)
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(Expressions.stringTemplate("DATE_FORMAT({0}, {1})", transaction.regDate, dateFormat)).toString(),
                        Collectors.toMap(
                                tuple -> tuple.get(transaction.tradeType),
                                tuple -> tuple.get(ordQtyAsInteger.sum()),
                                Integer::sum
                        )
                ));

        return result.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        TreeMap::new
                ));
    }

    @Override
    public Map<String, Map<String, Integer>> getColorByDate() {
        // 날짜 포맷팅을 위한 SQL 함수 사용
        StringExpression formattedDate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", transaction.regDate, "%Y-%m-%d");

        List<Tuple> results = jpaQueryFactory
                .select(
                        formattedDate,
                        transaction.color,
                        Expressions.numberTemplate(Long.class, "COUNT(*)") // 거래 건수를 카운트
                )
                .from(transaction)
                .groupBy(transaction.regDate, transaction.color)
                .fetch();

        return results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(formattedDate).toString(), // 날짜별로 그룹화
                        TreeMap::new, // 날짜별로 정렬된 결과
                        Collectors.toMap(
                                tuple -> tuple.get(transaction.color),
                                tuple -> ((Number) tuple.get(Expressions.numberTemplate(Long.class, "COUNT(*)"))).intValue(),
                                Integer::sum, // 기존 값을 합산
                                TreeMap::new // 색상별로 정렬된 결과
                        )
                ));
    }

    @Override
    public Map<String, Integer> getColorByCount() {
        // 색상별 총 거래 건수를 카운트
        NumberExpression<Long> countExpression = Expressions.numberTemplate(Long.class, "COUNT(*)");

        List<Tuple> results = jpaQueryFactory
                .select(
                        transaction.color,
                        countExpression
                )
                .from(transaction)
                .groupBy(transaction.color)
                .orderBy(countExpression.desc()) // 거래 건수를 기준으로 내림차순 정렬
                .limit(5) // 상위 5개 색상만 가져옴
                .fetch();

        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(transaction.color),
                        tuple -> ((Number) tuple.get(countExpression)).intValue(),
                        Integer::sum // 기존 값을 합산 (없지만 기본으로 설정)
                ));
    }
}
