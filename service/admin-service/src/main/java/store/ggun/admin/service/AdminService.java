package store.ggun.admin.service;

import store.ggun.admin.domain.dto.AdminDto;
import store.ggun.admin.domain.model.AdminModel;
import store.ggun.admin.domain.model.AdminMessengerModel;

import java.util.Optional;

public interface AdminService extends AdminCommandService<AdminDto>, AdminQueryService<AdminDto> {
    // command
    AdminMessengerModel modify(AdminDto adminDto);
    AdminMessengerModel modifyRole(AdminDto adminDto);
    AdminMessengerModel update(AdminDto adminDto);
    // query
    Boolean existsByUsername(String username);
    boolean findAdminByEmail(String email);
    Optional<AdminModel> findAdminByUsername(String name);
    Boolean logout(String accessToken);
    Optional<AdminDto> findUserInfo(String username);



    default AdminModel dtoToEntity(AdminDto dto){
        return AdminModel.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .name(dto.getName())
                .number(dto.getNumber())
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .job(dto.getJob())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .role(dto.getRole())
                .profile(dto.getProfile())
                .token(dto.getToken())
                .build();
    }


    default AdminDto entityToDto(AdminModel ent) {
        return AdminDto.builder()
                .id(ent.getId())
                .username(ent.getUsername())
                .password(ent.getPassword())
                .name(ent.getName())
                .number(ent.getNumber())
                .department(ent.getDepartment())
                .position(ent.getPosition())
                .job(ent.getJob())
                .email(ent.getEmail())
                .phone(ent.getPhone())
                .role(ent.getRole())
                .profile(ent.getProfile())
                .token(ent.getToken())
                .build();
    }
}


