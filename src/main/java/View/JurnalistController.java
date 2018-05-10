package View;

import ClientManager.Client;
import Model.ArticolEntity;
import Model.JurnalistEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import netscape.javascript.JSException;

import java.io.File;
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

    //private String path=;
    private File file=new File("D:\\FACULTA\\AN 3\\Semestrul 2\\PS\\news_agency_client\\src\\intrarea-armatei-in-dobrogea.jpg");

    String img= "<img alt=\"Image\" src=\"" + file.toURI() +  "\" />";

    @FXML
    HTMLEditor htmlTextEditor;

    @FXML
            private ListView<String> listArticles;

    private Subject subject;

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
        addHTMLButton();
        idField.setDisable(true);

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

    public void addHTMLButton()
    {
        Node toolNode = htmlTextEditor.lookup(".top-toolbar");
        Node webNode = htmlTextEditor.lookup(".web-view");
        if (toolNode instanceof ToolBar && webNode instanceof WebView) {
            ToolBar bar = (ToolBar) toolNode;
            WebView webView = (WebView) webNode;
            WebEngine engine = webView.getEngine();

            Button btnCaretAddImage = new Button("img");
            btnCaretAddImage.setMinSize(40.0, 24.0);
            btnCaretAddImage.setMaxSize(40.0, 24.0);

            bar.getItems().addAll(btnCaretAddImage);
            htmlTextEditor.setHtmlText("Bine ati venit!");
            //data uri image
            img =
                    "<img alt=\"Image\" src=\"" + file.toURI() +  "\" />";
            System.out.println(img);
            //http://stackoverflow.com/questions/2213376/how-to-find-cursor-position-in-a-contenteditable-div
            String jsCodeInsertHtml = "function insertHtmlAtCursor(html) {\n" +
                    "    var range, node;\n" +
                    "    if (window.getSelection && window.getSelection().getRangeAt) {\n" +
                    "        range = window.getSelection().getRangeAt(0);\n" +
                    "        node = range.createContextualFragment(html);\n" +
                    "        range.insertNode(node);\n" +
                    "    } else if (document.selection && document.selection.createRange) {\n" +
                    "        document.selection.createRange().pasteHTML(html);\n" +
                    "    }\n" +
                    "}insertHtmlAtCursor('####html####')";
            btnCaretAddImage.setOnAction((ActionEvent event) -> {
                try {
                    engine.executeScript(jsCodeInsertHtml.
                            replace("####html####",
                                    escapeJavaStyleString(img, true, true)));
                } catch (JSException e) {
                    // A JavaScript Exception Occured
                }
            });
        }
    }



    private static String escapeJavaStyleString(String str,
                                                boolean escapeSingleQuote, boolean escapeForwardSlash) {
        StringBuilder out = new StringBuilder("");
        if (str == null) {
            return null;
        }
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                out.append("\\u").append(hex(ch));
            } else if (ch > 0xff) {
                out.append("\\u0").append(hex(ch));
            } else if (ch > 0x7f) {
                out.append("\\u00").append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;
                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;
                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;
                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;
                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            out.append("\\u00").append(hex(ch));
                        } else {
                            out.append("\\u000").append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        if (escapeSingleQuote) {
                            out.append('\\');
                        }
                        out.append('\'');
                        break;
                    case '"':
                        out.append('\\');
                        out.append('"');
                        break;
                    case '\\':
                        out.append('\\');
                        out.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            out.append('\\');
                        }
                        out.append('/');
                        break;
                    default:
                        out.append(ch);
                        break;
                }
            }
        }
        return out.toString();
    }

    private static String hex(int i) {
        return Integer.toHexString(i);
    }

    public void chooseImage(javafx.event.ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        file=fileChooser.showOpenDialog(new Stage());
        img= "<img alt=\"Image\" src=\"" + file.toURI() +  "\" />";
    }
}
