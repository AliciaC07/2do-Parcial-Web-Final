package org.parcial.api;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.parcial.config.UrlEncodeShort;
import org.parcial.models.User;
import org.parcial.models.dto.InfoDto;
import org.parcial.models.dto.UrlDto;
import org.parcial.models.dto.UserDto;
import org.parcial.services.Principal;
import org.parcial.services.UrlService;
import org.parcial.services.UserService;
import org.parcial.services.VisitService;
import url.Url;
import url.UrlServiceGrpc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class gRPCshortlyController  extends UrlServiceGrpc.UrlServiceImplBase {

    private VisitService visitService = VisitService.getInstance();
    private UserService userService = UserService.getInstance();
    private Principal principal = Principal.getInstance();
    private String domain = "shortly.traki-tech.games/";
    private UrlService urlService = UrlService.getInstance();

    @Override
    public void getAllUrl(Url.UrlRequest request, StreamObserver<Url.UrlResponse> responseObserver) {
        ModelMapper modelMapper = new ModelMapper();
        List<UrlDto> urlDtos = modelMapper.map(urlService.findAllUrlByUserActiveTrue(request.getId()), new TypeToken<List<UrlDto>>() {}.getType());

        responseObserver.onNext(parseAllUrl(urlDtos));
        responseObserver.onCompleted();
    }

    @SneakyThrows
    @Override
    public void createUrl(Url.CreateUrlRequest request, StreamObserver<Url.CreateUrlResponse> responseObserver) {
        ModelMapper modelMapper = new ModelMapper();
        org.parcial.models.Url url = new org.parcial.models.Url();
        UrlDto urlDto;
        url.setOriginalUrl(request.getOriginalUrl());
        UrlEncodeShort urlEncodeShort = new UrlEncodeShort();
        url.setCuttedUrl(urlEncodeShort.encodeUrl(url.getOriginalUrl()));
        User creatorUser = userService.find(request.getIdUser());
        url.setUser(creatorUser);
        if (url.getUser() != null){
            url.setDateAdded(LocalDate.now());
            byte [] qrcode = principal.getQRCodeImage("https://"+domain+url.getCuttedUrl(),500,500);
            url.setQrCode(principal.getQrImageBase64(qrcode));
        }
        urlService.create(url);
        urlDto = modelMapper.map(url,UrlDto.class);
        urlDto.setPreview(principal.getByteArrayFromImageURL(principal.requestImage("http://api.linkpreview.net/?key=0cbfe7534103c6303369cf71fbbd5b53&q="+url.getOriginalUrl())));

        responseObserver.onNext(parseCreate(urlDto));
        responseObserver.onCompleted();
    }

    @Override
    public void getUrlInfo(Url.UrlInfoRequest request, StreamObserver<Url.UrlInfoResponse> responseObserver) {
        InfoDto infoDto = new InfoDto();

        infoDto.setVisitBrowserDto(visitService.getQuantityByBrowser(request.getId()));
        infoDto.setVisitOperatingSystemDto(visitService.getQuantityByOperatingSystem(request.getId()));

        responseObserver.onNext(parseInfo(infoDto));
        responseObserver.onCompleted();
    }

    private Url.UrlResponse parseAllUrl(List<UrlDto> urlDtos){
        return Url.UrlResponse.newBuilder().setUrls(new Gson().toJson(urlDtos)).build();
    }

    private Url.CreateUrlResponse parseCreate(UrlDto urlDto){
        return Url.CreateUrlResponse.newBuilder().setUrl(new Gson().toJson(urlDto)).build();
    }

    private Url.UrlInfoResponse parseInfo(InfoDto infoDto){
        return Url.UrlInfoResponse.newBuilder().setInfoUrl(new Gson().toJson(infoDto)).build();
    }
}
