package store.ggun.admin.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMessengerModel {

    private String message;
    private int status;
    private String accessToken;
    private String refreshToken;
    private Long id;
    private String username;
}
