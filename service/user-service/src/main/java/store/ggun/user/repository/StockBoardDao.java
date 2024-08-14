package store.ggun.user.repository;

import org.springframework.stereotype.Service;
import store.ggun.user.domain.StockBoardDto;
import store.ggun.user.domain.StockBoardModel;

import java.util.List;
import java.util.Optional;

@Service
public interface StockBoardDao {
    List<StockBoardDto> findAllQuery();

    Optional<StockBoardDto> findByTitleQuery(String title);

    void modify(StockBoardDto model);
}
