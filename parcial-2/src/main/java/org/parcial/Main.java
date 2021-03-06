package org.parcial;

import com.google.zxing.WriterException;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinVelocity;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.parcial.Server.UrlServer;
import org.parcial.api.RestShortlyController;
import org.parcial.controllers.*;
import org.parcial.models.User;
import org.parcial.services.UserService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, WriterException,InterruptedException {
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/public"));
            config.addStaticFiles("/public");
            config.enableCorsForAllOrigins();
            JavalinRenderer.register(JavalinVelocity.INSTANCE, ".html");

        });
        org.parcial.services.BootStrapService.startDb();
        new SoapController(app).applyRoutes();
        new RestShortlyController(app).applyRoutes();
        app.start(7001);

        //app.get("/", ctx -> ctx.result("Hola Mundo en Javalin :-D"));
        new UserController(app).applyRoutes();
        new VisitController(app).applyRoutes();
        new WebSocketController(app).applyRoutes();
        new ShortenerController(app).applyRoutes();

        StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
        User user = new User(null, "alicruz0703@gmail.com", spe.encryptPassword("123"), "Admin");
        UserService.getInstance().create(user);
        User otro = new User(null, "123@gmail.com", spe.encryptPassword("123"),"User");
        UserService.getInstance().create(otro);
        User otro1 = new User(null, "1234@gmail.com", spe.encryptPassword("123"),"User");
        UserService.getInstance().create(otro1);
        User otro2 = new User(null, "1235@gmail.com", spe.encryptPassword("123"),"User");
        UserService.getInstance().create(otro2);
        User otro3 = new User(null, "1236@gmail.com", spe.encryptPassword("123"),"User");
        UserService.getInstance().create(otro3);
        User otro4 = new User(null, "1237@gmail.com", spe.encryptPassword("123"),"User");
        UserService.getInstance().create(otro4);
        User otro5 = new User(null, "12378@gmail.com", spe.encryptPassword("123"),"User");
        UserService.getInstance().create(otro5);
        User otro6 = new User(null, "12379@gmail.com", spe.encryptPassword("123"),"User");
        UserService.getInstance().create(otro6);
        app.get("/",ctx -> {
            ctx.redirect("shortener/shorty");
        });
        app.after(ctx -> {
            //System.out.println("Enviando el header de seguridad para el Service Worker");
            ctx.header("Service-Worker-Allowed", "/");
        });

        UrlServer server = new UrlServer();
        server.start();
        server.blockUntilShutdown();



    }
}
