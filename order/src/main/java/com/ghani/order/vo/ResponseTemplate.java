package com.ghani.order.vo;

import com.ghani.order.model.Order;

import lombok.Data;

@Data
public class ResponseTemplate {
    Order order;
    Produk produk;
}
