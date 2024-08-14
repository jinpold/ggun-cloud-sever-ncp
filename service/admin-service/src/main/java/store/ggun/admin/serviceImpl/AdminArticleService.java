package store.ggun.admin.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.dto.AdminArticleDto;
import store.ggun.admin.domain.model.AdminArticleModel;
import store.ggun.admin.domain.model.AdminBoardModel;
import store.ggun.admin.domain.model.AdminModel;
import store.ggun.admin.domain.model.Messenger;
import store.ggun.admin.repository.jpa.AdminArticleRepository;
import store.ggun.admin.repository.jpa.AdminBoardRepository;
import store.ggun.admin.repository.jpa.AdminRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AdminArticleService implements store.ggun.admin.service.AdminArticleService {

    private final AdminArticleRepository adminArticleRepository;
    private final AdminBoardRepository adminBoardRepository;
    private final AdminRepository adminRepository;

    @Transactional
    @Override
    public Messenger save(AdminArticleDto adminArticleDto) {
        AdminBoardModel adminBoardModel = adminBoardRepository.findById(adminArticleDto.getBoardId()).orElseThrow();
        AdminModel adminModel = adminRepository.findById(adminArticleDto.getWriterId()).orElseThrow();
        AdminArticleModel adminArticleModel = adminArticleRepository.save(dtoToEntity(adminArticleDto, adminBoardModel, adminModel));

        return Messenger.builder()
                .id(adminArticleModel.getAdminBoardModel().getId()) //board id
                .message(adminArticleModel instanceof AdminArticleModel ? "SUCCESS" : "FAILURE")
                .build();

    }

    @Override
    public Messenger deleteById(Long id) {
        adminArticleRepository.deleteById(id);
        return Messenger.builder()
                .message(adminArticleRepository.findById(id).isPresent() ? "SUCCESS" : "FAILURE")
                .build();
    }
    @Transactional
    @Override
    public Messenger modify(AdminArticleDto adminArticleDto) {
        Optional<AdminArticleModel> article = adminArticleRepository.findById(adminArticleDto.getId());

        if (article.isEmpty()) {
            return Messenger.builder()
                    .message("FAILURE")
                    .build();
        }

        article.get().setTitle(adminArticleDto.getTitle());
        article.get().setContent(adminArticleDto.getContent());
        adminArticleRepository.save(article.get());
        return Messenger.builder()
                .message("SUCCESS")
                .build();

    }

    @Override
    public List<AdminArticleDto> findAll() throws SQLException {
        return adminArticleRepository.findAll().stream().map(i -> entityToDto(i)).toList();
    }
    @Override
    public Optional<AdminArticleDto> findById(Long id) {
        return adminArticleRepository.findById(id).stream().map(i -> entityToDto(i)).findAny();
    }
    @Override
    public Long count() {
        return adminArticleRepository.count();
    }
    @Override
    public boolean existsById(Long id) {
        return adminArticleRepository.existsById(id);
    }
    @Override
    public List<AdminArticleDto> getArticleByBoardId(Long boardId) {
        return adminArticleRepository.getArticleByBoardId(boardId)
                .stream().map(i -> entityToDto(i))
                .toList();
    }
}
