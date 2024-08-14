package store.ggun.admin.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.dto.AdminDto;
import store.ggun.admin.domain.model.AdminModel;
import store.ggun.admin.domain.model.Messenger;
import store.ggun.admin.repository.jpa.AdminRepository;
import store.ggun.admin.security.JwtProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements store.ggun.admin.service.AdminService {

    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public Messenger save(AdminDto adminDto) {
        AdminModel ent = adminRepository.save(dtoToEntity(adminDto));
        System.out.println((ent instanceof AdminModel) ? "SUCCESS" : "FAILURE");
        return Messenger.builder()
                .message((ent instanceof AdminModel) ? "SUCCESS" : "FAILURE")
                .build();
    }
    @Override
    public List<AdminDto> findAll() {
        return adminRepository.findAll().stream().map(i->entityToDto(i)).toList();
    }
    @Override
    public Optional<AdminDto> findById(Long id) {
        return adminRepository.findById(id).stream().map(i -> entityToDto(i)).findAny();
    }

    @Transactional
    @Override
    public Messenger modify(AdminDto adminDto) {
        AdminModel adminModel = adminRepository.findById(adminDto.getId()).orElseThrow(() -> new RuntimeException("Admin not found"));

        if (adminDto.getUsername() != null && !adminDto.getUsername().isEmpty()) {
            adminModel.setUsername(adminDto.getUsername());
        }

        adminModel.setPassword(adminDto.getPassword());
        adminModel.setEmail(adminDto.getEmail());
        adminModel.setName(adminDto.getName());
        adminModel.setNumber(adminDto.getNumber());
        adminModel.setPhone(adminDto.getPhone());
        adminModel.setDepartment(adminDto.getDepartment());
        adminModel.setPosition(adminDto.getPosition());
        adminModel.setJob(adminDto.getJob());
        adminModel.setRole(adminDto.getRole());
        adminModel.setToken(adminDto.getToken());
        adminRepository.save(adminModel);

        return adminRepository.findById(adminDto.getId()).get().equals(adminModel) ?
                Messenger.builder().message("SUCCESS").build() :
                Messenger.builder().message("FAILURE").build();
    }
    @Transactional
    @Override
    public Messenger modifyRole(AdminDto adminDto) {
        AdminModel adminModel = adminRepository.findById(adminDto.getId()).get();
        if (adminDto.getUsername() != null && !adminDto.getUsername().equals("")) {
            adminModel.setUsername(adminDto.getUsername());
        }
        if (adminDto.getRole() != null && !adminDto.getRole().equals("")) {
            adminModel.setRole(adminDto.getRole());
        }
        return adminRepository.findById(adminDto.getId()).get().equals(adminModel) ?
                Messenger.builder().message("SUCCESS").build() :
                Messenger.builder().message("FAILURE").build();
    }

    @Transactional
    @Override
    public Messenger update(AdminDto adminDto) {
        AdminModel adminModel = adminRepository.findById(adminDto.getId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        // 비밀번호를 사원번호로 변경
        adminModel.setPassword(adminDto.getNumber());
        adminRepository.save(adminModel);

        // 비밀번호가 정상적으로 변경되었는지 확인
        boolean isPasswordUpdated = adminRepository.findById(adminDto.getId())
                .map(AdminModel::getPassword)
                .map(password -> password.equals(adminDto.getNumber()))
                .orElse(false);

        return isPasswordUpdated ? Messenger.builder().message("SUCCESS").build()
                : Messenger.builder().message("FAILURE").build();
    }

    @Override
    public Messenger deleteById(Long id) {
        if (!adminRepository.existsById(id)) {
            // ID가 존재하지 않는 경우
            return Messenger.builder().message("FAILURE").build();
        }

        adminRepository.deleteById(id);
        // 삭제가 완료되었으므로 SUCCESS 메시지 반환
        return Messenger.builder().message("SUCCESS").build();
    }

    @Override
    public boolean existsById(Long id) {
        return adminRepository.existsById(id);
    }

    @Override
    public Long count() {
        return adminRepository.count();
    }

//    @Transactional
//    @Override
//    public Messenger login(AdminDto adminDto) {
//        log.info("login 진입 성공 email: {}", adminDto.getEmail());
//        List<AdminModel> admins = adminRepository.findAdminByEmail(adminDto.getEmail());
//
//        if (admins.size() == 1) {
//            AdminModel adminModel = admins.get(0);
//            boolean flag = adminModel.getPassword().equals(adminDto.getPassword());
//            return Messenger.builder()
//                    .message(flag ? "SUCCESS" : "FAILURE")
//                    .build();
//        } else if (admins.isEmpty()) {
//            return Messenger.builder()
//                    .message("User does not exist.")
//                    .build();
//        } else {
//            return Messenger.builder()
//                    .message("Multiple users found with the same email. Please contact support.")
//                    .build();
//        }
//    }

//        log.info("로그인 서비스로 들어온 파라미터 : " +dto);
//        AdminModel adminModel = adminRepository.findAdminByUsername((dto.getUsername())).get();
//        String accessToken = jwtProvider.createToken(entityToDto(adminModel));
//
//        boolean flag = adminModel.getPassword().equals(dto.getPassword());
//        log.info("accessToken 확인용: "+accessToken);
//        adminRepository.modifyTokenById(adminModel.getId(), accessToken);
//        // 토큰을 각 섹션 (Header, payload, signature)으로 분할
//
//        jwtProvider.printPayload(accessToken);
//        return Messenger.builder()
//                .message(flag ? "SUCCESS" : "FAILURE")
//                .accessToken(flag ? accessToken : "NONE")
//                .build();

    @Override
    public Boolean existsByUsername(String username) {
        Integer count  = adminRepository.existsByUsername(username);
        return count ==1;
    }

    @Override
    public boolean findAdminByEmail(String email) {
        Integer count = adminRepository.existsByEmail(email);
        return count == 1;
    }

    @Override
    public Optional<AdminModel> findAdminByUsername(String name) {
        return adminRepository.findAdminByUsername(name);
    }

    @Transactional
    @Override
    public Boolean logout(String token) {
        String accessToken = token != null && token.startsWith("Bearer ") ?
                token.substring(7) : "undefined";
        Long id = jwtProvider.getPayload(accessToken).get("adminId", Long.class);
        String deleteToken = "";
        adminRepository.modifyTokenById(id,deleteToken);
        return adminRepository.findById(id).get().getToken().equals("");
    }

    @Override
    public Optional<AdminDto> findUserInfo(String accessToken) {
        String splitToken = accessToken.substring(7);
        Long id = jwtProvider.getPayload(splitToken).get("id", Long.class);

        return Optional.of(entityToDto(adminRepository.findById(id).orElseThrow()));
    }

}
