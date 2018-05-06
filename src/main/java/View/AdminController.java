package View;

import ClientManager.Client;
import ClientManager.Encrypt;
import Model.AdminEntity;
import Model.JurnalistEntity;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AdminController {
    @FXML
    private javafx.scene.text.Text usernameText;
    private HashMap<String, Client> clients=new HashMap<>();
    @FXML
    private TextField usernameField;
    @FXML
    private TextField numeField;
    @FXML
    private TextField parolaField;

    public Text getUsernameText() {
        return usernameText;
    }

    public void setUsernameText(String text) {
        usernameText.setText(text);
    }

    public void addClient(String username,Client client)
    {
        clients.put(username,client);
    }

    public void Delogare(javafx.event.ActionEvent actionEvent){

        Stage stage=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Client client=clients.get(usernameText.getText());
        clients.remove(usernameText.getText());
        AdminEntity adminEntity=new AdminEntity(usernameText.getText());
        client.sendCommand("Delogare","admin");
        client.inchide();

        //client.inchide();
    }

    public void adaugaJurnalist(javafx.event.ActionEvent actionEvent)
    {
        Encrypt encrypt=new Encrypt();
        Client client=clients.get(usernameText.getText());
        JurnalistEntity jurnalistEntity=new JurnalistEntity(usernameField.getText(),numeField.getText(),encrypt.code(parolaField.getText()));
        client.setJurnalistEntity(jurnalistEntity);
        client.sendCommand("adaugaJurnalist","jurnalist");
        try {
            TimeUnit.MILLISECONDS.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Byte byte1=0;
        Alert alert;

        if(client.getReusit().get()==5)
        {
            alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Prelucrare date");
            alert.setHeaderText("Adaugare de jurnalist nou");
            alert.setContentText("Opearatiunea a reusit cu succes!");
            alert.showAndWait();
        }

        if(client.getReusit().get()==4)
        {
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prelucrare date");
            alert.setHeaderText("Adaugare de jurnalist nou");
            alert.setContentText("Exista deja un jurnalist cu acest username!");
            alert.showAndWait();
        }


    }
}
