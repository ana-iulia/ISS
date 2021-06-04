package teatru.client.gui;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import teatru.model.Manager;
import teatru.model.Spectator;
import teatru.services.ITeatruServices;
import teatru.services.TeatruException;


public class LoginController {
    //private Stage stageMain;
    private ITeatruServices server;
    private ManagerController appCtrl;
    private AppController app2Ctrl;
    private Manager crtEmployee;
    private Spectator crtSpectator;
//    private Service service;
//
//
    @FXML
    private TextField Textfieldusername;

    @FXML
    private PasswordField PasswordFieldpassword;
    @FXML
    private CheckBox isManager;

//    @FXML
//    private AnchorPane scenePane;

//    public void setService(Service service) {
//        this.service = service;
//
//    }
    Parent mainAappParent;
    Parent mainAappParent2;

    public void setServer(ITeatruServices s){
        this.server=s;

    }
    public void setParent(Parent p,Parent p2){mainAappParent=p;mainAappParent2=p2;}
//    public void setAppController(AppController appController) {
//        this.appCtrl = appController;
//    }

    public void handlelogin(ActionEvent actionEvent) {
        String u, p;

        u = Textfieldusername.getText();
        p = PasswordFieldpassword.getText();

        String errors = "";
        if (u.equals("")) {
            errors += "Introduceti nume utilizator!\n";
        }
        if (p.equals("")) {
            errors += "Introduceti parola!\n";
        }
        if (!errors.equals("")) {
            MessageAlert.showErrorMessage(null, errors);
        } else {
            if (isManager.isSelected()) {
                //System.out.println("SELECTAT!!!");

                crtEmployee = new Manager(u, p);

                try {

//                if(server.getManager(u,p)!=null) {
                    server.login(crtEmployee, appCtrl);
                    System.out.println("logged in");
                    appCtrl.setLists();
                    appCtrl.setManager(crtEmployee);
                    //appCtrl.setLists();
                    Stage stage = new Stage();
                    stage.setTitle("Window for " + crtEmployee.getId());
                    stage.setScene(new Scene(mainAappParent));
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            // appCtrl.logout();
                            System.exit(0);
                        }
                    });
                    stage.show();
//                appCtrl.setEmployee(crtEmployee);
//                appCtrl.setLists();
                    //appCtrl.setLoggedFriends();
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    //}


//                FXMLLoader loader = new FXMLLoader(
//                        getClass().getClassLoader().getResource("AppView.fxml"));
//                Parent root=loader.load();
//
//                AppController ctrl =
//                        loader.<AppController>getController();
//                setAppController(ctrl);
//                try {
//                    //Employee employee=server.getEmployee(crtEmployee.getUsername(),crtEmployee.getPassword());
//                    server.login(crtEmployee,appCtrl);
//                    Stage dialogStage = new Stage();
//                    dialogStage.setTitle(crtEmployee.getUsername());
//                    Scene scene = new Scene(root);
//                    dialogStage.setScene(scene);
//
////                    dialogStage.show();
////
////                    Stage stage=(Stage)scenePane.getScene().getWindow();
////                    stage.close();
//                    stageMain.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                        @Override
//                        public void handle(WindowEvent event) {
//                            appCtrl.logout();
//                            System.exit(0);
//                        }
//                    });

//                    ctrl.setServer(server,primaryStage);
//
//                    primaryStage.setTitle("Companie zboruri");
//                    primaryStage.setScene(new Scene(root, 300, 200));
//                    primaryStage.show();

//                    appCtrl.setServer(dialogStage,server);
//                    appCtrl.setLists();
//                    appCtrl.setEmployee(crtEmployee);
//                    stageMain.close();
//                    dialogStage.show();

//                } catch (ZborException e) {
//                    MessageAlert.showWarrningMessage(null, e.getMessage());
//                }
//            }catch (Exception ex) {
//                    MessageAlert.showErrorMessage(null, "Eroare la deschiderea ferestrei!");
//                }
//            }


//
//            }
//            try {
//                server.login(crtEmployee, appCtrl);
//                System.out.println("Logged in:");
//                Stage stage = new Stage();
//                stage.setTitle("Chat Window for " + crtEmployee.getName());
//                stage.setScene(new Scene(mainAappParent));
//
//                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                    @Override
//                    public void handle(WindowEvent event) {
//                        //appCtrl.logout();
//                        System.exit(0);
//                    }
//                });
//
////            stage.show();
////            appCtrl.setUser(crtEmployee);
////            appCtrl.setLoggedFriends();
////            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
//
                } catch (TeatruException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Teatru");
                    alert.setHeaderText("Authentication failure");
                    alert.setContentText("Wrong username or password");
                    alert.showAndWait();
                }
            }
              else{
                    crtSpectator = new Spectator(u, p);

                    try {
                        server.loginApp(crtSpectator, app2Ctrl);
                        System.out.println("logged in");
                        app2Ctrl.setSpectator(crtSpectator);
                        app2Ctrl.setLists2();
                        Stage stage = new Stage();
                        stage.setTitle("Window for " + crtSpectator.getId());
                        stage.setScene(new Scene(mainAappParent2));
                        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                // appCtrl.logout();
                                System.exit(0);
                            }
                        });
                        stage.show();
//                appCtrl.setEmployee(crtEmployee);
//                appCtrl.setLists();
                        //appCtrl.setLoggedFriends();
                        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    } catch (TeatruException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Teatru");
                        alert.setHeaderText("Authentication failure");
                        alert.setContentText("Wrong username or password");
                        alert.showAndWait();
                    }

        }
    }
    }
    public void setAppController(ManagerController managerController) {
        this.appCtrl = managerController;
    }

    public void setApp2Controller(AppController managerController) {
        this.app2Ctrl = managerController;
    }







}
