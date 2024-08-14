package store.ggun.user.service;

import store.ggun.user.domain.*;

import java.util.List;

public interface StockBoardService {
    List<StockBoardDto> findAllList();

    Messenger save(StockBoardDto model);

    Messenger deleteById(Long boardId);

    default StockBoardModel dtoToEntity (StockBoardDto dto) {
        return StockBoardModel.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .description(dto.getDescription())
                .build();
    }

    default StockBoardDto entityToDto (StockBoardModel model) {
        return StockBoardDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .content(model.getContent())
                .description(model.getDescription())
                .build();
    }

    Messenger modify(StockBoardDto model);
}
