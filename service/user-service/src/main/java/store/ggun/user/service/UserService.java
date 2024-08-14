package store.ggun.user.service;


import org.springframework.http.ResponseEntity;
import store.ggun.user.domain.PrincipalUserDetails;
import store.ggun.user.domain.TokenVo;
import store.ggun.user.domain.UserDto;
import store.ggun.user.domain.UserModel;

public interface UserService {

    default UserModel dtoToEntity(UserDto dto) {
        return UserModel.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .name(dto.getName())
                .age(dto.getAge())
                .sex(dto.getSex())
                .email(dto.getEmail())
                .ssnF(dto.getSsnF())
                .ssnS(dto.getSsnS())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .asset(dto.getAsset())
                .color(dto.getColor())
                .investmentPropensity(dto.getInvestmentPropensity())
                .roles(dto.getRoles())
                .build();
    }

    default UserDto entityToDto(UserModel model) {
        return UserDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .password(model.getPassword())
                .name(model.getName())
                .age(model.getAge())
                .sex(model.getSex())
                .email(model.getEmail())
                .ssnF(model.getSsnF())
                .ssnS(model.getSsnS())
                .address(model.getAddress())
                .phone(model.getPhone())
                .asset(model.getAsset())
                .color(model.getColor())
                .investmentPropensity(model.getInvestmentPropensity())
                .roles(model.getRoles())
                .build();
    }

    TokenVo join(UserDto param);

    PrincipalUserDetails login(UserDto userDto);

    TokenVo modify(UserDto userDto);

    TokenVo delete(long id);

    PrincipalUserDetails findByEmailOauth(String email);
}
