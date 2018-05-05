package ClientManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {



    public static void main(String[] args)
    {
        launch(args);
        //client.inchide();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 450, 275));
        primaryStage.show();
        /*Client client=new Client(1);
        Encrypt code=new Encrypt();
        AdminEntity adminEntity=new AdminEntity("Admin2",code.code("1234"));
        client.setAdminEntity(adminEntity);
        client.sendCommand("AdminInsert");
        client.inchide();*/
        //
    }
}
