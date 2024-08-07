package store.ggun.user.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.ggun.user.domain.TokenVo;
import store.ggun.user.domain.UserDto;
import store.ggun.user.domain.UserModel;
import store.ggun.user.repository.UserRepository;
import store.ggun.user.service.UserService;



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
        userModel.setRole("ROLE_USER");
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
    public UserDto login(UserDto userDto){ // exception으로 던지는 것도 고려해봐야됨 + 이미 로그인 되있는 상태면 어떻게 처리할껀지(다수 로그인 인정? or 에러?)
        UserModel user = userRepository.findByUsernames(userDto.getEmail());
        if (user.getId() == null) {
            return null;
        }else {
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                return entityToDto(user);
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


}