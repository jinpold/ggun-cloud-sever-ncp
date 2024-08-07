package store.ggun.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.ggun.admin.domain.model.AdminEmailModel;
import store.ggun.admin.serviceImpl.AdminEmailServiceImpl;


@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class AdminEmailController {


    private final AdminEmailServiceImpl adminEmailServiceImpl;

    @PostMapping("/send")
    public String sendEmail(@RequestBody AdminEmailModel adminEmailModel) {
        adminEmailServiceImpl.sendEmail(adminEmailModel.getEnpEmail(), adminEmailModel.getMessage());
        return "이메일이 성공적으로 전송되었습니다.";
    }
}