package org.parcial.services;

import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UrlService extends Repository<Url> {
    private static  UrlService urlService;

    public UrlService() {
        super(Url.class);
    }
    public static UrlService getInstance(){
        if (urlService == null){
            urlService = new UrlService();
        }
        return urlService;
    }
    public Url findUrlByOriginalUrl(String originalUrl){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select u from Url u where u.originalUrl = :originalurl");
        query.setParameter("originalurl", originalUrl);
        List<Url> url = query.getResultList();
        if (url.isEmpty()){
            System.out.println("Nada");
            return null;
        }
        return  url.get(0);

    }
    public Url findUrlByHash(String hash){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select u from Url u where u.cuttedUrl = :hashed");
        query.setParameter("hashed", hash);
        List<Url> url = query.getResultList();
        if (url.isEmpty()){
            System.out.println("Nada");
            return null;
        }
        return  url.get(0);

    }


}
