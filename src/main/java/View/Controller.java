package View;

import ClientManager.Client;
import ClientManager.Encrypt;
import Model.AdminEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    private TextField username;
    @FXML
    private TextField parola;
    @FXML private Button logareAdmin;

    AdminController adminController=new AdminController();
    //HashMap<Scene,Client> windows=new HashMap();
    public void LogareAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        Client client=new Client(1);
        Encrypt code= new Encrypt();
        AdminEntity adminEntity=new AdminEntity(username.getText(),code.code(parola.getText()));
        client.setAdminEntity(adminEntity);
        System.out.println(logareAdmin.getText());

        client.sendCommand(logareAdmin.getText());

        System.out.println(client.getReusit());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(client.getReusit()==true)
        {FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("View/Admin.fxml"));
        loader.setController(adminController);
        adminController.addClient(username.getText(),client);

        Parent root = loader.load();
        loader.setRoot(root);
        AdminController controller=loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Meniu Admin");
        Scene scene=new Scene(root);
        stage.setScene(scene);
        controller.setUsernameText(username.getText());
        stage.show();}
        else{System.out.println("Failed");}
    }


}
