package org.parcial.services;

import com.google.zxing.WriterException;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
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
    public List<Url> findAllByActiveTruePagination(int pageSize, int lastPageNumber){
        EntityManager entityManager = getEntityManager();
        Query selectQuery = entityManager.createQuery("select p from Url p where p.active = true");
        selectQuery.setFirstResult((lastPageNumber - 1) * pageSize);
        selectQuery.setMaxResults(pageSize);
        List<Url> lastPage = selectQuery.getResultList();
        return lastPage;

    }
    public List<Url> findAllUrlsByActive(){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT p from Url p WHERE p.active = true", Url.class);
        List<Url> urls = query.getResultList();
        return urls;

    }
    public void changeUrlCreator(User creator, List<Url> urls) throws IOException, WriterException {
        for (Url url : urls) {
            url.setUser(creator);
            byte [] qrcode = Principal.getInstance().getQRCodeImage("https://localhost:7000/url/info/"+url.getId(),100,100);
            url.setQrCode(Principal.getInstance().getQrImageBase64(qrcode));
            urlService.edit(url);
        }

    }
    public List<Url> findUrlsByUserActivePagination(Integer id, int pageSize, int lastPageNumber ){
        EntityManager entityManager = getEntityManager();
        Query selectQuery = entityManager.createQuery("select p from Url p where p.active = true and p.user.id = :iduser");
        selectQuery.setParameter("iduser", id);
        int fr = (lastPageNumber - 1) * pageSize;
        selectQuery.setFirstResult(fr);
        selectQuery.setMaxResults(pageSize);
        List<Url> lastPage = selectQuery.getResultList();
        return lastPage;
    }
    public List<Url> findAllUrlByUserActiveTrue(Integer id){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT p from Url p WHERE p.active = true and p.user.id = :userid");
        query.setParameter("userid", id);
        List<Url> urls = query.getResultList();
        return urls;
    }




}
