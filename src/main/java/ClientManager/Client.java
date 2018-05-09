package ClientManager;

import Model.AdminEntity;
import Model.ArticolEntity;
import Model.ArticoleInruditeEntity;
import Model.JurnalistEntity;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Client
{
    AdminEntity adminEntity;
    ArticoleInruditeEntity articoleInruditeEntity;
    ArticolEntity articolEntity;
    JurnalistEntity jurnalistEntity;
    List<ArticolEntity> articolEntities;
    List<ArticolEntity> articoleInrudite;
    List<ArticolEntity> titluArticolEntities;
    String comanda;
    int id;
    InetAddress ip;
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    AtomicInteger reusit=new AtomicInteger(0);


    public void setArticolEntities(List<ArticolEntity> articolEntities) {
        this.articolEntities = articolEntities;
    }

    public ArticoleInruditeEntity getArticoleInruditeEntity() {
        return articoleInruditeEntity;
    }

    public void setArticoleInrudite(List<ArticolEntity> articoleInrudite) {
        this.articoleInrudite = articoleInrudite;
    }

    public List<ArticolEntity> getArticoleInrudite() {
        return articoleInrudite;
    }

    public List<ArticolEntity> getArticolEntities() {
        return articolEntities;
    }

    public void setAdminEntity(AdminEntity adminEntity) {
        this.adminEntity = adminEntity;
    }

    public void setArticoleInruditeEntity(ArticoleInruditeEntity articoleInruditeEntity) {
        this.articoleInruditeEntity = articoleInruditeEntity;
    }

    public List<ArticolEntity> getTitluArticolEntities() {
        return titluArticolEntities;
    }

    public void setTitluArticolEntities(List<ArticolEntity> titluArticolEntities) {
        this.titluArticolEntities = titluArticolEntities;
    }

    public void setArticolEntity(ArticolEntity articolEntity) {
        this.articolEntity = articolEntity;
    }

    public void setJurnalistEntity(JurnalistEntity jurnalistEntity) {
        this.jurnalistEntity = jurnalistEntity;
    }

    public AtomicInteger getReusit() {
        return reusit;
    }

    public void setReusit(AtomicInteger reusit) {
        this.reusit = reusit;
    }

    public Client(int id)
    {
        try
        {
            this.id=id;
            //scn = new Scanner(System.in);

            // getting localhost ip
            ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            s = new Socket(ip, 8081);

            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            Thread c=new ServerHandler(s,dis,dos,this);
            c.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


public void sendCommand(String comanda,String objectType)
{
    //Encrypt encrypt=new Encrypt();
    //JurnalistEntity jurnalistEntity=new JurnalistEntity(1,"andrei","Andrei Bursuc",encrypt.code("1234"));
    //AdminEntity adminEntity = new AdminEntity("Admin",encrypt.code("1234"));
    Gson gson=new Gson();

    try {
        switch(objectType) {
            case "admin":
                dos.writeUTF(comanda + "\n" + gson.toJson(adminEntity));
                break;
            case "jurnalist":
                dos.writeUTF(comanda + "\n" + gson.toJson(jurnalistEntity));
                break;
            case "articol":
                /*for(String s : titluArticolEntities)
                {
                    System.out.println(s);
                }*/

                //System.out.println(gson.toJson(titluArticolEntities));

                dos.writeUTF(comanda + "\n" + gson.toJson(articolEntity) + "\n" + gson.toJson(titluArticolEntities) );
                break;
            default:
                dos.writeUTF(comanda);
            // printing date or time as requested by client
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

}

public void inchide()
{
    try {
        dos.writeUTF("Exit");
       /* this.dis.close();
        this.dos.close();
        System.out.println("ClientManager.Client " + this.s + " sends exit...");
        System.out.println("Closing this connection.");
        this.s.close();
        System.out.println("Connection closed");*/
    } catch (IOException e) {
        e.printStackTrace();
    }

}

}