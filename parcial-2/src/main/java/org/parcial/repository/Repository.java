package org.parcial.repository;

import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

public class Repository <T> {
    private static EntityManagerFactory entityManagerFactory;

    private Class<T> entityClass;

    public Repository(Class<T> entityClass) {
        if(entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("MyPersistentUnit");
        }
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public T create(T entity){
        EntityManager entityManager = getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();

        }finally {
            entityManager.close();

        }
        return entity;
    }

    public T edit(T entity){
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        }finally {
            entityManager.close();
        }
        return entity;
    }

    public boolean delete(Object entityId){
        boolean status = false;
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        try{
            T entity = entityManager.find(entityClass, entityId);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            status = true;
        }finally {
            entityManager.close();
        }
        return status;
    }

    public T find(Object objectId){
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(entityClass, objectId);
        }finally {
            entityManager.close();
        }
    }

    public List<T> findAll() throws PersistenceException {
        EntityManager entityManager = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.select(criteriaQuery.from(entityClass));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } finally {
            entityManager.close();
        }

    }

}
