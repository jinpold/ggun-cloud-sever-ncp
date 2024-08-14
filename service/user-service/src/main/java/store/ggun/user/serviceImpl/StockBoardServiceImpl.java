package store.ggun.user.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.ggun.user.domain.*;
import store.ggun.user.repository.StockBoardRepository;
import store.ggun.user.service.StockBoardService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockBoardServiceImpl implements StockBoardService {
    private final StockBoardRepository repository;


    @Override
    public List<StockBoardDto> findAllList() {
        return repository.findAllQuery();
    }

    @Override
    public Messenger save(StockBoardDto model) {
        if (!repository.existsByTitle(model.getTitle())) {
            repository.save(dtoToEntity(model));
            if (repository.existsByTitle(model.getTitle())) {
                return Messenger.builder()
                        .message("SUCCESS")
                        .build();
            }
        }
        return Messenger.builder()
                .message("FAIL")
                .build();
    }

    @Transactional
    @Override
    public Messenger deleteById(Long boardId) {
        repository.deleteById(boardId);
        if (repository.existsById(boardId)) {
            return Messenger.builder()
                    .message("FAIL")
                    .build();
        } else {
            return Messenger.builder()
                    .message("SUCCESS")
                    .build();
        }
    }

    @Override
    public Messenger modify(StockBoardDto model) {
        repository.modify(model);
        return Messenger.builder().message("SUCCESS").build();
    }
}
