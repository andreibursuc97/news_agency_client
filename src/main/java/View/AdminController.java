package View;

import ClientManager.Client;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class AdminController {
    @FXML
    private javafx.scene.text.Text usernameText;
    private HashMap<String, Client> clients=new HashMap<>();

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
        clients.remove(usernameText);
        client.inchide();

        //client.inchide();
    }
}
