package teatru.persistence.repository.jdbc;




import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import teatru.model.Manager;
import teatru.model.Performance;
import teatru.persistence.IPerformanceRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class PerformanceORMRepository implements IPerformanceRepository {
    JdbcUtils jdbcUtils;
    static SessionFactory sessionFactory;

    public PerformanceORMRepository(Properties jdbcProps){
            jdbcUtils=new JdbcUtils(jdbcProps);
            initialize();
            System.out.println("session factory: "+sessionFactory);

        }



//        @Override
//        public Performance findPerformance(String username, String password) {
//            List<Manager> employees=null;
//            try(Session session = sessionFactory.openSession()) {
//                Transaction tx = null;
//                try {
//                    tx = session.beginTransaction();
//                    employees =
//                            session.createQuery("FROM Manager WHERE username ="+ username.toString(), Manager.class).list();
//
////                                setFirstResult(10).setMaxResults(5).
////                                list();
//                    System.out.println("employees: "+employees);
////                for (Message m : messages) {
////                    System.out.println(m.getText() + ' ' + m.getId());
////                }
//                    tx.commit();
//                } catch (RuntimeException ex) {
//                    if (tx != null)
//                        tx.rollback();
//                }
//            }
//            return employees.get(0);
//        }

        @Override
        public Performance findOne(String id) {
            //List<Employee> employees=null;
            Performance performance=new Performance();
            System.out.println("Before transaction!");
            try(Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    performance =
                            session.createQuery("from Performance where title = ?1", Performance.class)
                                    .setParameter(1,id)
                                    .setMaxResults(1)
                                    .uniqueResult();

//.setParameter(s,s)
//                                setFirstResult(10).setMaxResults(5).
//                                list();
                    System.out.println("-----------performance: "+performance);
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
            performance.setId(performance.getTitle());

            return performance;
        }

        @Override
        public Iterable<Performance> findAll() {
            List<Performance> performances=new ArrayList<>();
            try(Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    performances =
                            session.createQuery("FROM Performance", Performance.class).list();

//                                setFirstResult(10).setMaxResults(5).
//                                list();
                    System.out.println("----- Performances: "+performances);
//                for (Message m : messages) {
//                    System.out.println(m.getText() + ' ' + m.getId());
//                }
                    tx.commit();
                } catch (RuntimeException ex) {
                    if (tx != null)
                        tx.rollback();
                }
            }
            for (Performance e:performances) {
                e.setId(e.getTitle());
            }
            return performances;

        }

        @Override
        public void save(Performance entity) {
            try(Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    session.save(entity);
                    tx.commit();
                } catch (RuntimeException ex) {
                    if (tx != null)
                        tx.rollback();
                }
            }
            System.out.println("++++++++++Performance added");

        }

        @Override
        public void delete(String id) {

        }

        @Override
        public void update(Performance entity) {

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
