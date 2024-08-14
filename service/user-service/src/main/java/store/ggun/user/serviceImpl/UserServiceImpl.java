package store.ggun.user.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.ggun.user.domain.PrincipalUserDetails;
import store.ggun.user.domain.TokenVo;
import store.ggun.user.domain.UserDto;
import store.ggun.user.domain.UserModel;
import store.ggun.user.domain.vo.GatewayUser;
import store.ggun.user.domain.vo.Role;
import store.ggun.user.repository.UserRepository;
import store.ggun.user.service.UserService;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public TokenVo join(UserDto param) {
        if (!userRepository.existsByUsername(param.getUsername())){
        UserModel userModel = dtoToEntity(param);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.setRoles(Role.valueOf("ROLE_USER"));
        userRepository.save(userModel);
        return TokenVo.builder()
                .message(userRepository.existsByUsername(param.getUsername())?"SUCCESS":"FAIL")
                .build();
        }
        return TokenVo.builder()
                .message("아이디가 존재합니다.")
                .build();
    }

    @Override
    public PrincipalUserDetails login(UserDto userDto){
        UserModel user = userRepository.findByUsernames(userDto.getUsername());
        if (user.getId() == null) {
            return null;
        }else {
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                return new PrincipalUserDetails(GatewayUser.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(List.of(user.getRoles()))
                        .name(user.getName())
                        .registration(user.getRegistration())
                        .build());
            }else {
                return null;
            }
        }
    }

    @Override
    public TokenVo modify(UserDto userDto) {
        userRepository.modify(userDto);
        UserModel user = userRepository.modifyFind(userDto);
        UserDto userDto1 = entityToDto(user);
        return TokenVo.builder()
                .message(userDto1.equals(userDto)?"SUCCESS":"FAIL")
                .build();
    }

    @Override
    public TokenVo delete(long id) {
        userRepository.deleteById(id);
        return TokenVo.builder()
                .message(userRepository.existsById(id)?"FAIL":"SUCCESS")
                .build();
    }

    @Override
    public PrincipalUserDetails findByEmailOauth(String email) {
        UserModel user = userRepository.findByEmailOauth(email);
        if (user == null) {
            return null;
        }
        return new PrincipalUserDetails(GatewayUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(List.of(user.getRoles()))
                .name(user.getName())
                .registration(user.getRegistration())
                .build());
    }


}