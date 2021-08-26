package org.parcial.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.parcial.models.Url;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private Integer id;

    private String userName;

    private String password;

    private String rol;

    private Boolean active = true;

}
