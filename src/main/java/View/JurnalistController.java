package View;

import ClientManager.Client;
import Model.JurnalistEntity;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.HashMap;

public class JurnalistController {

    @FXML
    private javafx.scene.text.Text usernameText;
    private HashMap<String, Client> clients=new HashMap<>();

    public void addClient(String username,Client client)
    {
        clients.put(username,client);
    }
    public void setUsernameText(String text) {
        usernameText.setText(text);
    }

    public void Delogare(javafx.event.ActionEvent actionEvent){

        Stage stage=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Client client=clients.get(usernameText.getText());
        clients.remove(usernameText.getText());
        JurnalistEntity jurnalistEntity=new JurnalistEntity(usernameText.getText());
        client.sendCommand("DelogareJurnalist","jurnalist");
        client.inchide();

        //client.inchide();
    }
}
