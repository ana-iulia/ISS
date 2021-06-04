package teatru.persistence.repository.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import teatru.model.Performance;
import teatru.model.Reservation;
import teatru.persistence.IPerformanceRepository;
import teatru.persistence.IReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReservationORMRepository implements IReservationRepository {
    JdbcUtils jdbcUtils;
    static SessionFactory sessionFactory;

    public ReservationORMRepository(Properties jdbcProps){
        jdbcUtils=new JdbcUtils(jdbcProps);
        initialize();
        System.out.println("session factory: "+sessionFactory);

    }

    @Override
    public Reservation findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Reservation> findAll() {
        List<Reservation> reservations=new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
               reservations =
                        session.createQuery("FROM Reservation", Reservation.class).list();

//                                setFirstResult(10).setMaxResults(5).
//                                list();
                System.out.println("----- Performances: "+reservations);
//                for (Message m : messages) {
//                    System.out.println(m.getText() + ' ' + m.getId());
//                }
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        for (Reservation e:reservations) {
            e.setId(e.getNumber());
        }
        return reservations;
    }

    @Override
    public void save(Reservation entity) {

    }

    @Override
    public void delete(Reservation entity) {

    }

    @Override
    public void update(Reservation entity) {

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
