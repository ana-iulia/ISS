package teatru.client.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import teatru.model.Manager;
import teatru.model.Performance;
import teatru.model.Reservation;
import teatru.model.Spectator;
import teatru.services.ITeatruObserver;
import teatru.services.ITeatruServices;
import teatru.services.TeatruException;

import java.awt.*;
import java.awt.Button;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.awt.Color.RED;

public class AppController implements Initializable, ITeatruObserver {
    //private Stage stage;
    private ITeatruServices server;
    private Spectator spectator;
    private Performance performance=null;
    private ArrayList<Integer>seatsOcupied;

    public AppController() {
        System.out.println("constructor AppController");

    }

    public AppController(ITeatruServices server) {
        this.server = server;
        System.out.println("constructor AppController");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("END INIT!!!!!!!!!");

//        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<Performance, String>("title"));
//        tableColumnDate.setCellValueFactory(new PropertyValueFactory<Performance, LocalDate>("date"));
//        tableColumnType.setCellValueFactory(new PropertyValueFactory<Performance, String>("type"));
//        tableColumnDirector.setCellValueFactory(new PropertyValueFactory<Performance, String>("director"));
//        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<Performance, Integer>("price"));
//        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<Performance, String>("description"));
//        tableViewPerformances.setItems(performances);

    }

//    @Override
//    public void employeesLoggedIn(Employee employee) throws ZborException {
//
//    }

//    @Override
//    public void ticketInregistrat(int flight) throws ZborException {
////        Platform.runLater(()->{try {
//        System.out.println("Setare tabel");
////            flights.setAll(server.getFlights());
////            tableViewFlights.setItems(flights);
//
//////        }catch(ZborException e)
////        {
////            MessageAlert.showErrorMessage(null, "Nu s-a putut actualiza lista de zboruri!");
////        };
////        });
//        Platform.runLater(() -> {
//            try {
//                //Flight[] lFl = server.getFlights();getLoggedFriends(user);
//                //tableViewFlights.getItems().clear();
//                //flights.clear();
//                flights.setAll(server.getFlights());
//                System.out.println("Zboriurile sunt" + flights);
//                //flights.addAll(server.getFlights());
//                //tableViewFlights.setItems(flights);
//
//            } catch (ZborException e) {
//                MessageAlert.showErrorMessage(null, "Nu s-a putut actualiza lista de zboruri!");
//            }
//            ;
//        });
//    }

    public void setServer(ITeatruServices server) {
        this.server = server;


//        try {
//            List<String> flights=server.getFlights();
//            System.out.println("DA");
//            System.out.println(flights);
//
//        } catch (ZborException e) {
//            e.printStackTrace();
//        }
    }


    public void setSpectator(Spectator spectator) throws TeatruException {
        this.spectator = spectator;

        //destinations.setAll(server.getDepartures());
    }
    //    private Service service;


    //    ObservableList<Flight> flights = FXCollections.observableArrayList();
//    ObservableList<String> resultFlights = FXCollections.observableArrayList();
    ObservableList<String> myReservations = FXCollections.observableArrayList();
    @FXML
    ListView<String>ReservationsList;
//
//    @FXML
//    TableColumn<Performance, String> tableColumnTitle;
//
//    @FXML
//    TableColumn<Performance, LocalDate> tableColumnDate;
//    @FXML
//    TableColumn<Performance, String> tableColumnType;
//    @FXML
//    TableColumn<Performance, String> tableColumnDirector;
//    @FXML
//    TableColumn<Performance, Integer> tableColumnPrice;
//    @FXML
//    TableColumn<Performance, String> tableColumnDescription;
//
//
//    @FXML
//    TableView<Performance> tableViewPerformances;
//    //
////    @FXML
////    private ListView<String> resultFlightsListView;
////
////    @FXML
////    private ComboBox<String> destinationsBox;
////
//    @FXML
//    private DatePicker datePicker;
//
//    @FXML
//    private TextField titleField;
//
//    @FXML
//    private TextField typeField;
//
//    @FXML
//    private TextField directorField;
//
//    @FXML
//    private TextField priceField;
//
//    @FXML
//    private TextArea descriptionField;

//
//    @FXML
//    private AnchorPane scenePane;

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField priceField;
    @FXML
    private TextArea descriptionArea;

    @FXML
    private CheckBox c1;

    @FXML
    private CheckBox c2;
    @FXML
    private CheckBox c3;
    @FXML
    private CheckBox c4;
    @FXML
    private CheckBox c5;
    @FXML
    private CheckBox c6;
    @FXML
    private CheckBox c7;
    @FXML
    private CheckBox c8;
    @FXML
    private CheckBox c9;


    public void setLists2() {
        try {
            //nameField.setBackground(Color.RED);

//            performances.setAll(server.getPerformances());
            this.spectator=server.getSpectator(spectator.getUsername(),spectator.getPassword());
            nameField.setText(spectator.getName());
            phoneField.setText(spectator.getPhone());
            emailField.setText(spectator.getEmail());
            for (Performance p:server.getPerformances()) {
                if(p.getDate().equals(LocalDate.now()))
                {
                    this.performance=p;

                    break;
                }

            }
            if(this.performance==null){
                MessageAlert.showErrorMessage(null, "Astazi nu are loc niciun spectacol!");

            }
            else{
               Integer i= this.performance.getPrice();
               priceField.setText(i.toString());
               descriptionArea.setText(this.performance.getDescription());
            }

            System.out.println("DA");

            for (Reservation res:server.getReservations()) {
//                if(res.getUsername().equals(this.spectator.getUsername())){
//                    String reserv=res.getNumber()+"-"+res.getSeats();
//                    myReservations.add(reserv);
//                }
                String s=res.getSeats();
                if(s.equals("1")){
                    c1.setSelected(true);
                    c1.setDisable(true);
                }
                if(s.equals("2")){
                    c2.setSelected(true);
                    c2.setDisable(true);
                }
                if(s.equals("3")){
                    c3.setSelected(true);
                    c3.setDisable(true);
                }
                if(s.equals("4")){
                    c4.setSelected(true);
                    c4.setDisable(true);
                }
                if(s.equals("5")){
                    c5.setSelected(true);
                    c5.setDisable(true);
                }
                if(s.equals("6")){
                    c6.setSelected(true);
                    c6.setDisable(true);
                }
                if(s.equals("7")){
                    c7.setSelected(true);
                    c7.setDisable(true);
                }
                if(s.equals("9")){
                    c9.setSelected(true);
                    c9.setDisable(true);
                }
                if(s.equals("8")){
                    c8.setSelected(true);
                    c8.setDisable(true);
                }
            }
            ReservationsList.setItems(myReservations);

        } catch (TeatruException e) {
            e.printStackTrace();
        }
    }
//    public void setService(Service service) {
//        this.service = service;
//        flights.setAll(service.getListFlights());
//        destinations.setAll(service.getDepartures());
//
//    }
//
//    @FXML
//    public void initialize() {
//        tableColumnDestination.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
//        tableColumnDepartureDateTime.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureDateTime"));
//        tableColumnAirport.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
//        tableColumnSeats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));
//        tableViewFlights.setItems(flights);
//        destinationsBox.setItems(destinations);
//
//
//    }

    @FXML
    public void handleRezerva(){


//        Rectangle rect = null;
        //Rectangle1.setFrame(70,70,70,70);
//        Rectangle lineBot = new Rectangle(50, 50);
//        lineBot.setFill(RED);
//        GraphicsContext gc =getGraphicsContext2D();
//
//        gc.setFill(RED);
//        gc.fillRect(0, 0, 50, 50);
        //gc.setFill(Color.FORESTGREEN);
    }

}
