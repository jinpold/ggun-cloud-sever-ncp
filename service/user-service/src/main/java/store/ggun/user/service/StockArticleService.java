package store.ggun.user.service;


import store.ggun.user.domain.*;

import java.util.List;

public interface StockArticleService {

    default StockArticleDto entityToDto(StockArticleModel model){
        return StockArticleDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .content(model.getContent())
                .writerId(model.getWriterId().getUsername())
                .stockBoards(model.getStockBoards().getId())
                .build();
    }

    default StockArticleModel dtoToEntity(StockArticleDto dto,Long id){
        return StockArticleModel.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writerId(UserModel.builder()
                        .id(id)
                        .build())
                .stockBoards(StockBoardModel.builder()
                        .id(dto.getStockBoards())
                        .build())
                .build();
    }

    List<StockArticleDto> findAllList(Long boardId);

    Messenger save(StockArticleDto model, Long id);

    Messenger deleteById(Long articleId, Long id);

    Messenger modifyById(StockArticleDto articleId, Long id);

    List<StockArticleDto2> findAllListlistTop();
}
