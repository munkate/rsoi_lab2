package ru.rsoi.delivery.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.rsoi.delivery.entity.Delivery;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.repository.DeliveryRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService{



    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);
    private static final String RESOURCE_ID = "deliveries";
    private static final String RESOURCE_SECRET = "deliveries";
    private String CLIENT_ID = "gateway";
    private String CLIENT_SECRET = "gateway";
    private long EXPIRATION = 1000000*60*30;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public boolean checkClient(String client_id, String client_secret){
        return client_id.equals(CLIENT_ID) && client_secret.equals(CLIENT_SECRET);

    }
    @Override
    public String createJWT() {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(RESOURCE_SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setIssuer(RESOURCE_ID)
                .setAudience(CLIENT_ID)
                .signWith(signatureAlgorithm, signingKey);
        long expMillis = nowMillis + EXPIRATION;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        return builder.compact();
    }
    @Override
    public boolean parseJWT(String jwt) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(RESOURCE_SECRET))
                .parseClaimsJws(jwt).getBody();
        return claims.getAudience().equals(CLIENT_ID) && claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    @Override
    public long createDelivery(DeliveryModel delivery) {
        try{
        Delivery new_delivery = new Delivery(delivery.getDeparture_date(), delivery.getArrive_date(),
                delivery.getOrigin(), delivery.getDestination(), delivery.getShip_id(), delivery.getUser_id(), delivery.getUid());

        deliveryRepository.save(new_delivery);
        LOGGER.info("Delivery created.");
        return new_delivery.getUid();
        }
        catch (RuntimeException e){
            LOGGER.error("Delivery didn't create.");
            return 0;
        }
    }
    @Override
    public DeliveryModel getModelFromHashMap(LinkedHashMap<String,Object> model) throws ParseException {
     try {   DeliveryModel del = new DeliveryModel();
       DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        del.setUid(Integer.parseInt(model.get("uid").toString()));
        del.setOrigin((String)model.get("origin"));
        del.setDestination((String)model.get("destination"));
        del.setUser_id(Integer.parseInt(model.get("user_id").toString()));
        del.setShip_id(Integer.parseInt(model.get("ship_id").toString()));
        del.setDeparture_date(format.parse((String)model.get("departure_date")));
        del.setArrive_date(format.parse((String)model.get("arrive_date")));
        LOGGER.info("Successful getting model from hashmap");
       return del;
     }
       catch (RuntimeException e){
         LOGGER.error("Failed to get model from hashmap");
         return null;
       }
    }


    @Override
    public DeliveryModel getDeliveryById(long id) {
       try{
           DeliveryModel model = buildModel( deliveryRepository.findByUid(id));
           return model;
       }
       catch(RuntimeException e)
       {
           LOGGER.error("Delivery with id={} didn't find",id);
           return null;
       }

        }


    @Override
    public Page<DeliveryModel> findAll(Pageable pageable) {
      try {  return deliveryRepository.findAll(pageable).map(this::buildModel);}
      catch (RuntimeException e){
          LOGGER.error("Failed to get all deliveries.");
          return null;
                }
    }

    @Override
    public Page<DeliveryModel> findAllByUserId(Integer id, Pageable pageable) {
        try {
            return deliveryRepository.findAllByUserId(id,pageable).map(this::buildModel);
        }
        catch (RuntimeException e){
            LOGGER.error("Failed to get all user's deliveries.");
            return null;
        }
    }

    @Override
    public void deleteDeliveryByUid(long id) {

        try{deliveryRepository.deleteByUid(id);}
        catch (RuntimeException e) {
            LOGGER.error("Failed to delete delivery with id={}",id);
        }


    }

    @Override
    public void editDelivery(DeliveryModel delivery) {

             try {
                Delivery new_delivery = getEntity(delivery);
                 assert new_delivery != null;
                 BeanUtils.copyProperties(delivery, new_delivery);

                deliveryRepository.save(new_delivery);
            }
            catch (RuntimeException e) {
                LOGGER.error("Delivery didn't update");
                throw new RuntimeException("DAO failed", e);
            }

    }

    @NonNull
    private DeliveryModel buildModel(Delivery delivery) {
        DeliveryModel model = new DeliveryModel();
     try {   BeanUtils.copyProperties(delivery,model);
        return model;}
        catch (RuntimeException e){
         LOGGER.error("Failed build model");
        }
        return null;
    }


    private Delivery getEntity(DeliveryModel model) {
        try {
            Delivery delivery = deliveryRepository.findByUid(model.getUid());
            return delivery;
        } catch (RuntimeException e){
            throw new RuntimeException("Failed to get entity.");
        }

    }

}
