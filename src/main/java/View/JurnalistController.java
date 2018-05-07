package View;

import ClientManager.Client;
import Model.ArticolEntity;
import Model.JurnalistEntity;
import Model.Observer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import javafx.scene.control.TableView;
import java.util.HashMap;

public class JurnalistController extends Observer {

    @FXML
    private javafx.scene.text.Text usernameText;
    private HashMap<String, Client> clients=new HashMap<>();
    @FXML private TableView<ArticolEntity> articolEntityTableView;
    @FXML private TableColumn<ArticolEntity,Integer> idArticol;
    @FXML private TableColumn<ArticolEntity,String> titluArticol;
    @FXML private TableColumn<ArticolEntity, String> abstractArticol;


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

    @Override
    public void update() {

    }
}
