package ru.astondevs.asber.depositservice.config.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.dto.NewCardRequestDto;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link KafkaProducer} configuration beans
 */
@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     * The strategy to produce a Producer instance(s).
     * @return {@link ProducerFactory}
     */
    @Bean
    public ProducerFactory<String, DepositAgreementDto> depositAgreementDtoProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * A template for executing high-level operations.
     * @return {@link KafkaTemplate}
     */
    @Bean
    public KafkaTemplate<String, DepositAgreementDto> depositAgreementDtoKafkaTemplate() {
        KafkaTemplate<String, DepositAgreementDto> template = new KafkaTemplate<>(depositAgreementDtoProducerFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    /**
     * The strategy to produce a Producer instance(s).
     * @return {@link ProducerFactory}
     */
    @Bean
    public ProducerFactory<String, NewCardRequestDto> cardRequestDtoProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * A template for executing high-level operations.
     * @return {@link KafkaTemplate}
     */
    @Bean
    public KafkaTemplate<String, NewCardRequestDto> cardRequestDtoKafkaTemplate() {
        KafkaTemplate<String, NewCardRequestDto> template = new KafkaTemplate<>(cardRequestDtoProducerFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}
