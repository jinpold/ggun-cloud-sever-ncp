package store.ggun.user.service;

import store.ggun.user.domain.ItemDto;
import store.ggun.user.domain.ItemModel;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ItemService {

    default ItemModel dtoToEntity(ItemDto dto) {
        return ItemModel.builder()
                .id(dto.getId())
                .open(dto.getOpen())
                .high(dto.getHigh())
                .low(dto.getLow())
                .close(dto.getClose())
                .adjClose(dto.getAdjClose())
                .volume(dto.getVolume())
                .date(dto.getDate())
                .build();
    }

    default ItemDto entityToDto(ItemModel ent) {
        return ItemDto.builder()
                .id(ent.getId())
                .open(ent.getOpen())
                .high(ent.getHigh())
                .low(ent.getLow())
                .close(ent.getClose())
                .adjClose(ent.getAdjClose())
                .volume(ent.getVolume())
                .date(ent.getDate())
                .build();
    }

    List<ItemModel> findByVolume();

    List<ItemModel> findAll();

    List<ItemModel> findDetail(Map<String,String> search) throws ParseException;
}