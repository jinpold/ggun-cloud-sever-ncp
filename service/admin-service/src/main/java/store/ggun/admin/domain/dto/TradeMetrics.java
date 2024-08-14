package store.ggun.admin.domain.dto;

public class TradeMetrics {
    private final double totalAmount;  // 거래 총액
    private final long transactionCount;  // 거래 건수 (주문 수량의 총합)

    // 생성자
    public TradeMetrics(double totalAmount, long transactionCount) {
        this.totalAmount = totalAmount;
        this.transactionCount = transactionCount;
    }

    // Getter 메서드
    public double getTotalAmount() {
        return totalAmount;
    }

    public long getTransactionCount() {
        return transactionCount;
    }
}
