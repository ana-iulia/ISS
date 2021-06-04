package teatru.persistence.repository.jdbc;

import teatru.model.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import teatru.persistence.IManagerRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ManagerORMRepository implements IManagerRepository {

    JdbcUtils jdbcUtils;
    static SessionFactory sessionFactory;

    public ManagerORMRepository(Properties jdbcProps){
        jdbcUtils=new JdbcUtils(jdbcProps);
        initialize();
        System.out.println("session factory: "+sessionFactory);

    }



    @Override
    public Manager findManager(String username, String password) {
        List<Manager> employees=null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                employees =
                        session.createQuery("FROM Manager WHERE username ="+ username.toString(), Manager.class).list();

//                                setFirstResult(10).setMaxResults(5).
//                                list();
                System.out.println("employees: "+employees);
//                for (Message m : messages) {
//                    System.out.println(m.getText() + ' ' + m.getId());
//                }
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return employees.get(0);
    }

    @Override
    public Manager findOne(String s) {
        //List<Employee> employees=null;
        Manager employee=new Manager();
        System.out.println("Before transaction!");
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                employee =
                        session.createQuery("from Manager where username = ?1", Manager.class)
                        .setParameter(1,s)
                        .setMaxResults(1)
                        .uniqueResult();

//.setParameter(s,s)
//                                setFirstResult(10).setMaxResults(5).
//                                list();
                System.out.println("-----------employee: "+employee);
//                for (Message m : messages) {
//                    System.out.println(m.getText() + ' ' + m.getId());
//                }
                tx.commit();
                System.out.println("After commit");
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        employee.setId(employee.getUsername());

        return employee;
    }

    @Override
    public Iterable<Manager> findAll() {
        List<Manager> employees=new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                employees =
                        session.createQuery("FROM Manager", Manager.class).list();

//                                setFirstResult(10).setMaxResults(5).
//                                list();
                System.out.println("-----Employees: "+employees);
//                for (Message m : messages) {
//                    System.out.println(m.getText() + ' ' + m.getId());
//                }
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        for (Manager e:employees) {
            e.setId(e.getUsername());
        }
        return employees;
    }

    @Override
    public void save(Manager entity) {

    }

    @Override
    public void delete(Manager entity) {

    }

    @Override
    public void update(Manager entity) {

    }

    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }



}
