package View;

import ClientManager.Client;
import Model.ArticolEntity;
import Model.JurnalistEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class JurnalistController extends Observer implements Initializable {

    @FXML
    private Text usernameText;
    @FXML
    TextField idField;
    @FXML
    TextField titluField;
    @FXML
    TextField autorField;
    @FXML
    TextArea abstractArea;


    @FXML
    HTMLEditor htmlTextEditor;

    @FXML
            private ListView<String> listArticles;

    Subject subject;

    private HashMap<String, Client> clients=new HashMap<>();

    public JurnalistController(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);

    }

    @FXML
    private TableView<ArticolEntity> articolEntityTableView;
    @FXML private TableColumn<ArticolEntity,Integer> idArticol;
    @FXML private TableColumn<ArticolEntity,String> titluArticol;
    @FXML private TableColumn<ArticolEntity, String> continutAbstractArticol;

    @FXML
    private TableView<ArticolEntity> articolInruditEntityTableView;
    @FXML private TableColumn<ArticolEntity,Integer> idArticolInrudit;
    @FXML private TableColumn<ArticolEntity,String> titluArticolInrudit;
    @FXML private TableColumn<ArticolEntity, String> continutAbstractArticolInrudit;

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

    public void adaugaArticol(javafx.event.ActionEvent actionEvent)
    {
        Client client=clients.get(usernameText.getText());
        ArticolEntity articolEntity=new ArticolEntity(titluField.getText(),abstractArea.getText(),autorField.getText(),htmlTextEditor.getHtmlText());
        client.setArticolEntity(articolEntity);
        List<ArticolEntity> list=new ArrayList<>();
        for(String s : listArticles.getSelectionModel().getSelectedItems())
        {
            list.add(new ArticolEntity(s));
        }
        client.setTitluArticolEntities(list);
        client.sendCommand("Inserare articol","articol");

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(client.getReusit().get()==10)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prelucrare date");
            alert.setHeaderText("Adaugare de articol nou ");
            alert.setContentText("Opearatiunea a reusit cu succes!");
            alert.showAndWait();
        }
        this.setItems();
    }

    public void actualizeazaArticol(javafx.event.ActionEvent actionEvent)
    {
        //articolEntityTableView.getSelectionModel().clearSelection();
        Client client=clients.get(usernameText.getText());
        ArticolEntity articolEntity=new ArticolEntity(Integer.parseInt(idField.getText()),titluField.getText(),abstractArea.getText(),autorField.getText(),htmlTextEditor.getHtmlText());
        client.setArticolEntity(articolEntity);
        client.sendCommand("Update articol","articol");

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(client.getReusit().get()==11)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prelucrare date");
            alert.setHeaderText("Articolul a fost actualizat");
            alert.setContentText("Opearatiunea a reusit cu succes!");
            alert.showAndWait();
        }
        this.setItems();
    }

    @Override
    public void update() {
        articolEntityTableView.setItems(subject.getArticolEntities());
        listArticles.setItems(subject.getTitleArticolEntities());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //idArticol=new TableColumn<>("id");
        idArticol.setCellValueFactory(new PropertyValueFactory<>("id"));
        //titluArticol=new TableColumn<>("Titlu");
        titluArticol.setCellValueFactory(new PropertyValueFactory<>("Titlu"));
        //continutAbstractArticol=new TableColumn<>("Abstract");
        continutAbstractArticol.setCellValueFactory(new PropertyValueFactory<>("AbstractArticol"));
        //articolEntityTableView.setItems(getArticles());
        //listArticles.setCellFactory(new PropertyValueFactory<ArticolEntity.class>("Titlu"));
        //listArticles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        idArticolInrudit.setCellValueFactory(new PropertyValueFactory<>("id"));
        //titluArticol=new TableColumn<>("Titlu");
        titluArticolInrudit.setCellValueFactory(new PropertyValueFactory<>("Titlu"));
        //continutAbstractArticol=new TableColumn<>("Abstract");
        continutAbstractArticolInrudit.setCellValueFactory(new PropertyValueFactory<>("AbstractArticol"));
        articolEntityTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->selecteazaArticol());
        articolInruditEntityTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->selecteazaArticolInrudit());
    }

    public void setItems()
    {
        setArticles();
        articolEntityTableView.setItems(subject.getArticolEntities());
        listArticles.setItems(subject.getTitleArticolEntities());
        listArticles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //listArticles.setItems(subject.getArticolEntities());
    }

    public void selecteazaArticol()
    {
        ArticolEntity articolEntity=articolEntityTableView.getSelectionModel().getSelectedItem();
        articolInruditEntityTableView.setItems(getArticlesInrudite(articolEntity));
        //Client client=clients.get(usernameText.getText());
        if(articolEntity!=null)
        {
            idField.setText(Integer.toString(articolEntity.getId()));
            titluField.setText(articolEntity.getTitlu());
            autorField.setText(articolEntity.getAutor());
            abstractArea.setText(articolEntity.getAbstractArticol());
            htmlTextEditor.setHtmlText(articolEntity.getContinut());
        }
        //continutArea.setText(client.getArticolEntities().get(articolEntity.getId()-1).getContinut());
    }

    public void selecteazaArticolInrudit()
    {
        ArticolEntity articolEntity=articolInruditEntityTableView.getSelectionModel().getSelectedItem();
        if(articolEntity!=null)
        {
            htmlTextEditor.setHtmlText(articolEntity.getContinut());
        }
    }

    public ObservableList<ArticolEntity> getArticlesInrudite(ArticolEntity articolEntity)
    {
        ObservableList<ArticolEntity> articolEntities=FXCollections.observableArrayList();
        //articolEntities.add(new ArticolEntity(1,"Premiu","Premiu obtinut de UTCN","",""));
        //articolEntities.add(new ArticolEntity(2,"Premiu 2","Premiu obtinut de UBB","",""));
        Client client=clients.get(usernameText.getText());
        if(articolEntity!=null) {
            client.setArticolEntity(articolEntity);
            client.sendCommand("Afiseaza articole inrudite", "articol");

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //listArticles.setItems(FXCollections.observableArrayList());
            for (ArticolEntity articolEntity1 : client.getArticoleInrudite()) {
                articolEntities.add(articolEntity1);
            }

            return articolEntities;
        }
        return null;
    }


    public void setArticles()
    {
        ObservableList<ArticolEntity> articolEntities=FXCollections.observableArrayList();
        //articolEntities.add(new ArticolEntity(1,"Premiu","Premiu obtinut de UTCN","",""));
        //articolEntities.add(new ArticolEntity(2,"Premiu 2","Premiu obtinut de UBB","",""));
        Client client=clients.get(usernameText.getText());
        client.sendCommand("Afiseaza articole","");

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //listArticles.setItems(FXCollections.observableArrayList());
        for(ArticolEntity articolEntity:client.getArticolEntities())
        {
            articolEntities.add(articolEntity);
        }

        subject.setArticolEntities(articolEntities);
    }
}
