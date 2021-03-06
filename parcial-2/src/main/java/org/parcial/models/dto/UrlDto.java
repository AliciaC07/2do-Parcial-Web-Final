package org.parcial.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.parcial.models.User;
import org.parcial.models.Visit;
import org.parcial.services.Principal;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UrlDto {
    private Integer id;

    private String originalUrl;

    private String cuttedUrl;


    private UserDto user;


    private Boolean active = true;


    private String qrCode;


    private String dateAdded;

    private String preview;

    private InfoDto infoDto;


    private Set<VisitDto> visits = new HashSet<>();
}
