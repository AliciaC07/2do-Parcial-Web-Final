package org.parcial.api;

import io.javalin.Javalin;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.parcial.config.UrlEncodeShort;
import org.parcial.controllers.BaseController;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.models.dto.*;
import org.parcial.services.Principal;
import org.parcial.services.UrlService;
import org.parcial.services.UserService;
import org.parcial.services.VisitService;

import java.time.LocalDate;
import java.util.List;

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
                ModelMapper modelMapper = new ModelMapper();
                List<UrlDto> urlDtos = modelMapper.map(urlService.findAllUrlByUserActiveTrue(id), new TypeToken<List<UrlDto>>() {}.getType());
                ctx.json(urlDtos);
            });

            app.post("/api/url-create", ctx -> {
                UrlDto originalUrl = ctx.bodyAsClass(UrlDto.class);
                Url url = new Url();
                System.out.println(url.getOriginalUrl());
                UrlEncodeShort urlEncodeShort = new UrlEncodeShort();
                String shortened = urlEncodeShort.encodeUrl(originalUrl.getOriginalUrl());
                url.setOriginalUrl(urlEncodeShort.decodeUrl(shortened));
                url.setCuttedUrl(shortened);
                ModelMapper modelMapper = new ModelMapper();
                url.setUser(modelMapper.map(originalUrl.getUser(), User.class));
                String preview = "http://api.linkpreview.net/?key=0cbfe7534103c6303369cf71fbbd5b53&q="+originalUrl.getOriginalUrl();
                String image  = principal.requestImage(preview);
                originalUrl.setPreview(principal.getByteArrayFromImageURL(image));
                if (url.getUser() != null){
                    url.setDateAdded(LocalDate.now());
                    byte [] qrcode = principal.getQRCodeImage("https://"+domain+url.getCuttedUrl(),500,500);
                    url.setQrCode(principal.getQrImageBase64(qrcode));
                }
                urlService.create(url);
                originalUrl = modelMapper.map(url, UrlDto.class);
                originalUrl.setPreview(principal.getByteArrayFromImageURL(image));
                ctx.json(originalUrl);
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
                String header = "Authorization";
                String type = "Bearer";

                if (ctx.req.getHeader(header) == null || !ctx.req.getHeader(header).startsWith(type)){
                    ctx.res.sendError(401, "You don't have authorization");
                }
                String jwt = ctx.req.getHeader(header).replace(type,"");
                Boolean verify = principal.tokenVerify1(jwt);
                System.out.println(verify);
                if (!verify){
                     ctx.json("You don't have authorization");
                     ctx.res.sendError(401, "You don't have authorization");
                }
            });
        });
    }
}
