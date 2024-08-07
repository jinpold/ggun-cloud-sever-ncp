package store.ggun.user.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class TokenVo {
    private String refreshToken;
    private String accessToken;
    private String message;
    private String role;
}
