package com.example.demo.order.event;

import com.example.demo.order.service.dto.OrderDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    private String eventId;
    private OrderEventType type;
    private OrderDTO order;
}
