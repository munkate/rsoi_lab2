package ru.rsoi.ships.service;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipServiceImpl.class);


    @Override
    public void editShip(ShipInfo shipInfo) {
       try{ Ship ship = getEntity(shipInfo);
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

      try{  Ship ship = new Ship(shipInfo.getSh_title(), shipInfo.getSkipper(), shipInfo.getYear(), shipInfo.getCapacity(), shipInfo.getType_id(), shipInfo.getUid());
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
