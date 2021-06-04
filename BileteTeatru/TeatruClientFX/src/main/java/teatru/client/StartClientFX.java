package teatru.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import netowrk.rpcprotocol.TeatruServicesRpcProxy;
import teatru.client.gui.AppController;
import teatru.client.gui.ManagerController;
import teatru.client.gui.LoginController;
import teatru.services.ITeatruServices;

import java.io.IOException;
import java.util.Properties;

public class StartClientFX extends Application {

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

//    Properties props = new Properties();
//    try {
//        props.load(new FileReader("bd.config"));
//    } catch (IOException e) {
//            System.out.println("Cannot find bd.config "+e);
//    }
//
//    EmployeeDBRepository employeeRepo = new EmployeeDBRepository(props);
//    FlightDBRepository flightDBRepository=new FlightDBRepository(props);
//    TicketDBRepository ticketDBRepository=new TicketDBRepository(props);
//    Service service=new Service(employeeRepo,flightDBRepository,ticketDBRepository);

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClientFX.class.getResourceAsStream("/teatruclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find teatruclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("teatru.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("teatru.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        ITeatruServices server = new TeatruServicesRpcProxy(serverIP, serverPort);

        //Load root layout from fxml file.
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("LoginView.fxml")); //URL
//        AnchorPane root = loader.load();
//        // Show the scene containing the root layout.
//        LoginController ctrl = loader.getController();
//        //ctrl.setService(service);
//        ctrl.setServer(server);
//        primaryStage.setScene(new Scene(root, 300, 200));
//        primaryStage.show();

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("view/loginView.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);
        System.out.println("A trecut de login view");

        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("view/managerView.fxml"));
        Parent croot=cloader.load();


        ManagerController appctrl =
                cloader.<ManagerController>getController();
        appctrl.setServer(server);
        ctrl.setAppController(appctrl);


        FXMLLoader c2loader = new FXMLLoader(
                getClass().getClassLoader().getResource("view/appView.fxml"));
        Parent c2root=c2loader.load();


        AppController app2ctrl =
                c2loader.<AppController>getController();
        app2ctrl.setServer(server);

        ctrl.setApp2Controller(app2ctrl);
        ctrl.setParent(croot,c2root);

        primaryStage.setTitle("Teatru");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



//        FXMLLoader cloader = new FXMLLoader(
//                getClass().getClassLoader().getResource("ChatW.fxml"));
//        Parent croot=cloader.load();
//
//
//        ChatController chatCtrl =
//                cloader.<ChatController>getController();
//        chatCtrl.setServer(server);
//
//        ctrl.setChatController(chatCtrl);
//        ctrl.setParent(croot);
//
//        primaryStage.setTitle("MPP chat");
//        primaryStage.setScene(new Scene(root, 300, 130));
//        primaryStage.show();
//
//
//
//

//
//        Properties props = new Properties();
//        try {
//            props.load(new FileReader("bd.config"));
//        } catch (IOException e) {
//            System.out.println("Cannot find bd.config "+e);
//        }
//
//
//        IEmployeeRepository employeeRepo = new EmployeeDBRepository(props);
//        IFlightRepository flightDBRepository=new FlightDBRepository(props);
//        ITicketRepository ticketDBRepository=new TicketDBRepository(props);
//        Service service=new Service(employeeRepo,flightDBRepository,ticketDBRepository);
//
//
//        //Load root layout from fxml file.
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/view/LoginView.fxml")); //URL
//        AnchorPane root = loader.load();
//        // Show the scene containing the root layout.
//        LoginController ctrl = loader.getController();
//        ctrl.setService(service);
//        primaryStage.setScene(new Scene(root, 300, 200));
//        primaryStage.show();

    }

    public static void main(String[] args) {


        launch(args);
    }

}
