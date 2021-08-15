package org.parcial.api;

import io.javalin.Javalin;
import org.modelmapper.ModelMapper;
import org.parcial.controllers.BaseController;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.models.dto.InfoDto;
import org.parcial.models.dto.LoginDto;
import org.parcial.models.dto.UserLogin;
import org.parcial.models.dto.VisitOperatingSystemDto;
import org.parcial.services.Principal;
import org.parcial.services.UrlService;
import org.parcial.services.UserService;
import org.parcial.services.VisitService;

public class RestShortlyController extends BaseController {

    private UrlService urlService = UrlService.getInstance();
    private Principal principal = Principal.getInstance();
    private String domain = "shortly.traki-tech.games/";
    private VisitService visitService = VisitService.getInstance();
    private UserService userService = UserService.getInstance();

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
            app.get("/api/visits-info/:id", ctx -> {
                Integer id = ctx.pathParam("id", Integer.class).get();
                InfoDto infoDto = new InfoDto();
                infoDto.setVisitBrowserDto(visitService.getQuantityByBrowser(id));
                infoDto.setVisitOperatingSystemDto(visitService.getQuantityByOperatingSystem(id));
                ctx.json(infoDto);
            });
            app.post("/login-rest", ctx -> {
                LoginDto userLogin = ctx.bodyAsClass(LoginDto.class);
                User user = userService.authUser(userLogin.getUserName(), userLogin.getPassword());
                if (user != null){
                    UserLogin userLogin1 = new UserLogin();
                    userLogin1.setPassword(user.getPassword());
                    userLogin1.setUserName(userLogin.getUserName());
                    userLogin1.setRol(user.getRol());
                    userLogin1.setId(user.getId());
                    userLogin1.setToken(principal.tokenCreated2(user));
                    ctx.json(userLogin1);

                }else {
                    ctx.json("User not found");
                }

            });
            app.before("/api/*", ctx -> {

            });



        });

    }
}
