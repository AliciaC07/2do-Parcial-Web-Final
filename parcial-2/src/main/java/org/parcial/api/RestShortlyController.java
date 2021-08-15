package org.parcial.api;

import io.javalin.Javalin;
import org.parcial.controllers.BaseController;
import org.parcial.models.Url;
import org.parcial.services.Principal;
import org.parcial.services.UrlService;

public class RestShortlyController extends BaseController {

    private UrlService urlService = UrlService.getInstance();
    private Principal principal = Principal.getInstance();
    private String domain = "shortly.traki-tech.games/";

    public RestShortlyController(Javalin app){
        super(app);
    }

    @Override
    public void applyRoutes() {

        app.routes(() -> {
            app.get("/api/urls/:id", ctx -> {
                Integer id = ctx.pathParam("id", Integer.class).get();
                ctx.json(urlService.findAllUrlByUserActiveTrue(id));
            });

            app.post("/api/url-create", ctx -> {
                Url url = ctx.bodyAsClass(Url.class);
                System.out.println(url.getOriginalUrl());
                byte [] qrcode = principal.getQRCodeImage("https://"+domain+url.getCuttedUrl(),500,500);
                url.setQrCode(principal.getQrImageBase64(qrcode));
                ctx.json(urlService.create(url));
            });
            app.get("/api/visits-os", ctx -> {

            });
        });

    }
}
