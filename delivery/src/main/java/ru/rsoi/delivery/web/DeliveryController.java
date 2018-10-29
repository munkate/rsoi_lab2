package ru.rsoi.delivery.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.service.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    public DeliveryService deliveryService;

    @GetMapping("/{id}")
    public DeliveryModel DeliveryById(@PathVariable Integer id) {
        return deliveryService.getDeliveryById(id);
    }

    @GetMapping
    public List<DeliveryModel> findAllDeliveries() {
        return deliveryService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDelivery(@PathVariable Integer id) {
        deliveryService.deleteDeliveryById(id);
    }

    @PostMapping("/createdelivery")
    public void createDelivery(@RequestBody DeliveryModel model) {
        deliveryService.createDelivery(model);
    }

    @PatchMapping("/editdelivery")
    public void editDelivery(@RequestBody DeliveryModel model) {
        deliveryService.editDelivery(model);
    }

    @GetMapping("/users/{id}/deliveries")
    public List<DeliveryModel> findAllDeliveriesById(@PathVariable Integer id) {
        return deliveryService.findAllByUserId(id);
    }
}
