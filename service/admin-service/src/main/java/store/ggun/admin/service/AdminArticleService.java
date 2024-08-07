package store.ggun.admin.service;

import store.ggun.admin.domain.dto.AdminArticleDto;
import store.ggun.admin.domain.model.AdminArticleModel;
import store.ggun.admin.domain.model.AdminBoardModel;
import store.ggun.admin.domain.model.AdminModel;

import java.util.List;

public interface AdminArticleService extends AdminCommandService<AdminArticleDto>, AdminQueryService<AdminArticleDto> {

    List<AdminArticleDto> getArticleByBoardId(Long id);

    default AdminArticleModel dtoToEntity(AdminArticleDto dto, AdminBoardModel adminBoardModel, AdminModel adminModel) {
        return AdminArticleModel.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(adminModel)
                .adminBoardModel(adminBoardModel)
                .build();
    }
    default AdminArticleDto entityToDto(AdminArticleModel ent) {

        return AdminArticleDto.builder()
                .id(ent.getId())
                .title(ent.getTitle())
                .content(ent.getContent())
                .writerId(ent.getWriter().getId())
                .boardId(ent.getAdminBoardModel().getId())
                .regDate(String.valueOf(ent.getModDate()))
                .modDate(String.valueOf(ent.getRegDate()))
                .build();
    }
}
