package org.parcial.controllers;


import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;
import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeToken;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.models.dto.ClientDBDto;
import org.parcial.services.Principal;
import org.parcial.services.UrlService;
import org.parcial.services.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WebSocketController extends BaseController{
    public static List<Session> users = new ArrayList<>();
    private String domain = "shortly.traki-tech.games/";

    public WebSocketController(Javalin app) {
        super(app);
    }

    @Override
    public void applyRoutes() {
        app.ws("/connectServer", wsHandler -> {
            wsHandler.onConnect(ctx -> {
                users.add(ctx.session);
                System.out.println("user con-cantidad de usuarios " + users.size());
                ctx.send(1);
            });

            wsHandler.onMessage(ctx -> {
                //System.out.println(ctx.message());
                //ModelMapper modelMapper = new ModelMapper();
                System.out.println("llego un mensaje");
                Type type = new TypeToken<List<ClientDBDto>>(){}.getType();
                List<ClientDBDto> urls = new Gson().fromJson(ctx.message(),type);
                addUrls(parseResponse(urls));
                ctx.send(2);
            });

            wsHandler.onClose(ctx -> {
                users.remove(ctx.session);
                System.out.println("user dis-Cantidad de usuarios "+users.size());
                ctx.send("Desconectado del ws");
            });

            wsHandler.onError(ctx -> {
                System.out.println("An error occurred "+ ctx.error());
                System.out.println("An error occurred "+ ctx.error());
                //users.remove(ctx.session);
            });
        });
    }

    private void addUrls(List<Url> urls){
        for (Url url: urls) {
            UrlService.getInstance().create(url);
            System.out.println(new Gson().toJson(url));
        }
    }

    private List<Url> parseResponse(List<ClientDBDto> response) {
        List<Url> urls = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        for (ClientDBDto parseUrl: response) {
            Url url = new Url();
            if(parseUrl.getInServer().equalsIgnoreCase("false")){
                User user = UserService.getInstance().findByUserName(parseUrl.getUser());
                url.setUser(user);
                url.setCuttedUrl(parseUrl.getCuttedUrl());
                url.setActive(true);
                url.setOriginalUrl(parseUrl.getOriginal());
                url.setDateAdded(LocalDate.parse(parseUrl.getDateAdded(),formatter));
                try {
                    byte[] qrCode = Principal.getInstance().getQRCodeImage("https://" + domain + url.getCuttedUrl(), 500, 500);
                    url.setQrCode(Principal.getInstance().getQrImageBase64(qrCode));
                }catch (IOException | WriterException e){
                    e.printStackTrace();
                }
                urls.add(url);
            }
        }
        return urls;
    }
}
