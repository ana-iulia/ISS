package teatru.client.gui;




import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jdk.jfr.Percentage;
import teatru.model.Manager;
import teatru.model.Performance;
import teatru.model.Reservation;
import teatru.services.ITeatruObserver;
import teatru.services.ITeatruServices;
import teatru.services.TeatruException;


import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerController implements Initializable, ITeatruObserver {
    //private Stage stage;
    private ITeatruServices server;
    private Manager employee;

    public ManagerController() {
        System.out.println("constructor AppController");

    }

    public ManagerController(ITeatruServices server) {
        this.server = server;
        System.out.println("constructor AppController");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("END INIT!!!!!!!!!");

        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<Performance, String>("title"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<Performance, LocalDate>("date"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<Performance, String>("type"));
        tableColumnDirector.setCellValueFactory(new PropertyValueFactory<Performance, String>("director"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<Performance, Integer>("price"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<Performance, String>("description"));
        tableViewPerformances.setItems(performances);

        tableColumnNumber.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("number"));
        tableColumnSeats.setCellValueFactory(new PropertyValueFactory<Reservation, String>("seats"));
        tableViewReservations.setItems(reservations);

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


    public void setManager(Manager employee) throws TeatruException {
        this.employee = employee;

        //destinations.setAll(server.getDepartures());
    }
    //    private Service service;


//    ObservableList<Flight> flights = FXCollections.observableArrayList();
//    ObservableList<String> resultFlights = FXCollections.observableArrayList();
    ObservableList<Performance> performances = FXCollections.observableArrayList();
    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    @FXML
    TableColumn<Reservation, Integer> tableColumnNumber;

    @FXML
    TableColumn<Reservation, String> tableColumnSeats;
    @FXML
    TableColumn<Performance, String> tableColumnTitle;

    @FXML
    TableColumn<Performance, LocalDate> tableColumnDate;
    @FXML
    TableColumn<Performance, String> tableColumnType;
    @FXML
    TableColumn<Performance, String> tableColumnDirector;
    @FXML
    TableColumn<Performance, Integer> tableColumnPrice;
    @FXML
    TableColumn<Performance, String> tableColumnDescription;


    @FXML
    TableView<Performance> tableViewPerformances;
    @FXML
    TableView<Reservation> tableViewReservations;
//
//    @FXML
//    private ListView<String> resultFlightsListView;
//
//    @FXML
//    private ComboBox<String> destinationsBox;
//
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField directorField;

    @FXML
    private TextField priceField;

    @FXML
    private TextArea descriptionField;

//
//    @FXML
//    private AnchorPane scenePane;

    public void setLists() {
        try {

            performances.setAll(server.getPerformances());
            reservations.setAll(server.getReservations());
        System.out.println("DA");


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
    public void handleAddPerformance() {
        LocalDate date = datePicker.getValue();
        String title=titleField.getText();
        String type=typeField.getText();
        String director=directorField.getText();
        String description=descriptionField.getText();
        String p=priceField.getText();
        try {

            int price = Integer.parseInt(p);

        if (date != null && !title.equals("") && !type.equals("") && !director.equals("") && !description.equals("") && price>0) {
            try {

                server.addPerformance(title,date,type,director,price,description);
                performances.setAll(server.getPerformances());
                tableViewPerformances.setItems(performances);
                MessageAlert.showInfoMessage(null, "Spectacol adaugat cu succes!");
                titleField.clear();
                typeField.clear();
                directorField.clear();
                priceField.clear();
                descriptionField.clear();
                datePicker.getEditor().clear();


            } catch (TeatruException e) {
                e.printStackTrace();
                MessageAlert.showErrorMessage(null, e.getMessage());
            }



        } else {
            MessageAlert.showErrorMessage(null, "Toate campurile sunt obligatorii!");

        }
        } catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Trebuie sa introduceti un pret intreg!\n");
        }
    }

    @FXML
    public void handleDelete(){
       Performance p =tableViewPerformances.getSelectionModel().getSelectedItem();
        System.out.println(p);
        if(p!=null){
            try{
                server.deletePerformance(p);
                performances.setAll(server.getPerformances());
                tableViewPerformances.setItems(performances);
                MessageAlert.showInfoMessage(null, "Spectacol sters cu succes!");

            }catch (TeatruException e){
                e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage());
        }

        }
        else{
            MessageAlert.showErrorMessage(null, "Trebuie sa selectati un spectacol!\n");
        }
    }
//
//    @FXML
//    public void handleBuy() {
//        LocalDate date = datePicker.getValue();
//        String destination = destinationsBox.getValue();
//        String hourSeats=resultFlightsListView.getSelectionModel().getSelectedItem();
//        String clientN=clientName.getText();
//        String tour=tourists.getText();
//        String s=nrSeats.getText();
//        String addr=address.getText();
//
//
//
//            if (!clientN.equals("") && !tour.equals("") && !addr.equals("") && hourSeats != null && !s.equals("")) {
//
//                try {
//
//                    int seats = Integer.parseInt(s);
////            try {
////                flight = server.createFlight(destination, date, hourSeats);
////                System.out.println(flight);
////            } catch (ZborException e) {
////                e.printStackTrace();
////            }
//
////            try {
////                try {
//                    System.out.println("!!!!!!!!!!!!!!!!!!!!Locuri"+seats);
//                    Flight flight = server.createFlight(destination, date, hourSeats);
//                    if(flight.getSeats()>=seats) {
//                        System.out.println("--------------Inainte de salvare tiket---------------");
//                        server.saveTicket(destination, date, hourSeats, clientN, tour, addr, seats);
//                        //MessageAlert.showInfoMessage(null, "Locurile au fost rezervate cu succes!\n");
//                        System.out.println("Lista FLIGHTS1"+flights);
//                        List<String> none = new ArrayList<>();
//                        resultFlights.setAll(none);
//                        resultFlightsListView.setItems(resultFlights);
//                        destinationsBox.getSelectionModel().clearSelection();
//                        resultFlightsListView.getSelectionModel().clearSelection();
//                        clientName.clear();
//                        tourists.clear();
//                        nrSeats.clear();
//                        address.clear();
//                        //ticketInregistrat(flight.getId());
//                        //System.out.println("Lista FLIGHTS1"+flights);
////                        flights.setAll(server.getFlights());
////                        tableViewFlights.setItems(flights);
//                        //System.out.println("Lista FLIGHTS2"+flights);
//                    }
//                    else{
//                        MessageAlert.showErrorMessage(null,"Nu mai sunt "+seats+" locuri la acest zbor!");
//                    }
//                } catch (ZborException e) {
//                    e.printStackTrace();
//                    MessageAlert.showErrorMessage(null, e.getMessage());
//                } catch (NumberFormatException e) {
//                    MessageAlert.showErrorMessage(null, "Trebuie sa introduceti un numar la locuri!\n");
//                }
//            }
//
//
////            } catch (ZborException m) {
////                MessageAlert.showErrorMessage(null, m.getMessage());
////            }
//        else{
//                MessageAlert.showErrorMessage(null, "Trebuie sa selectati din lista ora si destinatia si sa completati toate campurile!");
//            }
//
//
//    }
//    @FXML
//    public void handleLogout() {
//        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Logout");
//        alert.setContentText("Sunteti sigur ca vreti sa va delogati?");
//        if(alert.showAndWait().get()==ButtonType.OK) {
//            logout();
//            Stage stage = (Stage) scenePane.getScene().getWindow();
//            stage.close();
//        }
//    }
//
//    void logout() {
//        try {
//            server.logout(employee, this);
//        } catch (ZborException e) {
//            System.out.println("Logout error " + e);
//        }
//
//    }


}
