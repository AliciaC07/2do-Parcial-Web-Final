package org.parcial.api;

import com.google.zxing.WriterException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.parcial.config.UrlEncodeShort;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.models.dto.*;
import org.parcial.services.Principal;
import org.parcial.services.UrlService;
import org.parcial.services.UserService;
import org.parcial.services.VisitService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebService
public class SoapShortly {

    private UrlService urlService = UrlService.getInstance();
    private Principal principal = Principal.getInstance();
    private String domain = "shortly.traki-tech.games/";
    private VisitService visitService = VisitService.getInstance();
    private UserService userService = UserService.getInstance();

    @WebMethod
    public List<UrlDto> getUrlsByUser(Integer id){
        ModelMapper modelMapper = new ModelMapper();
        List<UrlDto> urls = modelMapper.map(urlService.findAllUrlByUserActiveTrue(id), new TypeToken<List<UrlDto>>() {}.getType());
        for (var url: urls) {
            url.setInfoDto(principal.getStatics(url.getId()));
        }
        return urls;
    }
    @WebMethod
    public UrlDto createUrl(String originalUrl, Integer id) throws IOException, WriterException, InterruptedException {
        Url urlnew = new Url();
        UrlEncodeShort urlEncodeShort = new UrlEncodeShort();
        urlnew.setCompleteUrl(originalUrl);
        String shortened = urlEncodeShort.encodeUrl(originalUrl);
        urlnew.setOriginalUrl(urlEncodeShort.decodeUrl(shortened));
        urlnew.setCuttedUrl(shortened);
        String preview = "http://api.linkpreview.net/?key=0cbfe7534103c6303369cf71fbbd5b53&q="+originalUrl;
        String image  = principal.requestImage(preview);

        if (id != null){
            User user = userService.findById(id);
            urlnew.setUser(user);
            urlnew.setDateAdded(LocalDate.now());
            byte [] qrcode = principal.getQRCodeImage("https://"+domain+urlnew.getCuttedUrl(),500,500);
            urlnew.setQrCode(principal.getQrImageBase64(qrcode));
            urlService.create(urlnew);
        }else {
            urlnew.setDateAdded(LocalDate.now());
            byte [] qrcode = principal.getQRCodeImage("https://"+domain+urlnew.getCuttedUrl(),500,500);
            urlnew.setQrCode(principal.getQrImageBase64(qrcode));
            urlService.create(urlnew);
        }
        ModelMapper modelMapper = new ModelMapper();
        UrlDto urlDto = modelMapper.map(urlnew, UrlDto.class);
        urlDto.setPreview(principal.getByteArrayFromImageURL(image));
        return urlDto;

    }
    @WebMethod
    public InfoDto getVisitInfo(Integer idUrl){
        InfoDto infoDto = new InfoDto();
        infoDto.setVisitBrowserDto(visitService.getQuantityByBrowser(idUrl));
        infoDto.setVisitOperatingSystemDto(visitService.getQuantityByOperatingSystem(idUrl));
        return infoDto;
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
