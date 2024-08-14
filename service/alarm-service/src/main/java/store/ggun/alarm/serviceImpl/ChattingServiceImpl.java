//package store.ggun.alarm.serviceImpl;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import store.ggun.alarm.domain.model.ChatModel;
//import store.ggun.alarm.service.ChatService;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ChattingServiceImpl implements ChattingSerivce {
//
//    private final ReactiveKafkaProducerTemplate<String, ChatModel> kafkaTemplate;
//    private final ReactiveMongoTemplate mongoTemplate;
//
//    @Override
//    public Mono<ChatModel> processMessage(ChatModel chatModel) {
//        chatModel.setCreatedAt(LocalDateTime.now()); // 인스턴스 메소드 호출
//        return kafkaTemplate.send("advice", chatModel)
//                .then(mongoTemplate.save(chatModel))
//                .thenReturn(chatModel);
//    }
//
//    @Override
//    public Flux<ChatModel> getChatMessages(String roomId) {
//        Criteria criteria = Criteria.where("roomId").is(roomId);
//        Query query = new Query(criteria);
//        log.info("Query: {}", query);
//        return mongoTemplate.find(query, ChatModel.class);
//    }
//    @Override
//    public Mono<ChatModel> createChatRoom(String senderId, String receiverId) {
//        ChatModel chatModel = ChatModel.builder()
//                .roomId(UUID.randomUUID().toString())
//                .senderId(senderId)
//                .receiverId(receiverId)
//                .message("채팅방이 생성되었습니다.")
//                .build();
//        return mongoTemplate.save(chatModel);
//    }
//
//    @Override
//    public Mono<String> getRoomId(String senderId, String receiverId) {
//
//        Criteria criteria = new Criteria().orOperator(
//                Criteria.where("senderId").is(senderId).and("receiver").is(receiverId),
//                Criteria.where("senderId").is(receiverId).and("receiver").is(senderId)
//        );
//        Query query = new Query(criteria);
//        return mongoTemplate.findOne(query, ChatModel.class)
//                .map(ChatModel::getRoomId);
//    }
//}
