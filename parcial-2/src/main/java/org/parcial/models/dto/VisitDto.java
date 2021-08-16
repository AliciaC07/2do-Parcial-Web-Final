package org.parcial.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.parcial.models.Url;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class VisitDto {
    private Integer id;


    private String operatingSystem;


    private String ip;


    private LocalDate date;


    private LocalTime time;


    private String browser;


}
