package teatru.server;



import org.hibernate.service.spi.ServiceException;
import teatru.model.Manager;
import teatru.model.Performance;
import teatru.model.Reservation;
import teatru.model.Spectator;
import teatru.persistence.IManagerRepository;
import teatru.persistence.IPerformanceRepository;
import teatru.persistence.IReservationRepository;
import teatru.persistence.ISpectatorRepository;
import teatru.services.ITeatruObserver;
import teatru.services.ITeatruServices;
import teatru.services.TeatruException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TeatruServiceImpl implements ITeatruServices {

    private IManagerRepository employeeRepository;
    private IPerformanceRepository performanceRepository;
    private ISpectatorRepository spectatorRepository;
    private IReservationRepository reservationRepository;


    //private MessageRepository messageRepository;
    private Map<String, ITeatruObserver> loggedEmployees;
    private Map<String, ITeatruObserver> loggedSpectators;


    public TeatruServiceImpl(IManagerRepository employeeRepository,IPerformanceRepository performanceRepository,ISpectatorRepository spectatorRepository,IReservationRepository reservationRepository) {
        this.employeeRepository=employeeRepository;
        this.performanceRepository=performanceRepository;
        this.spectatorRepository=spectatorRepository;
        this.reservationRepository=reservationRepository;
        //userRepository= uRepo;
        //messageRepository=mRepo;
        loggedEmployees =new ConcurrentHashMap<>();
        loggedSpectators =new ConcurrentHashMap<>();
    }

//    @Override
//    public void flightsListChange(List<Flight> flights) throws ZborException {
////        String id_receiver=message.getReceiver().getId();
////        IChatObserver receiverClient=loggedClients.get(id_receiver);
////        if (receiverClient!=null) {      //the receiver is logged in
////            messageRepository.save(message);
////            receiverClient.messageReceived(message);
////        }
////        else
////            throw new ChatException("User "+id_receiver+" not logged in.");
//    }

    public Manager getManager(String username, String password){


        for (Manager m:employeeRepository.findAll()) {
            if(m.getUsername().equals(username))
                return m;
        }

        return null;
    }

    @Override
    public Spectator getSpectator(String username, String password) throws TeatruException {

        return spectatorRepository.findOne(username);
    }

    @Override
    public synchronized List<Performance> getPerformances() throws TeatruException {
        List<Performance> performances=new ArrayList<>();
        System.out.println("performance: ");
        for (Performance p:performanceRepository.findAll()) {

                performances.add(p);
        }
        System.out.println("+++++++++++++Performances in serverImpl: "+performances);
        return performances;
    }
//    @Override
//    public boolean equalDate(LocalDate date){
//        for (Performance p:performanceRepository.findAll()) {
//            if(p.getDate().equals(date)) {
//                return true;
//            }
//        }
//        return false;
//    }
    @Override
    public void addPerformance(String title, LocalDate date, String type, String director, int price, String description) throws TeatruException {
        for (Performance p:performanceRepository.findAll()) {
            if(p.getDate().equals(date)) {
                throw new TeatruException("");
            }
        }
        Performance performance=new Performance(title,title,date,type,director,price,description);
        performanceRepository.save(performance);
        System.out.println("A salvat ====> performance");
//TO DO: NOTIFICARE pt clienti
//        Ticket ticket=new Ticket(flight,clientName,tourists,address,seats);
//            ticket.setId(id);
//            ticketRepository.save(ticket);
//        System.out.println("A salvat ====> notificare");
//            NotifyNewTicket(ticket);
//            System.out.println("A notificat!");
    }

    @Override
    public void deletePerformance(Performance p) throws TeatruException {
        System.out.println("ServiceImpl:---------------");
        System.out.println("Perf: "+p);
        for (Performance performance:performanceRepository.findAll()) {
            System.out.println("before deleting!!! "+p);
            System.out.println("Perforances compared: "+performance);
            if(performance.equals(p) ) {
                System.out.println("Found equal: "+performance);
                //&& !performance.getDate().equals(LocalDate.now())
                performanceRepository.delete(p);
                System.out.println("deleted!!!!");
                break;
            }
//            else{
//                throw new TeatruException("");
//            }
        }
    }

    @Override
    public void loginApp(Spectator spectator, ITeatruObserver client) throws TeatruException {
        Spectator spectatorR=spectatorRepository.findOne(spectator.getId());
        System.out.println("---------------------------------------------------------------------");
        System.out.println("AICI"+spectatorR);

        if (spectatorR!=null){
            if(loggedSpectators.get(spectatorR.getId())!=null) {
                throw new TeatruException("Spectator already logged in.");
            }
            loggedEmployees.put(spectatorR.getId(), client);
            //notifyEmployeesLoggedIn(employee);
            System.out.println(loggedEmployees);
        }else
            throw new TeatruException("Authentication failed.");
    }

    @Override
    public List<Reservation> getReservations() throws TeatruException {
        List<Reservation> reservations=new ArrayList<>();
        System.out.println("performance: ");
        for (Reservation r:reservationRepository.findAll()) {

            reservations.add(r);
        }
        System.out.println("+++++++++++++Performances in serverImpl: "+reservations);
        return reservations;
    }


//    public synchronized List<Flight> getFlights(){
//        List<Flight> flights=new ArrayList<>();
//        for (Flight f:flightRepository.findAll()) {
//            flights.add(f);
//        }
//
//        return flights;
//    }

//
//    public synchronized List<String> getDepartures(){
//        List<String> departures=new ArrayList<>();
//        System.out.println(flightRepository.findAll());
//        for (Flight f:flightRepository.findAll()) {
//            if(!departures.contains(f.getDestination())){
//                departures.add(f.getDestination());
//            }
//
//        }
//        return departures;
//    }
//
//
//    public synchronized List<String> getListStringFlights(String destination, LocalDate date) throws ZborException {
//        List<String> flights = new ArrayList<>();
//
//        for (Flight f : flightRepository.findAll()) {
//            if (f.getDestination().equals(destination) && f.getDepartureDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(date.toString())) {
//
//                String fl = "Ora plecarii->"+f.getDepartureDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))+","+" Locuri disponibile->"+f.getSeats();
//                flights.add(fl);
//            }
//        }
//
//        return flights;
//    }
//
//
//    public synchronized Flight createFlight(String destination, LocalDate date, String timeSeats) {
//        Flight flight=null;
//        List<String> to_list = Arrays.asList(timeSeats.split(","));
//        List<String> to_listHour=Arrays.asList(to_list.get(0).split("->"));
//        String hour=to_listHour.get(1);
//        for (Flight f:flightRepository.findAll()) {
//            if(f.getDestination().equals(destination) && f.getDepartureDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(date.toString()) && f.getDepartureDateTime().format(DateTimeFormatter.ofPattern("HH:mm")).equals(hour)){
//                flight=f;
//            }
//
//        }
//
//        return flight;
//    }
//
//
//    public synchronized void saveTicket(String destination, LocalDate date, String timeSeats,String clientName, String tourists, String address, Integer seats)throws ZborException{
//        Flight flight=getFlight(destination,date,timeSeats);
//
//            updateFlights(flight,seats);
//            int id=0;
//            for (Ticket t:ticketRepository.findAll()) {
//                if(t.getId()>id)
//                    id=t.getId();
//            }
//            id++;
//            Ticket ticket=new Ticket(flight,clientName,tourists,address,seats);
//            ticket.setId(id);
//            ticketRepository.save(ticket);
//        System.out.println("A salvat ====> notificare");
//            NotifyNewTicket(ticket);
//            System.out.println("A notificat!");
//
//
//    }
//    public Flight getFlight(String destination, LocalDate date, String timeSeats) {
//        Flight flight=null;
//        List<String> to_list = Arrays.asList(timeSeats.split(","));
//        List<String> to_listHour=Arrays.asList(to_list.get(0).split("->"));
//        String hour=to_listHour.get(1);
//        for (Flight f:flightRepository.findAll()) {
//            if(f.getDestination().equals(destination) && f.getDepartureDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(date.toString()) && f.getDepartureDateTime().format(DateTimeFormatter.ofPattern("HH:mm")).equals(hour)){
//                flight=f;
//            }
//
//        }
//
//        return flight;
//    }
//
//    public void updateFlights(Flight flight,int seats){
//
//        int remainedSeats=flight.getSeats()-seats;
//        if(remainedSeats>0){
//            Flight flight2=new Flight(flight.getId(),flight.getDestination(),flight.getDepartureDateTime(),flight.getAirport(),remainedSeats);
//            flightRepository.update(flight2);
//
//        }
//        else if(remainedSeats==0){
//            flightRepository.delete(flight.getId());
//
//        }
//
//    }


    public synchronized void login(Manager employee, ITeatruObserver client) throws TeatruException {
        Manager employeeR=employeeRepository.findOne(employee.getId());
        System.out.println("---------------------------------------------------------------------");
        System.out.println("AICI"+employeeR);

        if (employeeR!=null){
            if(loggedEmployees.get(employeeR.getId())!=null) {
                throw new TeatruException("Employee already logged in.");
            }
            loggedEmployees.put(employeeR.getId(), client);
            //notifyEmployeesLoggedIn(employee);
            System.out.println(loggedEmployees);
        }else
            throw new TeatruException("Authentication failed.");
    }
    private final int defaultThreadsNo=5;
//    private final int defaultThreadsNo=5;
//    private void notifyEmployeesLoggedIn(Employee employee) throws ZborException {
//        //Iterable<User> friends=userRepository.getFriendsOf(user);
//        System.out.println("Logged "+employeeRepository.findAll());
//
//        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
//        for(User us :friends){
//            IZborObserver zborClient=loggedEmployees.get(us.getId());
//            if (chatClient!=null)
//                executor.execute(() -> {
//                    try {
//                        System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
//                        zborClient.friendLoggedIn(user);
//                    } catch (ChatException e) {
//                        System.err.println("Error notifying friend " + e);
//                    }
//                });
//        }
//
//        executor.shutdown();
//    }


//    public synchronized void logout(Employee employee, IZborObserver client) throws ZborException {
//        System.out.println("-------------------------------------Before:"+loggedEmployees);
//        IZborObserver localZbor=loggedEmployees.remove((employee.getId()));
//        System.out.println("After:"+loggedEmployees);
//        System.out.println("After:"+localZbor);
//        if(localZbor==null)
//            throw  new ZborException("User "+ employee.getUsername()+" nu este logat!");
//    }

//    @Override
//    public Employee[] getLoggedEmployees(Employee employee) throws ZborException {
//        return new Employee[0];
//    }

//    private void NotifyNewTicket(Ticket ticket) throws ZborException {
//
//
//        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
//        for(Employee u:employeeRepository.findAll()){
//            IZborObserver client=loggedEmployees.get(u.getId());
//            System.out.println("CLIENT:"+client);
//            if (client!=null)
//                executor.execute(() -> {
//                    try {
//                        System.out.println("---------Notifying [" + u.getId()+ "] flight inregistrat.--------------");
//
//                        client.ticketInregistrat(ticket.getFlight().getId());
//                    } catch (ZborException e) {
//                        System.err.println("Error notifying friend " + e);
//                    }
//                });
//        }
//
//        executor.shutdown();
//    }
}
