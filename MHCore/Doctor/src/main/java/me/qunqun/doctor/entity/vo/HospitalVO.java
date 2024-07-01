package me.qunqun.doctor.entity.vo;

import lombok.Data;
import me.qunqun.shared.entity.po.Hospital;

@Data
public class HospitalVO {
    private Integer id;
    private String name;
    private String picture;
    private String address;
    private String telephone;
    private String deadline;
    private String businessHours;
    private String rule;
    private Integer state;


    public HospitalVO fromEntity(Hospital hospital) {
        this.id = hospital.getId();
        this.name = hospital.getName();
        this.picture = hospital.getPicture();
        this.address = hospital.getAddress();
        this.telephone = hospital.getTelephone();
        this.deadline = hospital.getDeadline();
        this.businessHours = hospital.getBusinessHours();
        this.rule = hospital.getRule();
        this.state = hospital.getState();
        return this;
    }

    public Hospital toEntity() {
        Hospital hospital = new Hospital();
        hospital.setId(this.id);
        hospital.setName(this.name);
        hospital.setPicture(this.picture);
        hospital.setAddress(this.address);
        hospital.setTelephone(this.telephone);
        hospital.setDeadline(this.deadline);
        hospital.setBusinessHours(this.businessHours);
        hospital.setRule(this.rule);
        hospital.setState(this.state);
        return hospital;
    }
}
