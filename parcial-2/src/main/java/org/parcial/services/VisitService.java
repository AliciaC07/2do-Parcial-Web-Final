package org.parcial.services;

import org.parcial.models.Visit;
import org.parcial.models.dto.VisitBrowserDto;
import org.parcial.models.dto.VisitOperatingSystemDto;
import org.parcial.models.dto.VisitsDateDto;
import org.parcial.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VisitService extends Repository<Visit> {
    private static  VisitService visitService;

    public VisitService() {
        super(Visit.class);
    }
    public static VisitService getInstance(){
        if (visitService == null){
            visitService = new VisitService();
        }
        return visitService;
    }
    public List<VisitsDateDto> getAllQuantityByDate(){
        List<Object[]> visit = new ArrayList<>();
        List<VisitsDateDto> visitsQuantity = new ArrayList<>();
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select date, count(id) as quantity " +
                "from Visit WHERE date >= :today" +
                " or date >= :yesterday or date >= :pyesterday " +
                "group by date");
        query.setParameter("today",LocalDate.now());
        query.setParameter("yesterday", LocalDate.now().minusDays(1));
        query.setParameter("pyesterday",LocalDate.now().minusDays(2));
        visit = query.getResultList();
        for (Object[] obj: visit) {
            VisitsDateDto vso = new VisitsDateDto();
            vso.setDate(LocalDate.parse(obj[0].toString()));
            vso.setQuantity(Integer.parseInt(obj[1].toString()));
            visitsQuantity.add(vso);
        }

        return visitsQuantity;


    }
    //select OPERATINGSYSTEM, count(ID) as Cantidad from VISIT  group by VISIT.OPERATINGSYSTEM
    public List<VisitOperatingSystemDto> getQuantityByOperatingSystem(){
        List<Object[]> visit = new ArrayList<>();
        List<VisitOperatingSystemDto> visitsQuantity = new ArrayList<>();
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select p.operatingSystem, count(p.id) as Cantidad from Visit p group by p.operatingSystem");
        visit = query.getResultList();
        if (visit.isEmpty()){
            VisitOperatingSystemDto v = new VisitOperatingSystemDto();
            v.setOS("Operating System");
            v.setQuantity(0);
            visitsQuantity.add(v);
        }else {
            for (Object[] obj: visit) {
                VisitOperatingSystemDto vso = new VisitOperatingSystemDto();
                vso.setOS(obj[0].toString());
                vso.setQuantity(Integer.parseInt(obj[1].toString()));
                visitsQuantity.add(vso);
            }

        }

        return visitsQuantity;


    }
    public List<VisitBrowserDto> getQuantityByBrowser(){
        List<Object[]> visit = new ArrayList<>();
        List<VisitBrowserDto> visitsQuantity = new ArrayList<>();
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select p.browser, count(p.id) as Cantidad from Visit p group by p.browser");
        visit = query.getResultList();
        if (visit.isEmpty()){
            VisitBrowserDto v = new VisitBrowserDto();
            v.setBrowser("Browser");
            v.setQuantity(0);
            visitsQuantity.add(v);

        }else {
            for (Object[] obj: visit) {
                VisitBrowserDto vso = new VisitBrowserDto();
                vso.setBrowser(obj[0].toString());
                vso.setQuantity(Integer.parseInt(obj[1].toString()));
                visitsQuantity.add(vso);
            }
        }


        return visitsQuantity;


    }


}
