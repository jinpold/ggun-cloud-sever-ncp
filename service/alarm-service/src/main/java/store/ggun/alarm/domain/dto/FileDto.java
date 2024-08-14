package store.ggun.alarm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
    private String id;
    private String filename;
    private String contentType;
    private String url;
    private byte[] data;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
