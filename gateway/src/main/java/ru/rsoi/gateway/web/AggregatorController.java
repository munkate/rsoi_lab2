package ru.rsoi.gateway.web;

import com.netflix.zuul.context.RequestContext;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.gateway.client.DeliveryFullInformation;
import ru.rsoi.models.DeliveryModel;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/agr")
@CrossOrigin
public class AggregatorController {
    @Autowired
    private DeliveryFullInformation deliveryFullService;

    @GetMapping("/users/{user_id}/deliveries")
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<JSONObject> getUserDeliveriesFullInfo(@PathVariable Integer user_id, @RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size, @RequestHeader(value = "usertoken",required = false) String token, final HttpServletRequest httpRequest) {
        if (null!=httpRequest.getHeader("Authorization")||null!=token&&deliveryFullService.checkUserToken(token)){
        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(deliveryFullService.getUserDeliveriesFullInfo(user_id,request, token));
        }
        else return ResponseEntity.badRequest().body(new JSONObject());
    }
    @GetMapping("/users/{user_id}/deliveries/{del_id}")
  //  @PreAuthorize("isAuthenticated()")
    public ResponseEntity<JSONObject> getUserDeliveryFullInfo(@PathVariable Integer del_id,@RequestHeader(value ="usertoken",required = false) String token, final HttpServletRequest httpRequest) {
        if (null!=httpRequest.getHeader("Authorization")||null!=token&&deliveryFullService.checkUserToken(token)){
        return ResponseEntity.ok(deliveryFullService.getDeliveryFullInfo(del_id,token));}
        else return ResponseEntity.badRequest().body(new JSONObject());
    }

    @DeleteMapping("/delete/users/{user_id}/deliveries/{id}")
   // @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteDelivery (@PathVariable Integer id,@RequestHeader(value ="usertoken",required = false) String token, final HttpServletRequest httpRequest)
    {
        if (null!=httpRequest.getHeader("Authorization")||null!=token&&deliveryFullService.checkUserToken(token)){
        deliveryFullService.deleteDelivery(id,token);
        return ResponseEntity.noContent().build();}
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/users/{user_id}/delivery")
    public ResponseEntity<Void> createDelivery(@RequestBody JSONObject data,@RequestHeader(value ="usertoken",required = false) String token, final HttpServletRequest httpRequest)
    {
        if (null!=httpRequest.getHeader("Authorization")||null!=token&&deliveryFullService.checkUserToken(token)){

        deliveryFullService.createDelivery(data,token);
        return ResponseEntity.ok().build();}
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/editdelivery")
    public ResponseEntity<Void> editDelivery(@RequestBody JSONObject model,@RequestHeader(value ="usertoken", required = false) String token, final HttpServletRequest httpRequest) {
        if (null != httpRequest.getHeader("Authorization")||null!=token&&deliveryFullService.checkUserToken(token)){
        deliveryFullService.editDelivery(model,token);
        return ResponseEntity.ok().build();}
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/ships")
    public ResponseEntity<JSONObject> findAllShips(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(deliveryFullService.getShips(request));
    }

    @GetMapping("/ships/{ship_id}")
    public ResponseEntity<JSONObject> findShipById(@PathVariable Integer ship_id){
        return ResponseEntity.ok(deliveryFullService.getShipById(ship_id));
    }

    @DeleteMapping("/ships/delete/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Integer id,@RequestHeader(value ="usertoken", required = false) String token, final HttpServletRequest httpRequest){
        if (null!= httpRequest.getHeader("Authorization")||null!=token&&deliveryFullService.checkUserToken(token)){
        deliveryFullService.deleteShip(id,token);
        return ResponseEntity.ok().build();}
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/ships/ship")
    public ResponseEntity<Void> createShip(@RequestBody JSONObject data,@RequestHeader(value ="usertoken", required = false) String token, final HttpServletRequest httpRequest){
        if (null!=httpRequest.getHeader("Authorization")||null!=token&&deliveryFullService.checkUserToken(token)){
        deliveryFullService.createShip(data,token);
        return ResponseEntity.ok().build();}
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/edit/ship")
    public ResponseEntity<Void> editShip(@RequestBody JSONObject data,@RequestHeader(value ="usertoken", required = false) String token, final HttpServletRequest httpRequest){
        if (null!=httpRequest.getHeader("Authorization")||null!=token&&deliveryFullService.checkUserToken(token)){
            deliveryFullService.editShip(data,token);
            return ResponseEntity.ok().build();}
        else return ResponseEntity.badRequest().build();
    }

}
