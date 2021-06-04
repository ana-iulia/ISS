package netowrk.rpcprotocol;



import netowrk.dto.ManagerDTO;
import netowrk.dto.SpectatorDTO;
import teatru.model.Manager;
import teatru.model.Performance;
import teatru.model.Reservation;
import teatru.model.Spectator;
import teatru.services.ITeatruObserver;
import teatru.services.ITeatruServices;
import teatru.services.TeatruException;

import javax.swing.text.TableView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TeatruServicesRpcProxy implements ITeatruServices {
    private String host;
    private int port;

    private ITeatruObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;

    private volatile boolean finished;
    public TeatruServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }
//    @Override
//    public void flightsListChange(List<Flight> flights) throws ZborException {
//
//    }

    @Override
    public Manager getManager(String username, String password) throws TeatruException {
        String s = username+";"+password;
        Request req = new Request.Builder().type(RequestType.GET_EMPLOYEE).data(s).build();
        sendRequest(req);
        System.out.println("Cerere employee trimisa");
        Response response = readResponse();
        System.out.println("raspuns employee result"+response);
        if (response.type() == ResponseType.ERROR) {
            throw new TeatruException("Error");
        }
        Manager manager = (Manager) response.data();
        return manager;
    }

    @Override
    public Spectator getSpectator(String username, String password) throws TeatruException {
        String s=username+";"+password;
        Request req = new Request.Builder().type(RequestType.GET_SPECTATOR).data(s).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            throw new TeatruException("Error");
        }
        Spectator employee = (Spectator) response.data();
        return employee;
    }

    @Override
    public List<Performance> getPerformances() throws TeatruException {
        Request req = new Request.Builder().type(RequestType.GET_PERFORMANCES).build();
        sendRequest(req);
        System.out.println("Cerere performances trimisa");
        Response response = readResponse();
        System.out.println("raspuns performances"+response);
        if (response.type() == ResponseType.ERROR) {
            throw new TeatruException("Error");
        }
        System.out.println("Inainte: "+response.data());
        List<Performance> performances = (List<Performance>) response.data();
        return performances;
    }



    @Override
    public void addPerformance(String title, LocalDate date, String type, String director, int price, String description) throws TeatruException {
        String s = title+";"+date.toString() + ";" + type+";"+director+";"+price+";"+description;
        Request req = new Request.Builder().type(RequestType.SAVE_PERFORMANCE).data(s).build();
        sendRequest(req);
        System.out.println("new performance trimisa");

        Response response = readResponse();
        System.out.println("new performance result"+response);
        if (response.type() == ResponseType.ERROR) {
            throw new TeatruException("Exista deja un spectcol in data: "+date.toString()+" !\n");
        }
        //Flight flight = (Flight) response.data();
    }

    @Override
    public void deletePerformance(Performance p) throws TeatruException {
        String s = p.getTitle()+";"+p.getDate().toString() + ";" + p.getType()+";"+p.getDirector()+";"+p.getPrice()+";"+p.getDescription();
        Request req = new Request.Builder().type(RequestType.DELETE_PERFORMANCE).data(s).build();
        sendRequest(req);
        System.out.println("del performance trimisa "+s);

        Response response = readResponse();
        System.out.println("del performance result"+response);
        if (response.type() == ResponseType.ERROR) {
            throw new TeatruException("Nu se poate sterge spectacolul: "+p.getTitle()+" !\n");
        }
    }

    @Override
    public void loginApp(Spectator spectator, ITeatruObserver client) throws TeatruException {
        initializeConnection();
        //EmployeeDTO udto= new EmployeeDTO(username,password);
        Request req=new Request.Builder().type(RequestType.LOGINAPP).data(spectator).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new TeatruException(err);
        }
    }

    @Override
    public List<Reservation> getReservations() throws TeatruException {
        Request req = new Request.Builder().type(RequestType.GET_RESERVATIONS).build();
        sendRequest(req);
        System.out.println("Cerere res trimisa");
        Response response = readResponse();
        System.out.println("raspuns res"+response);
        if (response.type() == ResponseType.ERROR) {
            throw new TeatruException("Error");
        }
        System.out.println("Inainte: "+response.data());
        List<Reservation> reservations = (List<Reservation>) response.data();
        return reservations;
    }


//    public List<Flight> getFlights() throws ZborException {
////        Request req = new Request.Builder().type(RequestType.GET_FLIGHTS).build();
////        sendRequest(req);
////        Response response = readResponse();
////        if (response.type() == ResponseType.ERROR) {
////            throw new ZborException("Error");
////        }
////        List<String> flights = (List<String>) response.data();
////        System.out.println(flights);
////        return null;
//
//        Request req = new Request.Builder().type(RequestType.GET_FLIGHTS).build();
//        sendRequest(req);
//        System.out.println("Cerere flights trimisa");
//        Response response = readResponse();
//        System.out.println("raspuns flights"+response);
//        if (response.type() == ResponseType.ERROR) {
//            throw new ZborException("Error");
//        }
//        System.out.println("Inainte: "+response.data());
//        List<Flight> flights = (List<Flight>) response.data();
//        return flights;
//    }

//
//    public List<String> getDepartures() throws ZborException {
//        Request req = new Request.Builder().type(RequestType.GET_DEPARTURES).data(null).build();
//        sendRequest(req);
//        System.out.println("Cerere departure trimisa");
//        Response response = readResponse();
//        System.out.println("raspuns departure"+response);
//
//
//        if (response.type() == ResponseType.ERROR) {
//            throw new ZborException("Error");
//        }
//        List<String> departures = (List<String>) response.data();
//        return departures;
//    }

//    @Override
//    public List<String> getListStringFlights(String destination, LocalDate date) throws ZborException {
//        String s = destination + ";" + date;
//        Request req = new Request.Builder().type(RequestType.GET_FLIGHTS_RESUT).data(s).build();
//        sendRequest(req);
//        System.out.println("Cerere result flights trimisa pentru: "+destination+" "+date);
//        Response response = readResponse();
//        System.out.println("raspuns flights result"+response);
//        if (response.type() == ResponseType.ERROR) {
//            throw new ZborException("Error");
//        }
//        List<String> flights = (List<String>) response.data();
//        return flights;
//    }
//
//    @Override
//    public Flight createFlight(String destination, LocalDate date, String timeSeats)throws ZborException {
//        String s = destination+";"+date + ";" + timeSeats;
//        Request req = new Request.Builder().type(RequestType.CREATE_FLIGHT).data(s).build();
//        sendRequest(req);
//        System.out.println("Cerere flight trimisa");
//        Response response = readResponse();
//        System.out.println("raspuns flight result"+response);
//        if (response.type() == ResponseType.ERROR) {
//            throw new ZborException("Error");
//        }
//        Flight flight = (Flight) response.data();
//        return flight;
//
//    }
//
//    @Override
//    public void saveTicket(String destination, LocalDate date, String timeSeats, String clientN, String tour, String adr, Integer seats) throws ZborException {
//        String s = destination+";"+date + ";" + timeSeats+";"+clientN+";"+tour+";"+adr+";"+seats;
//        Request req = new Request.Builder().type(RequestType.NEW_TICKET).data(s).build();
//        sendRequest(req);
//        System.out.println("new ticket trimisa");
//
//        Response response = readResponse();
//        System.out.println("new ticket result"+response);
//        if (response.type() == ResponseType.ERROR) {
//            throw new ZborException("Error");
//        }
//        //Flight flight = (Flight) response.data();
//
//    }

    public void login(Manager employee, ITeatruObserver client) throws TeatruException {
        initializeConnection();
        //EmployeeDTO udto= new EmployeeDTO(username,password);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(employee).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new TeatruException(err);
        }
    }

//
//    public void logout(Manager employee, ITeatruObserver client) throws TeatruException {
//        //EmployeeDTO udto= DTOUtils.getDTO(employee);
//        Request req=new Request.Builder().type(RequestType.LOGOUT).data(employee).build();
//        sendRequest(req);
//        Response response=readResponse();
//        closeConnection();
//        if (response.type()== ResponseType.ERROR){
//            String err=response.data().toString();
//            throw new TeatruException(err);
//        }
//    }


    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws TeatruException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new TeatruException("Error sending object "+e);
        }

    }

    private Response readResponse() throws TeatruException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws TeatruException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
//        if ((response.type() == ResponseType.UPDATE)) {
//
//            int idZbor = (Integer)response.data();
//            //List<Flight> flights=(List<Flight>)response.data();
//            System.out.println("ID ZBOR:"+idZbor);
//            try {
//                client.ticketInregistrat(idZbor);
//            } catch (ZborException e) {
//                e.printStackTrace();
//            }
//        }


    }


    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (((Response) response).type() == ResponseType.UPDATE) {
                        handleUpdate((Response) response);
                        System.out.println("UPDATAT");
                    } else {
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
