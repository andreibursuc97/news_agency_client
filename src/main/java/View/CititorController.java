package View;

import ClientManager.Client;
import Model.ArticolEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class CititorController extends Observer implements Initializable {

    @FXML
    private TableView<ArticolEntity> articolEntityTableView;
    @FXML private TableColumn<ArticolEntity,Integer> idArticol;
    @FXML private TableColumn<ArticolEntity,String> titluArticol;
    @FXML private TableColumn<ArticolEntity, String> continutAbstractArticol;
    @FXML
    Text anonimText;

    @FXML
    private TableView<ArticolEntity> articolInruditEntityTableView;
    @FXML private TableColumn<ArticolEntity,Integer> idArticolInrudit;
    @FXML private TableColumn<ArticolEntity,String> titluArticolInrudit;
    @FXML private TableColumn<ArticolEntity, String> continutAbstractArticolInrudit;

    @FXML private WebView webViewArticle;

    Subject subject;
    boolean updated=false;

    public CititorController(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    private HashMap<String, Client> clients=new HashMap<>();

    public void addClient(String username,Client client)
    {
        clients.put(username,client);
    }

    public void setUsernameText(String text) {
        anonimText.setText(text);
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
        titluArticolInrudit.setCellValueFactory(new PropertyValueFactory<>("Titlu"));
        idArticolInrudit.setCellValueFactory(new PropertyValueFactory<>("id"));
        //continutAbstractArticol=new TableColumn<>("Abstract");
        continutAbstractArticolInrudit.setCellValueFactory(new PropertyValueFactory<>("AbstractArticol"));
        articolEntityTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->selecteazaArticol());
        articolInruditEntityTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->selecteazaArticolInrudit());
    }

    public void selecteazaArticol()
    {
        ArticolEntity articolEntity=articolEntityTableView.getSelectionModel().getSelectedItem();
        articolInruditEntityTableView.setItems(getArticlesInrudite(articolEntity));
        webViewArticle.getEngine().loadContent(articolEntity.getContinut());
        //Client client=clients.get(usernameText.getText());

        //continutArea.setText(client.getArticolEntities().get(articolEntity.getId()-1).getContinut());
    }

    public void selecteazaArticolInrudit()
    {
        ArticolEntity articolEntity=articolInruditEntityTableView.getSelectionModel().getSelectedItem();
        if(articolEntity!=null)
        {
            webViewArticle.getEngine().loadContent(articolEntity.getContinut());
        }

    }

    public ObservableList<ArticolEntity> getArticlesInrudite(ArticolEntity articolEntity)
    {
        ObservableList<ArticolEntity> articolEntities=FXCollections.observableArrayList();
        //articolEntities.add(new ArticolEntity(1,"Premiu","Premiu obtinut de UTCN","",""));
        //articolEntities.add(new ArticolEntity(2,"Premiu 2","Premiu obtinut de UBB","",""));
        Client client=clients.get(anonimText.getText());
        client.setArticolEntity(articolEntity);
        client.sendCommand("Afiseaza articole inrudite","articol");

        try {
            TimeUnit.MILLISECONDS.sleep(520);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //listArticles.setItems(FXCollections.observableArrayList());
        for(ArticolEntity articolEntity1:client.getArticoleInrudite())
        {
            articolEntities.add(articolEntity1);
        }

        return articolEntities;

    }

    public void setItems()
    {
            setArticles();
            articolEntityTableView.setItems(subject.getArticolEntities());

    }



    public void setArticles()
    {
        ObservableList<ArticolEntity> articolEntities=FXCollections.observableArrayList();
        //articolEntities.add(new ArticolEntity(1,"Premiu","Premiu obtinut de UTCN","",""));
        //articolEntities.add(new ArticolEntity(2,"Premiu 2","Premiu obtinut de UBB","",""));
        Client client=clients.get(anonimText.getText());
        client.sendCommand("Afiseaza articole","");

        //client.sendCommand("Afiseaza articole","");
        try {
            TimeUnit.MILLISECONDS.sleep(550);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(client.getArticolEntities()!=null)
        {
            for(ArticolEntity articolEntity:client.getArticolEntities())
            {
                articolEntities.add(articolEntity);
            }
        }
        subject.setArticolEntities(articolEntities);
    }

    @Override
    public void update() {
        articolEntityTableView.setItems(subject.getArticolEntities());
    }
}
