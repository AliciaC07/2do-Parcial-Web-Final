package org.parcial.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InfoDto {

    private List<VisitOperatingSystemDto> visitOperatingSystemDto;
    private List<VisitBrowserDto> visitBrowserDto;
}
