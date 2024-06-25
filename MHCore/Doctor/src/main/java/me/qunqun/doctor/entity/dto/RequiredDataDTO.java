package me.qunqun.doctor.entity.dto;

import lombok.Data;

@Data
public class RequiredDataDTO {
    private Integer id;
    private String name;
    private Boolean isRequired;

    public RequiredDataDTO(int i, String s, boolean b) {
        this.id = i;
        this.name = s;
        this.isRequired = b;
    }

    public RequiredDataDTO() {
    }
}
