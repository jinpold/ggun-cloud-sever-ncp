package store.ggun.admin.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.dto.AdminTransactionDto;
import store.ggun.admin.domain.model.AdminMessengerModel;
import store.ggun.admin.domain.model.AdminTransactionModel;
import store.ggun.admin.repository.jpa.AdminTransactionRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminTransactionService implements store.ggun.admin.service.AdminTransactionService {

    private final AdminTransactionRepository adminTransactionRepository;

    @Override
    public AdminMessengerModel save(AdminTransactionDto adminTransactionDto) {
        AdminTransactionModel adminTransactionModel = adminTransactionRepository.save(dtoToEntity(adminTransactionDto));
        System.out.println((adminTransactionModel instanceof AdminTransactionModel) ? "SUCCESS" : "FAILURE");
        return AdminMessengerModel.builder()
                .message((adminTransactionModel instanceof AdminTransactionModel) ? "SUCCESS" : "FAILURE")
                .build();
    }

    @Override
    public AdminMessengerModel deleteById(Long id) {
        return null;
    }

    @Override
    public AdminMessengerModel modify(AdminTransactionDto adminTransactionDto) {
        return null;
    }

    @Override
    public List<AdminTransactionDto> findAll() throws SQLException {
        return adminTransactionRepository.getAllTransactions().stream().toList();
    }

    @Override
    public Optional<AdminTransactionDto> findById(Long id) {
        return adminTransactionRepository.findById(id).stream().map(i -> entityToDto(i)).findAny();
    }

    @Override
    public Long count() {
        return adminTransactionRepository.countAllTransactions();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Map<String, Double> getNetProfitByDate() {
        return adminTransactionRepository.getNetProfitByDate();
    }

    @Override
    public Map<String, Double> getTotalByDate()  {
        return adminTransactionRepository.getTotalByDate();
    }

    @Override
    public Map<String, Map<String, Integer>> getQuantityByDate() {
        return adminTransactionRepository.getQuantityByDate();
    }
}
