package org.parcial.services;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.parcial.config.JwtGen;
import org.parcial.models.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Principal {
    private static Principal principal = null;

    StrongPasswordEncryptor spe = new StrongPasswordEncryptor();

    private Principal() {

    }

    public static Principal getInstance(){
        if(principal == null){
            principal = new Principal();
        }
        return principal;
    }
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public String tokenCreated(User user){
        String jwtId = "PacoFish";
        String jwtIssuer = "JWT Gen";
        String jwtSubject = user.getUserName();
        int jwtTimeToLive = 2000000;

        return JwtGen.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );
    }
    public String tokenCreated2(User user){
        String jwtId = "PacoFish";
        String jwtIssuer = "JWT Gen";
        String jwtSubject = user.getUserName();
        int jwtTimeToLive = 1000000;

        return JwtGen.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );
    }
    public Boolean tokenVerify(String userName, String jwt){
        String jwtId = "PacoFish";
        String jwtIssuer = "JWT Gen";
        try{
            if (jwt != null){
                Claims claims = JwtGen.decodeJWT(jwt);
                if (claims.getId().equals(jwtId) && claims.getIssuer().equals(jwtIssuer)
                        && claims.getSubject().equals(userName)){
                    return true;
                }else {
                    return false;
                }
            }


        }catch (JwtException e){
            System.out.println("Token is invalid");
        }

        return false;
    }
    public Boolean tokenVerify1(String jwt){
        String jwtId = "PacoFish";
        String jwtIssuer = "JWT Gen";
        try{
            if (jwt != null){
                Claims claims = JwtGen.decodeJWT(jwt);
                if (claims.getId().equals(jwtId) && claims.getIssuer().equals(jwtIssuer)){
                    return true;
                }else {
                    return false;
                }
            }


        }catch (JwtException e){
            System.out.println("Token is invalid");
        }

        return false;
    }
    public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
    public String getQrImageBase64(byte[] pgnData){
        return Base64.getEncoder().encodeToString(pgnData);
    }

    public String getByteArrayFromImageURL(String url) {

        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            System.out.println("Error"+e.toString());
        }
        return " ";
    }
    public String requestImage(String url1) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url1))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonElement jsonElement = new JsonParser().parse(response.body());

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        //System.out.println( jsonObject.get("image") );

        return jsonObject.get("image").getAsString();

    }

}
