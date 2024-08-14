package store.ggun.alarm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import store.ggun.alarm.domain.model.RoomModel;
import store.ggun.alarm.domain.model.UserModel;
import store.ggun.alarm.domain.vo.Role;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FluxChatMongoConfig {
    private final ReactiveMongoTemplate mongoTemplate;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            mongoTemplate.getCollectionNames()
                    .flatMap(collectionName -> mongoTemplate.dropCollection(collectionName).thenReturn(collectionName))
                    .doOnNext(collectionName -> System.out.println("Dropped collection: " + collectionName))
                    .thenMany(mongoTemplate.insertAll(generateUsers(20)))  // 20명의 User 생성
                    .doOnNext(user -> System.out.println("Inserted user: " + user))
                    .thenMany(mongoTemplate.findAll(UserModel.class)
                            .collectList()
                            .flatMapMany(users -> {
                                List<RoomModel> rooms = new ArrayList<>();
                                for (int i = 0; i < 10; i++) {
                                    UserModel user1 = users.get(i * 2); // 짝수 인덱스
                                    UserModel user2 = users.get(i * 2 + 1); // 홀수 인덱스
                                    rooms.add(RoomModel.builder()
                                            .id(String.valueOf(i + 1))
                                            .title("Room " + (i + 1))
                                            .members(List.of(user1.getId(), user2.getId()))
                                            .build());
                                }
                                return mongoTemplate.insertAll(rooms);
                            })
                    )
                    .doOnNext(room -> System.out.println("Inserted room: " + room))
                    .doOnComplete(() -> System.out.println("MongoDB Initiated!"))
                    .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                    .blockLast(); // 마지막 요소를 블로킹하여 기다립니다.
        };
    }

    // 유저 생성 로직 수정
    private List<UserModel> generateUsers(int count) {
        List<UserModel> users = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            users.add(UserModel.builder()
                    .id(String.valueOf(i))  // 수동으로 id 설정
                    .email("user" + i + "@test.com")
                    .password("password" + i)
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .profile("profileUrl" + i)
                    .roles(List.of(Role.ROLE_ADMIN)) // 모든 사용자를 ROLE_ADMIN으로 설정
                    .build());
        }
        return users;
    }
}
