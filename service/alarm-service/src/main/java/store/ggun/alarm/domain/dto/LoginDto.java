package store.ggun.alarm.domain.dto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
