package store.ggun.admin.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.dto.AdminBoardDto;
import store.ggun.admin.domain.model.AdminMessengerModel;
import store.ggun.admin.repository.jpa.AdminBoardRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AdminBoardService implements store.ggun.admin.service.AdminBoardService {

    private final AdminBoardRepository repository;

    @Override
    public AdminMessengerModel save(AdminBoardDto t) {
        repository.save(dtoToEntity(t));
        return AdminMessengerModel.builder()
                .message("성공")
                .status(200)
                .build();
    }
    @Override
    public List<AdminBoardDto> findAll() throws SQLException {
        return repository.findAll().stream().map(i->entityToDto(i)).toList();
    }
    @Override
    public Optional<AdminBoardDto> findById(Long id) {
        return repository.findById(id).stream().map(i -> entityToDto(i)).findAny();
    }
    @Transactional
    @Override
    public AdminMessengerModel modify(AdminBoardDto adminBoardDto) {
        repository.save(dtoToEntity(adminBoardDto));
        return AdminMessengerModel.builder()
                .message("성공")
                .status(200)
                .build();
    }
    @Override
    public AdminMessengerModel deleteById(Long id) {
        repository.deleteById(id);
        return existsById(id) ?
                AdminMessengerModel.builder()
                        .message("회원탈퇴 완료")
                        .status(200)
                        .build() :
                AdminMessengerModel.builder()
                        .message("회원탈퇴 실패")
                        .status(200)
                        .build();
    }
    @Override
    public Long count() {
        return repository.count();
    }
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}