package store.ggun.chat.config;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import store.ggun.chat.domain.UserModel;
import store.ggun.chat.repository.UserRepository;

import java.util.stream.IntStream;

@Configuration
public class UsersCollectionConfig {
    @Bean
    CommandLineRunner initUsers(UserRepository repo) {
        System.out.println(" >>>> 1 실행 >>>>");
        String uri = "mongodb://root:root@localhost:27017/admin";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            System.out.println(" DB 접속 ...");
            MongoDatabase database = mongoClient.getDatabase("ggundb");
            System.out.println(" MyDB 컬렉션 접속 ...");
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println(" 핑 연 결 ...");
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                System.out.println(commandResult);
            } catch (MongoException me) {
                System.out.println(" >>>> 9 에러 발생 >>>>");
                System.err.println(me);
            }
        }
        return args -> {
            IntStream.range(0, 1).forEach(i -> {
                Mono<UserModel> userMono = Mono.just(UserModel.builder()
                        .email(i + "@test.com")
                        .firstName("James" + i)
                        .lastName("Byden " + i)
                        .password("aaa")
                        .roles(null)
                        .build());

                repo.insert(userMono);
            });
        };
    }
}
