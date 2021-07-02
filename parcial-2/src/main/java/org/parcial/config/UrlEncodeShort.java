package org.parcial.config;

import java.util.HashMap;
import java.util.Map;

public class UrlEncodeShort {
    private final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final char[] allowedCharacters = allowedString.toCharArray();
    //en base 62 segun la doc
    private final int base = allowedCharacters.length;
    private Map<String, String> keyUrl ;
    private Map<String, String> urlKey ;

    public UrlEncodeShort() {
        keyUrl = new HashMap<>();
        urlKey = new HashMap<>();
    }

    public String encodeUrl(String originalUrl){
        String urlCuted = "";
        originalUrl = confirmUrl(originalUrl);
        if (urlKey.containsKey(originalUrl)){
            urlCuted = keyUrl.get(originalUrl);
        }else {
            urlCuted = setKey(originalUrl);
        }

        return urlCuted;

    }
    public String decodeUrl(String cuttedUrl){
        String longURL = "";
        String key = cuttedUrl.substring(cuttedUrl.lastIndexOf("/") + 1);
        longURL = keyUrl.get(key);
        return longURL;

    }

    public String ramChar(){
        StringBuilder ramString = new StringBuilder();
        for (int i =0; i < 8; i++){
            ramString.append(allowedString.charAt((int) Math.floor(Math.random() * base)));
        }
        return ramString.toString();
    }
    public String setKey(String originalUrl){
        String key;
        key = ramChar();
        keyUrl.put(key,originalUrl);
        urlKey.put(originalUrl,key);
        return key;
    }
    public String confirmUrl(String originalUrl){
        if (originalUrl.substring(0, 7).equals("http://"))
            originalUrl = originalUrl.substring(7);

        if (originalUrl.substring(0, 8).equals("https://"))
            originalUrl = originalUrl.substring(8);

        if (originalUrl.charAt(originalUrl.length() - 1) == '/')
            originalUrl = originalUrl.substring(0, originalUrl.length() - 1);
        return originalUrl;

    }
}
