package store.ggun.user.service;

import store.ggun.user.domain.*;

import java.util.List;

public interface StockCommentService {

    default StockCommentDto entityToDto(StockCommentModel model){
        return StockCommentDto.builder()
                .id(model.getId())
                .comment(model.getComment())
                .writerId(model.getWriterId().getUsername())
                .commentDate(model.getRegDate())
                .build();
    }

    default StockCommentModel dtoToEntity(StockCommentDto dto, Long id){
        return StockCommentModel.builder()
                .id(dto.getId())
                .comment(dto.getComment())
                .writerId(UserModel.builder()
                        .id(id)
                        .build())
                .stockArticles(StockArticleModel.builder()
                        .id(dto.getStockArticles())
                        .build())
                .build();
    }

    List<StockCommentDto> findAllList(Long articleId);

    Messenger save(StockCommentDto model, Long id);

    Messenger deleteById(Long articleId, Long id);

    Messenger modifyById(StockCommentDto articleId, Long id);
}
