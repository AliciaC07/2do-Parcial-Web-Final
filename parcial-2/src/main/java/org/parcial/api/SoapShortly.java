package org.parcial.api;

import com.google.zxing.WriterException;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.models.dto.UrlDto;
import org.parcial.models.dto.VisitBrowserDto;
import org.parcial.models.dto.VisitOperatingSystemDto;
import org.parcial.models.dto.VisitsClickDto;
import org.parcial.services.Principal;
import org.parcial.services.UrlService;
import org.parcial.services.UserService;
import org.parcial.services.VisitService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebService
public class SoapShortly {

    private UrlService urlService = UrlService.getInstance();
    private Principal principal = Principal.getInstance();
    private String domain = "shortly.traki-tech.games/";
    private VisitService visitService = VisitService.getInstance();
    private UserService userService = UserService.getInstance();

    @WebMethod
    public List<Url> getUrlsByUser(Integer id){
        return urlService.findAllUrlByUserActiveTrue(id);
    }
    @WebMethod
    public UrlDto createUrl(UrlDto url) throws IOException, WriterException {
        Url urlCreate = new Url();
        urlCreate.setCuttedUrl(url.getCuttedUrl());
        urlCreate.setOriginalUrl(url.getOriginalUrl());
        urlCreate.setDateAdded(LocalDate.parse(url.getDateAdded()));
        if (url.getUser() != null){
            urlCreate.setUser(url.getUser());
        }
        byte [] qrcode = principal.getQRCodeImage("https://"+domain+url.getCuttedUrl(),500,500);
        urlCreate.setQrCode(principal.getQrImageBase64(qrcode));

        urlService.create(urlCreate);
        return url;

    }
    @WebMethod
    public List<VisitOperatingSystemDto> getVisitOS(){
        return visitService.getQuantityByOperatingSystem();
    }

    @WebMethod
    public List<VisitBrowserDto> getVisitB(){
        return visitService.getQuantityByBrowser();
    }

    @WebMethod
    public User login(User user){
        return userService.authUser(user.getUserName(), user.getPassword());
    }

    @WebMethod
    public VisitsClickDto getClicks(Integer id){
        User user = userService.findById(id);
        VisitsClickDto vo = new VisitsClickDto();
        vo.setTotalClicks(user.getTotalCliks());
        vo.setTotalMonth(user.getQuantityPerMonth());
        return vo;
    }




}
