package ru.rsoi.shipments.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import ru.rsoi.shipments.entity.enums.Unit;

import javax.persistence.*;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "title")
    private String title;

    @Column(name = "declare_value")
    private Integer declare_value;

    @Column(name = "unit_id")
    @Enumerated(EnumType.ORDINAL)
    private Unit unit_id;

    @Column(name = "uid")
    private long uid;

    @Column(name="delivery_id")
    private Integer del_id;

    public Integer getDel_id() {
        return del_id;
    }

    public void setDel_id(Integer del_id) {
        this.del_id = del_id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Shipment() {

    }

    public Shipment(String title, Integer declare_value, Unit unit_id) {

        this.title = title;
        this.declare_value = declare_value;
        this.unit_id = unit_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
