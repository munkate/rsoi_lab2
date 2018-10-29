package ru.rsoi.models;

import ru.rsoi.models.enums.Unit;

public class ShipmentInfo {


    private String title;

    private Integer declare_value;

    private Unit unit_id;

    private long uid;

    public Integer getDel_id() {
        return del_id;
    }

    public void setDel_id(Integer del_id) {
        this.del_id = del_id;
    }

    private Integer del_id;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDeclare_value() {
        return declare_value;
    }

    public void setDeclare_value(Integer declare_value) {
        this.declare_value = declare_value;
    }

    public Unit getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Unit unit_id) {
        this.unit_id = unit_id;
    }
}
