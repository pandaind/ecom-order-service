package com.example.demo.order.event;

import com.example.demo.order.model.EventStatus;
import com.example.demo.order.service.OrderService;
import com.example.demo.order.service.dto.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Slf4j
@Component
public class OrderEventProducer {
    private static final String TOPIC = "order-events";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @Autowired
    public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate,
                              ObjectMapper objectMapper,
                              OrderService orderService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    public ListenableFuture<SendResult<String, String>> sendEvent(OrderEvent event) throws JsonProcessingException {
        String key = event.getEventId();
        String value = objectMapper.writeValueAsString(event);
        ProducerRecord<String, String> producerRecord = buildProducerRecord(key, value);
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                handleSuccess(key, event, result);
            }

            @Override
            public void onFailure(@NotNull Throwable ex) {
                handleFailure(key, event, ex);
            }

        });
        return listenableFuture;
    }

    private void handleSuccess(String key, OrderEvent value, SendResult<String, String> result) {
        log.info("Message Sent SuccessFully for the key : {}, partition is {}", key, result.getRecordMetadata().partition());
        OrderDTO dto = value.getOrder();
        dto.setEventStatus(EventStatus.SUCCESS);
        this.orderService.updateOrder(dto);
    }

    private void handleFailure(String key, OrderEvent value, Throwable ex) {
        log.error("Error Sending the Message with key {} and the exception is {}", key, ex.getMessage());
        try {
            OrderDTO dto = value.getOrder();
            dto.setEventStatus(EventStatus.ERROR);
            this.orderService.updateOrder(dto);
        } catch (Throwable throwable) {
            log.error("Error in handleFailure: {}", throwable.getMessage());
        }
    }

    private ProducerRecord<String, String> buildProducerRecord(String key, String value) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "order-service".getBytes()));
        return new ProducerRecord<>(TOPIC, null, key, value, recordHeaders);
    }
}
