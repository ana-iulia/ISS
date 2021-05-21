package teatru.client.gui;


import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    static void showErrorMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
    static void showInfoMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.INFORMATION);
        message.initOwner(owner);
        message.setTitle("Mesaj de informare");
        message.setContentText(text);
        message.showAndWait();
    }
    static void showWarrningMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.WARNING);
        message.initOwner(owner);
        message.setTitle("Mesaj de avertizare");
        message.setContentText(text);
        message.showAndWait();
    }
}



