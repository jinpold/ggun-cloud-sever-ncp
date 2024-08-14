package store.ggun.admin.repository.dao;
import store.ggun.admin.domain.dto.AdminTransactionDto;
import store.ggun.admin.domain.dto.TradeMetrics;

import java.util.List;
import java.util.Map;


public interface AdminTransactionDao {

    Long countAllTransactions();

    List<AdminTransactionDto> getAllTransactions();

    Map<String, Map<String, TradeMetrics>> getTotalByDate();

    Map<String, Map<String, Integer>> getQuantityByDate();

    Map<String, Map<String, Long>> getNetProfitByDate();

    Map<String, Map<String, Map<String, Integer>>> getProductByDate();

    Map<String, Map<String, Integer>> getColorByDate();

    Map<String, Integer> getColorByCount();

}

