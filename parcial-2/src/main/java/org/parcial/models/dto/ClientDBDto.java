package org.parcial.models.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDBDto {
    private String originalUrl;
    private String user;
    private String cuttedUrl;
    private String original;
    private String dateAdded;
    private String inServer;
    private Integer id;

    public ClientDBDto() {
    }
}
