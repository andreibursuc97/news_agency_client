package View;

import ClientManager.Client;
import ClientManager.Encrypt;
import Model.AdminEntity;
import Model.JurnalistEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    @FXML private Button logare;
    int readerNumber=0;
    Subject subject;


    //Client clientController=new Client(1);


    public Controller() {

        subject=new Subject();
    }

    //AdminController adminController=new AdminController();
    //HashMap<Scene,Client> windows=new HashMap();
    public void LogareAdmin(javafx.event.ActionEvent actionEvent)  throws IOException

    {
        Client client=new Client(1);
        Encrypt code= new Encrypt();
        AdminEntity adminEntity=new AdminEntity(username.getText(),code.code(parola.getText()));
        client.setAdminEntity(adminEntity);
        System.out.println(logareAdmin.getText());

        client.sendCommand(logareAdmin.getText(),"admin");

        System.out.println(client.getReusit());
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(client.getReusit().get()==0)
        {FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("View/Admin.fxml"));
        AdminController adminController=new AdminController();
        loader.setController(adminController);
        adminController.addClient(username.getText(),client);

        Parent root = loader.load();
        loader.setRoot(root);
        AdminController controller=loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Meniu Admin");
        stage.setOnCloseRequest((event) -> event.consume());
        Scene scene=new Scene(root);
        stage.setScene(scene);
        controller.setUsernameText(username.getText());
        stage.show();}
        else{System.out.println("Failed");}

        Alert alert;
        if(client.getReusit().get()==1)
        {
            //Dialogs.showWarningDialog(new Stage(),"Ai introdus un username gresit!","Eroare Logare","Avertisment");
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de logare");
            alert.setHeaderText("A aparut o problema in timpul logarii");
            alert.setContentText("Ai introdus un username gresit");
            alert.showAndWait();
            //Action
        }

        if(client.getReusit().get()==2)
        {
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de logare");
            alert.setContentText("Ai introdus o parola gresita");
            alert.setHeaderText("A aparut o problema in timpul logarii");
            alert.showAndWait();
            //Action
        }

        if(client.getReusit().get()==3)
        {
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de logare");
            alert.setContentText("Acest user este deja logat!");
            alert.setHeaderText("A aparut o problema in timpul logarii");
            alert.showAndWait();
            //Action
        }

    }

    public void LogareJurnalist(javafx.event.ActionEvent actionEvent) throws IOException {
        Client client=new Client(1);
        Encrypt code= new Encrypt();
        JurnalistEntity jurnalistEntity=new JurnalistEntity(username.getText(),code.code(parola.getText()));
        client.setJurnalistEntity(jurnalistEntity);
        //System.out.println(logare.getText());

        client.sendCommand(logare.getText(),"jurnalist");


        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(client.getReusit());


        Alert alert;
        if(client.getReusit().get()==6)
        {
            //Dialogs.showWarningDialog(new Stage(),"Ai introdus un username gresit!","Eroare Logare","Avertisment");
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de logare");
            alert.setHeaderText("A aparut o problema in timpul logarii");
            alert.setContentText("Ai introdus un username gresit");
            alert.showAndWait();
            //Action
        }

        if(client.getReusit().get()==7)
        {
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de logare");
            alert.setContentText("Ai introdus o parola gresita");
            alert.setHeaderText("A aparut o problema in timpul logarii");
            alert.showAndWait();
            //Action
        }

        if(client.getReusit().get()==8)
        {
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de logare");
            alert.setContentText("Acest user este deja logat!");
            alert.setHeaderText("A aparut o problema in timpul logarii");
            alert.showAndWait();
            //Action
        }

        if(client.getReusit().get()==9)
        {FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("View/Jurnalist.fxml"));
            JurnalistController jurnalistController=new JurnalistController(this.subject);
            loader.setController(jurnalistController);
            jurnalistController.addClient(username.getText(),client);
            //jurnalistController.setUsernameText(username.getText());
            Parent root = loader.load();
            loader.setRoot(root);
            JurnalistController jurnalistController1=loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Meniu Jurnalist");
            stage.setOnCloseRequest((event) -> event.consume());
            Scene scene=new Scene(root);
            stage.setScene(scene);
            jurnalistController1.setUsernameText(username.getText());
            jurnalistController1.setItems();
            stage.show();}

    }

    public void Cititor(javafx.event.ActionEvent actionEvent) throws IOException
    {
        Client client=new Client(1);
        //Encrypt code= new Encrypt();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("View/Cititor.fxml"));
        CititorController cititorController=new CititorController(this.subject);
        loader.setController(cititorController);
        cititorController.addClient("cititor"+readerNumber,client);
        //jurnalistController.setUsernameText(username.getText());
        Parent root = loader.load();
        loader.setRoot(root);
        CititorController cititorController1=loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Meniu Cititor");
        stage.setOnCloseRequest((event) -> client.inchide());
        Scene scene=new Scene(root);
        stage.setScene(scene);
        cititorController1.setUsernameText("cititor"+readerNumber);
        cititorController1.setItems();
        stage.show();
        readerNumber++;
    }






    /**/
}
