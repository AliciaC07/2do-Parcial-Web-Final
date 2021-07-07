package org.parcial.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitOperatingSystemDto {

    private String oS;
    private Integer quantity;


    public VisitOperatingSystemDto() {
    }

    public VisitOperatingSystemDto(String oS, Integer quantity) {
        this.oS = oS;
        this.quantity = quantity;
    }
}
