package id.saputra.adi.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.saputra.adi.orderservice.domain.dto.OrderDtoRq;

public interface IOrder {
    Object inquiry(OrderDtoRq orderDtoRq) throws JsonProcessingException;
    Object order(OrderDtoRq orderDtoRq);
}
