import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class Main {
    static EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("xxx");
    static EntityManager entityManager = managerFactory.createEntityManager();

    public static void main(String[] args) {

        entityManager.getTransaction().begin();
        deleteFromDbByName("user");







//        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("xxx");
//
//       EntityManager entityManager = managerFactory.createEntityManager();
//        entityManager.persist(createUser());

//        TypedQuery<User> select_u_from_user_u = entityManager.createQuery("select u from User u", User.class);
//        List<User> resultList = select_u_from_user_u.getResultList();
//        System.out.println(resultList);

        /*User user = entityManager.find(User.class, 1);

        entityManager.remove(user);*/

        entityManager.getTransaction().commit();
        entityManager.close();

        managerFactory.close();


    }

    public static User createUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter user name");
        String name = scanner.nextLine();
        System.out.println("enter user surname");
        String surname = scanner.nextLine();
        System.out.println("enter user email");
        String email = scanner.nextLine();
        String phone = fillPhoneNumber();
        String login = name + "." + surname + "." + phone.substring(9,11);
        String password = new StringBuilder(name).reverse().toString();

        return new User(name, surname, email, login, password, phone);
    }

    public static String fillPhoneNumber(){
        String res = "";
        for (int i = 0; i < 11; i++) {
            long var = Math.round(Math.random()*9);
            res = res + var;
        }
        return res;
    }

    public static void showAllUsers(){
        TypedQuery<User> query = entityManager.createQuery("select u from User u", User.class);
        List<User> resultList = query.getResultList();
        System.out.println(resultList);
    }

    public static void showUsersById(int id){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :x", User.class);
        query.setParameter("x", id);
        List<User> resultList = query.getResultList();
        System.out.println(resultList);

//        User user = entityManager.find(User.class, id);
//        System.out.println(user);
    }

    public static void showUserByName(String name){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :z", User.class);
        query.setParameter("z", name);
        List<User> resultList = query.getResultList();
        System.out.println(resultList);
    }

    public static void deleteFromDbById(int id){
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        if (entityManager.find(User.class, id) == null){
            System.out.println(" element was removed");
        }
    }

    public static void deleteFromDbByName(String name){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
        query.setParameter("name", name);
        List<User> resultList = query.getResultList();
        for (User user : resultList) {
            entityManager.remove(user);
        }
    }
}
