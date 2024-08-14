package store.ggun.alarm.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;
import store.ggun.alarm.domain.model.ChatModel;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, ChatModel> reactiveKafkaProducerTemplate(KafkaProperties kafkaProperties) {
        SenderOptions<String, ChatModel> senderOptions = SenderOptions.create(kafkaProperties.buildProducerProperties(null));
        return new ReactiveKafkaProducerTemplate<>(senderOptions);
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, ChatModel> reactiveKafkaConsumerTemplate(KafkaProperties kafkaProperties) {
        ReceiverOptions<String, ChatModel> receiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties(null));
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }

    @Bean
    public NewTopic adviceTopic() {
        return TopicBuilder.name("advice")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "223.130.140.182:9092:9092"); // Docker Compose의 Kafka 컨테이너 이름
        configs.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000); // 30 seconds
        return new KafkaAdmin(configs);
    }
}