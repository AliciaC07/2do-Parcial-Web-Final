package org.parcial.services;

import org.parcial.models.Url;
import org.parcial.models.Visit;
import org.parcial.repository.Repository;

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

}
