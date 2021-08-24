package org.parcial.controllers;

import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.parcial.models.Url;
import org.parcial.services.UrlService;

import java.util.ArrayList;
import java.util.List;

public class WebSocketController extends BaseController{
    public static List<Session> users = new ArrayList<>();

    public WebSocketController(Javalin app) {
        super(app);
    }

    @Override
    public void applyRoutes() {
        app.ws("/connectServer", wsHandler -> {
            wsHandler.onConnect(ctx -> {
                users.add(ctx.session);
            });

            wsHandler.onMessage(ctx -> {
                ModelMapper modelMapper = new ModelMapper();
                List<Url> urls = modelMapper.map(ctx.message(), new TypeToken<List<Url>>() {}.getType());
                addUrls(urls);
            });

            wsHandler.onClose(ctx -> {
                users.remove(ctx.session);
            });

            wsHandler.onError(ctx -> {
                System.out.println("An error occurred");
            });
        });
    }

    private void addUrls(List<Url> urls){
        for (Url url: urls) {
            UrlService.getInstance().create(url);
        }
    }
}
