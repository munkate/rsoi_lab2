package ru.rsoi.gateway.web;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.gateway.client.DeliveryFullInformation;
import ru.rsoi.models.DeliveryModel;

@RestController
@RequestMapping("/agr")
public class AggregatorController {
    @Autowired
    private DeliveryFullInformation deliveryFullService;

    @GetMapping("/users/{user_id}/deliveries")
    public ResponseEntity<JSONObject> getUserDeliveriesFullInfo(@PathVariable Integer user_id, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(deliveryFullService.getUserDeliveriesFullInfo(user_id,request));
    }
    @GetMapping("/users/{user_id}/deliveries/{del_id}")
    public ResponseEntity<JSONObject> getUserDeliveryFullInfo(@PathVariable Integer del_id) {
        return ResponseEntity.ok(deliveryFullService.getDeliveryFullInfo(del_id));
    }

    @DeleteMapping("/delete/users/{user_id}/deliveries/{id}")
    public ResponseEntity<Void> deleteDelivery (@PathVariable Integer id)
    {

        deliveryFullService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{user_id}/delivery")
    public ResponseEntity<Void> createDelivery(@RequestBody JSONObject data)
    {
        deliveryFullService.createDelivery(data);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/editdelivery")
    public ResponseEntity<Void> editDelivery(@RequestBody JSONObject model) {

        deliveryFullService.editDelivery(model);
        return ResponseEntity.ok().build();
    }
}
