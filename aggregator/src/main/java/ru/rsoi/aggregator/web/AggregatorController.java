package ru.rsoi.aggregator.web;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.aggregator.client.DeliveryFullInformation;

@RestController
@RequestMapping("/agr")
public class AggregatorController {
    @Autowired
    private DeliveryFullInformation deliveryFullService;

    @GetMapping("/deliveries/{del_id}")
    public JSONObject getDeliveryFullInfo(@PathVariable Integer del_id) {
        return deliveryFullService.getDeliveryFullInfo(del_id);
    }

    @DeleteMapping("/delete/users/{user_id}/deliveries/{id}")
    public void deleteDelivery (@PathVariable Integer id)
    {
        deliveryFullService.deleteDelivery(id);
    }
}
