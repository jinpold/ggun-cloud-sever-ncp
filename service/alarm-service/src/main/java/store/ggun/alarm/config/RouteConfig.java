package store.ggun.alarm.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import store.ggun.alarm.handler.ChatHandler;

@Configuration
public class RouteConfig {

    @Bean
    public RouterFunction<ServerResponse> route(ChatHandler chatHandler) {
        return RouterFunctions.route()
                .GET("/api/chats/receive/{roomId}", chatHandler::subscribeByRoomId)
                .build();
    }
}