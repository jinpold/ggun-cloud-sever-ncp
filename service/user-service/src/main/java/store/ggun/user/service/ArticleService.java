package store.ggun.user.service;

import store.ggun.user.domain.*;

import java.util.List;

public interface ArticleService {
    ArticleModel save(ArticleDto model, Long id);

    List<ArticleDto> findAllByBoardId(Long boardId);

    ArticleDto modify(ArticleDto model, Long id);

    Messenger delete(Long model, Long id);

    default ArticleModel dto2ToEntity(ArticleDto2 dto2){
        return ArticleModel.builder()
                .id(dto2.getId())
                .title(dto2.getTitle())
                .content(dto2.getContent())
                .writerId(UserModel.builder()
                        .id(dto2.getWriterId())
                        .build())
                .boardId(BoardModel.builder()
                        .id(dto2.getBoardId())
                        .build())
                .answer(dto2.getAnswer())
                .build();
    }
}
