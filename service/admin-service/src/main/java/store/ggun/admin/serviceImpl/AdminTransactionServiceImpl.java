package store.ggun.admin.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.dto.AdminTransactionDto;
import store.ggun.admin.domain.dto.TradeMetrics;
import store.ggun.admin.domain.model.Messenger;
import store.ggun.admin.domain.model.AdminTransactionModel;
import store.ggun.admin.repository.jpa.AdminTransactionRepository;
import store.ggun.admin.service.AdminTransactionService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminTransactionServiceImpl implements AdminTransactionService {

    private final AdminTransactionRepository adminTransactionRepository;

    @Override
    public Messenger save(AdminTransactionDto adminTransactionDto) {
        AdminTransactionModel adminTransactionModel = adminTransactionRepository.save(dtoToEntity(adminTransactionDto));
        return Messenger.builder()
                .message((adminTransactionModel instanceof AdminTransactionModel) ? "SUCCESS" : "FAILURE")
                .build();
    }

    @Override
    public Messenger deleteById(Long id) {
        adminTransactionRepository.deleteById(id);
        return Messenger.builder()
                .message("SUCCESS")
                .build();
    }

    @Override
    public Messenger modify(AdminTransactionDto adminTransactionDto) {
        return adminTransactionRepository.save(dtoToEntity(adminTransactionDto)) instanceof AdminTransactionModel ?
                Messenger.builder()
                        .message("SUCCESS")
                        .build() :
                Messenger.builder()
                        .message("FAILURE")
                        .build();
    }

    @Override
    public List<AdminTransactionDto> findAll() throws SQLException {
        return adminTransactionRepository.getAllTransactions().stream().toList();
    }

    @Override
    public Optional<AdminTransactionDto> findById(Long id) {
        return adminTransactionRepository.findById(id).stream().map(this::entityToDto).findAny();
    }

    @Override
    public Long count() {
        return adminTransactionRepository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return adminTransactionRepository.existsById(id);
    }

    @Override
    public  Map<String, Map<String, Long>> getNetProfitByDate() {
        return adminTransactionRepository.getNetProfitByDate();
    }

    @Override
    public Map<String, Map<String, TradeMetrics>> getTotalByDate() {

        return adminTransactionRepository.getTotalByDate();
    }

    @Override
    public Map<String, Map<String, Integer>> getQuantityByDate() {
        return adminTransactionRepository.getQuantityByDate();
    }

    @Override
    public Map<String, Map<String, Map<String, Integer>>> getProductByDate() {
        return adminTransactionRepository.getProductByDate();
    }

    @Override
    public Long countAllTransactions() {
        return adminTransactionRepository.countAllTransactions();
    }

    @Override
    public Map<String, Map<String, Integer>> getColorByDate() {
        return adminTransactionRepository.getColorByDate();
    }

    @Override
    public Map<String, Integer> getColorByCount() {
        return adminTransactionRepository.getColorByCount();
    }
}
