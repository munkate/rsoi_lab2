package ru.rsoi.aggregator.web;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
