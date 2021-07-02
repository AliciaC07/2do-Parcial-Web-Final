package org.parcial.services;

import org.parcial.models.CookieVerification;
import org.parcial.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookieVerificationService extends Repository<CookieVerification> {

    private static  CookieVerificationService cookieVerificationService;

    public CookieVerificationService() {
        super(CookieVerification.class);
    }
    public static CookieVerificationService getInstance(){
        if(cookieVerificationService == null){
            cookieVerificationService = new CookieVerificationService();
        }
        return cookieVerificationService;
    }
    public Map<String, String> findByCookieToken(String token){
        EntityManager entityManager = getEntityManager();
        Map<String, String> cookie = new HashMap<>();
        Query query = entityManager.createQuery("select c from CookieVerification c where c.token = :token");
        query.setParameter("token", token);
        List<CookieVerification> cookieVerifications = query.getResultList();
        if (cookieVerifications.isEmpty()){
            System.out.println("Nada");
            return cookie;
        }

       cookie.put("user", cookieVerifications.get(0).getUsername());
       cookie.put("token", cookieVerifications.get(0).getToken());
       cookie.put("id", cookieVerifications.get(0).getId().toString());
       return cookie;

    }
    public Map<String, String> findByCookieUsername(String username){
        EntityManager entityManager = getEntityManager();
        Map<String, String> cookie = new HashMap<>();
        Query query = entityManager.createQuery("select c from CookieVerification c where c.username = :username");
        query.setParameter("username", username);
        List<CookieVerification> cookieVerifications = query.getResultList();
        if (cookieVerifications.isEmpty()){
            System.out.println("Nada");
            return cookie;
        }

        cookie.put("user", cookieVerifications.get(0).getUsername());
        cookie.put("token", cookieVerifications.get(0).getToken());

        return cookie;

    }
    public CookieVerification findByCookieTokenVeri(String token){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select c from CookieVerification c where c.token = :token");
        query.setParameter("token", token);
        List<CookieVerification> cookieVerifications = query.getResultList();
        if (cookieVerifications.isEmpty()){
            System.out.println("Nada");
            return cookieVerifications.get(0);
        }

        return cookieVerifications.get(0);

    }
}
