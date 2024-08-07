package store.ggun.alarm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import store.ggun.alarm.domain.model.RoleModel;
import store.ggun.alarm.domain.model.RoomModel;
import store.ggun.alarm.domain.model.UserModel;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FluxChatMongoConfig {
    private final ReactiveMongoTemplate mongoTemplate;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            mongoTemplate.getCollectionNames()
                    .flatMap(collectionName -> mongoTemplate.dropCollection(collectionName))
                    .log()
                    .collectList()
                    .flatMapMany(i -> mongoTemplate.insertAll(
                            List.of(
                                    UserModel.builder()
                                            .email("admin2@admin")
                                            .password("admin2")
                                            .firstName("heo")
                                            .lastName("jin")
                                            .profile("test url2")
                                            .roles(List.of(RoleModel.ROLE_ADMIN, RoleModel.ROLE_USER, RoleModel.ROLE_GUEST))
                                            .build()
                            )
                    ))
                    .collectList()
                    .flatMap(users -> {
                        UserModel user = users.get(0);
                        return mongoTemplate.insert(
                                RoomModel.builder()
                                        .id("1")
                                        .title("test room")
                                        .members(List.of(String.valueOf(user.getId())))
                                        .build()
                        );
                    })
                    .subscribe();

            System.out.println("MongoDB Initiated!");
        };
    }
}