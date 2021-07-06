package org.parcial.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VisitsDateDto {

    private LocalDate date;
    private Integer quantity;

    public VisitsDateDto() {
    }

    public VisitsDateDto(LocalDate date, Integer quantity) {
        this.date = date;
        this.quantity = quantity;
    }
}
