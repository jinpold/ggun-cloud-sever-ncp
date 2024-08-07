package store.ggun.alarm.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;
import store.ggun.alarm.domain.model.ChatModel;

@Configuration
public class KafkaConfig {


    @Bean
    public ReactiveKafkaProducerTemplate<String, ChatModel> reactiveKafkaProducerTemplate(KafkaProperties kafkaProperties) {
        return new ReactiveKafkaProducerTemplate<>(
                SenderOptions.<String, ChatModel>create(kafkaProperties.buildProducerProperties(null))
        );
    }

//    @Bean
//    public ReactiveKafkaConsumerTemplate<String, ChatModel> reactiveKafkaConsumerTemplate(KafkaProperties kafkaProperties) {
//        return new ReactiveKafkaConsumerTemplate<>(
//                ReceiverOptions.<String, ChatModel>create(kafkaProperties.buildConsumerProperties(null))
//        );
//    }

//    @Bean
//    public ReactiveKafkaProducerTemplate<String, ChatModel> reactiveKafkaProducerTemplate() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        SenderOptions<String, ChatModel> senderOptions = SenderOptions.create(props);
//        return new ReactiveKafkaProducerTemplate<>(senderOptions);
//    }
//
//    @Bean
//    public ReactiveKafkaConsumerTemplate<String, ChatModel> reactiveKafkaConsumerTemplate() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, ChatModel.class.getName());
//
//        ReceiverOptions<String, ChatModel> receiverOptions = ReceiverOptions.create(props);
//        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
//    }


    @Bean
    public NewTopic adviceTopic() {
        return TopicBuilder.name("advice")
                .partitions(10)
                .replicas(1)
                .build();
    }
}