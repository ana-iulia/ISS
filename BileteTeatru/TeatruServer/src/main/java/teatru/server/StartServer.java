package teatru.server;

import netowrk.utils.AbstractServer;
import netowrk.utils.ServerException;
import netowrk.utils.TeatruRpcConcurrentServer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import teatru.persistence.IManagerRepository;
import teatru.persistence.IPerformanceRepository;
import teatru.persistence.IReservationRepository;
import teatru.persistence.ISpectatorRepository;
import teatru.persistence.repository.jdbc.ManagerORMRepository;
import teatru.persistence.repository.jdbc.PerformanceORMRepository;
import teatru.persistence.repository.jdbc.ReservationORMRepository;
import teatru.persistence.repository.jdbc.SpectatorORMRepository;
import teatru.services.ITeatruServices;


import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort=55555;
    static SessionFactory sessionFactory;
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/teatruserver.properties"));
 //           serverProps={"teatru.jdbc.url=jdbc:sqlite:C:\sqlite\SQLiteStudio\Teatru.db", "teatru.jdbc.driver=org.sqlite.JDBC", "teatru.server.port=55556"};
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find teatruserver.properties "+e);
            return;
        }
        //IEmployeeRepository employeeRepo=new EmployeeDBRepository(serverProps);
        IManagerRepository employeeRepo=new ManagerORMRepository(serverProps);
        IPerformanceRepository performanceRepository=new PerformanceORMRepository(serverProps);
        ISpectatorRepository spectatorRepo=new SpectatorORMRepository(serverProps);
        IReservationRepository reservationRepository=new ReservationORMRepository(serverProps);
//        ITicketRepository ticketRepository=new TicketDBRepository(serverProps);
        ITeatruServices teatruServerImpl=new TeatruServiceImpl(employeeRepo,performanceRepository,spectatorRepo,reservationRepository);
//        initialize();
//        System.out.println("session factory: "+sessionFactory);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("teatru.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new TeatruRpcConcurrentServer(chatServerPort, teatruServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
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
