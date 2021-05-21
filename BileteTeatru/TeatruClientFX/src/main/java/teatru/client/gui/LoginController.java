package teatru.client.gui;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import teatru.model.Manager;
import teatru.services.ITeatruServices;
import teatru.services.TeatruException;


public class LoginController {
    //private Stage stageMain;
    private ITeatruServices server;
    private ManagerController appCtrl;
    private Manager crtEmployee;
//    private Service service;
//
//
    @FXML
    private TextField Textfieldusername;

    @FXML
    private PasswordField PasswordFieldpassword;


//    @FXML
//    private AnchorPane scenePane;

//    public void setService(Service service) {
//        this.service = service;
//
//    }
    Parent mainAappParent;

    public void setServer(ITeatruServices s){
        this.server=s;

    }
    public void setParent(Parent p){mainAappParent=p;}
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
            crtEmployee = new Manager(u, p);
            try {
//                FXMLLoader uloader = new FXMLLoader(getClass().getResource("/AppView.fxml"));
                //Parent root = uloader.load();
//                AppController uctrl = uloader.getController();
//                setAppController(uctrl);

//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("AppView.fxml"));
//                AnchorPane root = loader.load();
//                AppController ctrl = loader.getController();
//                //ctrl.setService(service);
//                setAppController(ctrl);
                server.login(crtEmployee, appCtrl);
                System.out.println("logged in");
                appCtrl.setManager(crtEmployee);
                appCtrl.setLists();
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
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();


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
                alert.setTitle("MPP Zboruri");
                alert.setHeaderText("Authentication failure");
                alert.setContentText("Wrong username or password");
                alert.showAndWait();
            }
        }
    }
    public void setAppController(ManagerController managerController) {
        this.appCtrl = managerController;
    }







}
