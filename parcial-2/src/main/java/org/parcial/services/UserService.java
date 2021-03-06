package org.parcial.services;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.parcial.models.User;
import org.parcial.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserService  extends Repository<User> {
    private static  UserService userService;
    StrongPasswordEncryptor spe = new StrongPasswordEncryptor();

    public UserService() {
        super(User.class);
    }
    public static UserService getInstance(){
        if(userService == null){
            userService = new UserService();
        }
        return userService;
    }
    public User findByUserName(String username){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select u from app_user u where u.userName = :username and u.active = true ");
        query.setParameter("username", username);
        List<User> user = query.getResultList();
        if (user.isEmpty()){
            System.out.println("Nada");
            return null;
        }
        return  user.get(0);

    }
    public User findById(Integer id){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select u from app_user u where u.id = :id and u.active = true ");
        query.setParameter("id", id);
        List<User> user = query.getResultList();
        if (user.isEmpty()){
            System.out.println("Nada");
            return null;
        }
        return  user.get(0);

    }
    public List<User> findAllUsersByActive(){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT p from app_user p WHERE p.active = true", User.class);
        List<User> users = query.getResultList();
        return users;

    }
    public List<User> findAllByActiveTruePagination(int pageSize, int lastPageNumber){
        EntityManager entityManager = getEntityManager();
        Query selectQuery = entityManager.createQuery("select p from app_user p where p.active = true");
        selectQuery.setFirstResult((lastPageNumber - 1) * pageSize);
        selectQuery.setMaxResults(pageSize);
        List<User> lastPage = selectQuery.getResultList();
        return lastPage;

    }
    public User authUser(String userName, String password){
        User user = findByUserName(userName);
        if (user == null){
            return null;
        }
        if (spe.checkPassword(password, user.getPassword())){
            return user;
        }
        return null;

    }

}
