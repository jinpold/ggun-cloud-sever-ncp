package store.ggun.alarm.service;

import store.ggun.alarm.domain.dto.ChatDto;
import store.ggun.alarm.domain.model.ChatModel;

public class ChatMapper {

    public static ChatDto toDto(ChatModel chatModel) {
        if (chatModel == null) {
            return null;
        }
        return ChatDto.builder()
                .id(chatModel.getId())
                .roomId(chatModel.getRoomId())
                .senderId(chatModel.getSenderId())
                .senderName(chatModel.getSenderName())
                .message(chatModel.getMessage())
                .createdAt(chatModel.getCreatedAt())
                .build();
    }

    // DTO to Model
    public static ChatModel toModel(ChatDto chatDto) {
        return ChatModel.builder()
                .id(chatDto.getId())
                .roomId(chatDto.getRoomId())
                .senderId(chatDto.getSenderId())
                .senderName(chatDto.getSenderName())
                .message(chatDto.getMessage())
                .createdAt(chatDto.getCreatedAt())
                .build();
    }
}
