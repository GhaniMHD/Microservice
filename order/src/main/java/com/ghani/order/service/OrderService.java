package com.ghani.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ghani.order.model.Order;
import com.ghani.order.repository.OrderRepository;
import com.ghani.order.vo.Produk;
import com.ghani.order.vo.ResponseTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public ResponseTemplate getOrderWithProdukById(Long id) {
        Order order = getOrderById(id);

        if (order == null) {
            return null;
        }

        ResponseTemplate vo = new ResponseTemplate();
        vo.setOrder(order);
        vo.setProduk(getProdukById(order.getIdProduk()));
        return vo;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private Produk getProdukById(Long idProduk) {
        if (idProduk == null) {
            return null;
        }

        List<ServiceInstance> productInstances = discoveryClient.getInstances("PRODUK");
        if (productInstances.isEmpty()) {
            return null;
        }

        String produkUrl = productInstances.get(0).getUri() + "/api/produk/" + idProduk;
        try {
            return restTemplate.getForObject(produkUrl, Produk.class);
        } catch (RestClientException exception) {
            return null;
        }
    }
}
