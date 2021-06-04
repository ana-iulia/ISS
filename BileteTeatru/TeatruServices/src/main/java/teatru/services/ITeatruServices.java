package teatru.services;


import teatru.model.Manager;
import teatru.model.Performance;
import teatru.model.Reservation;
import teatru.model.Spectator;

import java.time.LocalDate;
import java.util.List;

public interface ITeatruServices {
    void login(Manager employee, ITeatruObserver client) throws TeatruException;
//    //void sendMessage(Message message) throws ZborException;
//    void logout(Manager employee, ITeatruObserver client) throws TeatruException;
//    //Employee[] getLoggedEmployees(Employee employee) throws ZborException;
////    void modifyFlights
//    void flightsListChange(List<Flight> flights)throws TeatruException;
    Manager getManager(String username,String password) throws TeatruException;
    Spectator getSpectator(String username, String password) throws TeatruException;
    List<Performance> getPerformances()throws TeatruException;


    void addPerformance(String title, LocalDate date, String type, String director, int price, String description) throws TeatruException;

    void deletePerformance(Performance p) throws TeatruException;

    void loginApp(Spectator spectator, ITeatruObserver client)throws TeatruException;

    List<Reservation> getReservations()throws TeatruException;


//    List<Flight> getFlights()throws TeatruException;
//    List<String> getDepartures()throws TeatruException;
//    List<String> getListStringFlights(String destination,LocalDate date)throws TeatruException;
//    Flight createFlight(String destination,LocalDate date,String timeSeats)throws TeatruException;
//    void saveTicket(String destination,LocalDate date,String timeSeats,String clientN,String tour,String adr,Integer seats)throws TeatruException;
}
