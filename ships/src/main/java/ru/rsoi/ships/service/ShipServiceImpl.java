package ru.rsoi.ships.service;


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
import org.springframework.stereotype.Service;
import ru.rsoi.ships.entity.Ship;
import ru.rsoi.ships.entity.enums.ShipType;
import ru.rsoi.ships.model.ShipInfo;
import ru.rsoi.ships.repository.ShipRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

@Service
public class ShipServiceImpl implements ShipService {
    private static final String RESOURCE_ID = "ships";
    private static final String RESOURCE_SECRET = "ships";
    private String CLIENT_ID = "gateway";
    private String CLIENT_SECRET = "gateway";
    private long EXPIRATION = 1000*60*30;

    @Autowired
    private ShipRepository shipRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipServiceImpl.class);


    @Override
    public boolean checkClient(String client_id, String client_secret){
        return client_id.equals(CLIENT_ID) && client_secret.equals(CLIENT_SECRET);

    }
    public String createJWT() {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(RESOURCE_SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
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
    public boolean parseJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(RESOURCE_SECRET))
                .parseClaimsJws(jwt).getBody();
        long d = System.currentTimeMillis();
        return claims.getAudience().equals(CLIENT_ID) && claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }


    @Override
    public void editShip(ShipInfo shipInfo) {
       try{
           Ship ship = null;
           try {ship = getEntity(shipInfo);}
           catch ( NullPointerException ignored){

           }
            BeanUtils.copyProperties(shipInfo,ship);
            shipRepository.save(ship);}
            catch (RuntimeException e){
            LOGGER.error("Failed update ship uid={}",shipInfo.getUid());
            throw new RuntimeException("DAO failed", e);
        }

    }


    @Override
    public Page<ShipInfo> listAllByPage(Pageable pageable) {
      try{  return shipRepository.findAll(pageable).map(this::buildModel);}
                catch (RuntimeException e){
          LOGGER.error("Failed to get all ships");
          return null;
                }
    }

    @Override
    public void delete(long id) {
     try{   shipRepository.deleteByUid(id);
        LOGGER.info("Ship deleted.");}
        catch (RuntimeException e)
        {
            LOGGER.error("Failed to delete ship with id={}",id);
        }
    }

    @Override
    public ShipInfo getById(long id) {
      try  {
          ShipInfo model = buildModel(shipRepository.findByUid(id));
       return model;}
       catch (RuntimeException e)
       {
           LOGGER.error("Failed to get ship with id={}",id);
           return null;
       }

    }

    @Override
    public long createShip(ShipInfo shipInfo) {

      try{
          long range = 1234567L;
          Random r = new Random();
          Ship ship = new Ship(shipInfo.getSh_title(), shipInfo.getSkipper(), shipInfo.getYear(), shipInfo.getCapacity(), shipInfo.getType_id(), (long)(r.nextDouble()*range));
        shipRepository.save(ship);
        LOGGER.info("Ship created.");
      return ship.getUid();}
        catch(RuntimeException e) {
          LOGGER.error("Failed to create ship");
          return 0;
        }
    }


    private ShipInfo buildModel(Ship ship) {
        ShipInfo model = new ShipInfo();
       try{ BeanUtils.copyProperties(ship, model);
        return model;}
        catch (RuntimeException e){
           LOGGER.error("Failed to build model");
           return null;
        }
    }


    private Ship getEntity(ShipInfo model) {
       try { Ship ship = shipRepository.findByUid(model.getUid());
        return ship;}
        catch (RuntimeException e)
        {
            LOGGER.error("Failed to get entity");
            return null;
        }
    }

    @Override
    public ShipInfo getModelFromHashMap(LinkedHashMap<String,Object> model){
        try {   ShipInfo shipInfo = new ShipInfo((String)model.get("title"), (String)model.get("skipper"),
                (Integer)model.get("year"), (Integer)model.get("capacity"), ShipType.valueOf((Integer)model.get("type_id")), (Integer)model.get("uid"));

            LOGGER.info("Successful getting model from hashmap");
            return shipInfo;
        }
        catch (RuntimeException e){
            LOGGER.error("Failed to get model from hashmap");
            return null;
        }
    }
}
