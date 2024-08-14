package store.ggun.user.repository;

import store.ggun.user.domain.BoardDto;
import store.ggun.user.domain.BoardModel;

import java.util.List;

public interface BoardDao {
    List<BoardDto> findAllR();
}
