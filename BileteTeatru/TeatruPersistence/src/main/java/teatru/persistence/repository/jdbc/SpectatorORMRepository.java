package teatru.persistence.repository.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import teatru.model.Manager;
import teatru.model.Spectator;
import teatru.persistence.IPerformanceRepository;
import teatru.persistence.ISpectatorRepository;

import java.util.List;
import java.util.Properties;

public class SpectatorORMRepository implements ISpectatorRepository {
    JdbcUtils jdbcUtils;
    static SessionFactory sessionFactory;

    public SpectatorORMRepository(Properties jdbcProps){
        jdbcUtils=new JdbcUtils(jdbcProps);
        initialize();
        System.out.println("session factory: "+sessionFactory);

    }

    @Override
    public Spectator findOne(String s) {
        //List<Employee> employees=null;
        Spectator spectator=new Spectator();
        System.out.println("Before transaction!");
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
               spectator =
                        session.createQuery("from Spectator where username = ?1", Spectator.class)
                                .setParameter(1,s)
                                .setMaxResults(1)
                                .uniqueResult();

//.setParameter(s,s)
//                                setFirstResult(10).setMaxResults(5).
//                                list();
                System.out.println("-----------spectator: "+spectator);
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
        spectator.setId(spectator.getUsername());

        return spectator;
    }

    @Override
    public Iterable<Spectator> findAll() {
        return null;
    }

    @Override
    public void save(Spectator entity) {

    }

    @Override
    public void delete(Spectator entity) {

    }

    @Override
    public void update(Spectator entity) {

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

    @Override
    public Spectator findSpectator(String username, String password) {
        List<Spectator> employees=null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                employees =
                        session.createQuery("FROM Spectator WHERE username ="+ username.toString(), Spectator.class).list();

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
}
