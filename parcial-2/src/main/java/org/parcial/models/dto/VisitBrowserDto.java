package org.parcial.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitBrowserDto {

    private String browser;
    private Integer quantity;

    public VisitBrowserDto() {
    }
}
