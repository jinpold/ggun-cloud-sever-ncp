package store.ggun.alarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class HomeController {

    public String Date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

    @GetMapping("/api/chats/test")
    public String hello() {
        return Date +" "+"Welcome To alarm service";
    }
}
