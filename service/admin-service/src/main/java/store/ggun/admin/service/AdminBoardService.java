package store.ggun.admin.service;

import store.ggun.admin.domain.dto.AdminBoardDto;
import store.ggun.admin.domain.model.AdminBoardModel;

public interface AdminBoardService extends AdminCommandService<AdminBoardDto>, AdminQueryService<AdminBoardDto> {




    default AdminBoardModel dtoToEntity(AdminBoardDto dto){

        return AdminBoardModel.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    default AdminBoardDto entityToDto(AdminBoardModel ent){

        return AdminBoardDto.builder()
                .id(ent.getId())
                .title(ent.getTitle())
                .description(ent.getDescription())
                .regDate(ent.getRegDate().toString())
                .modDate(ent.getModDate().toString())
                .build();
    }
}
