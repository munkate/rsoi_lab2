package ru.rsoi.ships.model;

import ru.rsoi.ships.entity.enums.ShipType;

import java.util.UUID;

public class ShipInfo {

    private String sh_title;

    private String skipper;

    private Integer year;

    private Integer capacity;

    private long uid;

    private ShipType type_id;

    public ShipInfo(){}

    public ShipInfo(String title, String skipper, Integer year, Integer capacity, ShipType type, long uid) {
        this.sh_title = title;
        this.skipper = skipper;
        this.year = year;
        this.capacity = capacity;
        this.type_id = type;
        this.uid = uid;
    }

    public String getSh_title() {
        return sh_title;
    }

    public void setSh_title(String sh_title) {
        this.sh_title = sh_title;
    }

    public String getSkipper() {
        return skipper;
    }

    public void setSkipper(String skipper) {
        this.skipper = skipper;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public ShipType getType_id() {
        return type_id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setType_id(ShipType type_id) {
        this.type_id = type_id;
    }
}

