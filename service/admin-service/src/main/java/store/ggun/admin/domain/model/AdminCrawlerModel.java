package store.ggun.admin.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminCrawlerModel {
    private String imgLink;
    private String title;
    private String content;
    private String imgSrc;
}
