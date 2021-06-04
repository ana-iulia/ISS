package netowrk.rpcprotocol;



import teatru.model.Manager;
import teatru.model.Performance;
import teatru.model.Reservation;
import teatru.model.Spectator;
import teatru.services.ITeatruObserver;
import teatru.services.ITeatruServices;
import teatru.services.TeatruException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TeatruClientRpcWorker implements Runnable, ITeatruObserver {
    private ITeatruServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public TeatruClientRpcWorker(ITeatruServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }




    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();
    private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();

    private Response handleRequest(Request request){
        Response response=null;
        if(request.type() == RequestType.LOGIN) {
            System.out.println("Login request.."+request.type());
            //EmployeeDTO employeeDTO=(EmployeeDTO) request.data();
            Manager employee = (Manager) request.data();
            try {
                server.login(employee, this);

                return okResponse;
            } catch (TeatruException e) {
                connected = false;
                return errorResponse;
            }
        }
        if(request.type() == RequestType.LOGINAPP) {
            System.out.println("LoginAPP request.."+request.type());
            //EmployeeDTO employeeDTO=(EmployeeDTO) request.data();
            Spectator spectator = (Spectator) request.data();
            try {
                server.loginApp(spectator, this);

                return okResponse;
            } catch (TeatruException e) {
                connected = false;
                return errorResponse;
            }
        }
        if(request.type() == RequestType.GET_PERFORMANCES) {
            try {
            System.out.println("Performance request.."+request.type());
            //EmployeeDTO employeeDTO=(EmployeeDTO) request.data();
            List<Performance> performances = server.getPerformances();
            return new Response.Builder().type(ResponseType.GET_PERFORMANCES).data(performances).build();
            } catch (TeatruException e) {
                return errorResponse;
            }
        }
        if(request.type() == RequestType.GET_RESERVATIONS) {
            try {
                System.out.println("REs request.."+request.type());
                //EmployeeDTO employeeDTO=(EmployeeDTO) request.data();
                List<Reservation>reservations = server.getReservations();
                return new Response.Builder().type(ResponseType.GET_RESERVATIONS).data(reservations).build();
            } catch (TeatruException e) {
                return errorResponse;
            }
        }
        if(request.type() == RequestType.SAVE_PERFORMANCE) {
            try {
                String s = (String) request.data();
                System.out.println(s);
                String[] fields = s.split(";");
                System.out.println(fields);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate departureDate = LocalDate.parse(fields[1], formatter);
                server.addPerformance(fields[0], departureDate,fields[2],fields[3],Integer.parseInt(fields[4]),fields[5]);
//                return new Response.Builder().type(ResponseType.NEW_TICKET).data(null).build();
                return okResponse;
                } catch (TeatruException e) {
                return errorResponse;
            }
        }
        if(request.type() == RequestType.DELETE_PERFORMANCE) {
            try {
                System.out.println("WORKER!!\n");
                String s = (String) request.data();
                System.out.println("Worker: "+s);
                String[] fields = s.split(";");
                System.out.println(fields);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate departureDate = LocalDate.parse(fields[1], formatter);
                Performance p=new Performance(fields[0], departureDate,fields[2],fields[3],Integer.parseInt(fields[4]),fields[5]);
                System.out.println("New perf: "+p);
                // server.addPerformance(fields[0], departureDate,fields[2],fields[3],Integer.parseInt(fields[4]),fields[5]);
//                return new Response.Builder().type(ResponseType.NEW_TICKET).data(null).build();
                server.deletePerformance(p);
                return okResponse;
            } catch (TeatruException e) {
                return errorResponse;
            }
        }
        if(request.type() == RequestType.GET_EMPLOYEE) {
            try {
                //System.out.println("WORKER!!\n");
                String s = (String) request.data();
                //System.out.println("Worker: "+s);
                String[] fields = s.split(";");
                System.out.println(fields);



                //System.out.println("New perf: "+p);
                // server.addPerformance(fields[0], departureDate,fields[2],fields[3],Integer.parseInt(fields[4]),fields[5]);
//                return new Response.Builder().type(ResponseType.NEW_TICKET).data(null).build();
                Manager m=server.getManager(fields[0],fields[1]);
                return new Response.Builder().type(ResponseType.GET_EMPLOYEE).data(m).build();
//
            } catch (TeatruException e) {
                return errorResponse;
            }
        }
        if(request.type() == RequestType.GET_SPECTATOR) {
            try {
                //System.out.println("WORKER!!\n");
                String s = (String) request.data();
                //System.out.println("Worker: "+s);
                String[] fields = s.split(";");
                System.out.println(fields);



                //System.out.println("New perf: "+p);
                // server.addPerformance(fields[0], departureDate,fields[2],fields[3],Integer.parseInt(fields[4]),fields[5]);
//                return new Response.Builder().type(ResponseType.NEW_TICKET).data(null).build();
                Spectator sp=server.getSpectator(fields[0],fields[1]);
                return new Response.Builder().type(ResponseType.GET_SPECTATOR).data(sp).build();
//
            } catch (TeatruException e) {
                return errorResponse;
            }
        }


//        if(request.type()==RequestType.LOGOUT){
//            System.out.println("Logout request");
//            Employee employee =(Employee) request.data();
//            try{
//                server.logout(employee,this);
//                connected=false;
//                return okResponse;
//            }
//            catch (ZborException e){
//                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//            }
//        }
//        if (request.type() == RequestType.GET_DEPARTURES) {
//            try {
//                List<String> departures = server.getDepartures();
//                return new Response.Builder().type(ResponseType.GET_DEPARTURES).data(departures).build();
//
//            } catch (ZborException e) {
//                return errorResponse;
//            }
//        }
//        if (request.type() == RequestType.GET_FLIGHTS) {
//            try {
//
//                List<Flight> flights = server.getFlights();
//                return new Response.Builder().type(ResponseType.GET_FLIGHTS).data(flights).build();
//            } catch (ZborException e) {
//                return errorResponse;
//            }
//        }
//        if (request.type() == RequestType.GET_FLIGHTS_RESUT) {
//            try {
//                String s = (String) request.data();
//                System.out.println(s);
//                String[] fields = s.split(";");
//                System.out.println(fields);
//
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDate departureDate = LocalDate.parse(fields[1], formatter);
//                List<String> flights = server.getListStringFlights(fields[0], departureDate);
//                return new Response.Builder().type(ResponseType.GET_FLIGHTS_RESULT).data(flights).build();
//            } catch (ZborException e) {
//                return errorResponse;
//            }
//        }
//        if (request.type() == RequestType.CREATE_FLIGHT) {
//            try {
//                String s = (String) request.data();
//                System.out.println(s);
//                String[] fields = s.split(";");
//                System.out.println(fields);
//
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDate departureDate = LocalDate.parse(fields[1], formatter);
//                Flight flight = server.createFlight(fields[0], departureDate, fields[2]);
//                return new Response.Builder().type(ResponseType.CREATE_FLIGHT).data(flight).build();
//            } catch (ZborException e) {
//                return errorResponse;
//            }
//
//        }
//
//        if (request.type() == RequestType.NEW_TICKET) {
//            try {
//                String s = (String) request.data();
//                System.out.println(s);
//                String[] fields = s.split(";");
//                System.out.println(fields);
//
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDate departureDate = LocalDate.parse(fields[1], formatter);
//                server.saveTicket(fields[0], departureDate,fields[2],fields[3],fields[4],fields[5],Integer.parseInt(fields[6]));
////                return new Response.Builder().type(ResponseType.NEW_TICKET).data(null).build();
//                return okResponse;
//                } catch (ZborException e) {
//                return errorResponse;
//            }
//        }
        return response;
    }


    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }
//
//    @Override
//    public void employeesLoggedIn(Employee employee) throws ZborException {
//
//    }

//    @Override
//    public void ticketInregistrat(int flight) throws ZborException {
//        System.out.println("UPDATE");
//        Response resp=new Response.Builder().type(ResponseType.UPDATE).data(flight).build();
//        try {
//
//            sendResponse(resp);
//            System.out.println("UPDATE: a trimis raspuns"+resp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
