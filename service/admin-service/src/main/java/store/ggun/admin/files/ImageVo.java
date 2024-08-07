package store.ggun.admin.files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageVo {

    private long imageId;
    String name;
    long lastModified;
    long lastModifiedDate;
    String type;
    String webkitRelativePath;
    long size;

}