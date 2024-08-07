package store.ggun.admin.repository.dao;
import store.ggun.admin.domain.dto.AdminTransactionDto;

import java.util.List;
import java.util.Map;


public interface AdminTransactionDao {

    Long countAllTransactions();

    List<AdminTransactionDto> getAllTransactions();

    Map<String, Double> getTotalByDate();

    Map<String, Map<String, Integer>> getQuantityByDate();

    Long getTransactionsById();

    List<String> getTransactionsByUsername();

    Map<String, Double> getNetProfitByDate();

    List<AdminTransactionDto> getTransactionsByNetProfit();

}

