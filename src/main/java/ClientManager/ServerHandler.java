package ClientManager;

import Model.ArticolEntity;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    Client client;

    public ServerHandler(Socket s, DataInputStream dis, DataOutputStream dos,Client client)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.client=client;
    }

    @Override
    public void run()
    {
        String received;
        String toreturn;
        while (true)
        {
            try {

                // Ask user what he wants


                // receive the answer from client
                received = dis.readUTF();

                String[] vectReceived=received.split("\n");
                received=vectReceived[0];
                Gson gson=new Gson();

                System.out.println(received);
                //System.out.println(received+" "+vectReceived[1]);
                if(received.equals("Exit"))
                {
                    System.out.println("ClientManager.Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
                System.out.println(received);
                // creating Date object

                // write on output stream based on the
                // answer from the client
                switch (received) {
                    case "UsernameGresit":
                        System.out.println("Ai introdus un username gresit!");
                        client.setReusit(new AtomicInteger(1));
                        dos.writeUTF("Exit");
                        break;
                    case "ParolaGresita":
                        System.out.println("Ai introdus o parola gresita!");
                        client.setReusit(new AtomicInteger(2));
                        dos.writeUTF("Exit");
                        break;
                    case "DejaLogat":
                        client.setReusit(new AtomicInteger(3));
                        dos.writeUTF("Exit");
                        break;
                    case "LogareReusita":
                        System.out.println("Logare reusita");
                        client.setReusit(new AtomicInteger(0));
                        break;
                    case "ExistaDejaJurnalist":
                        client.setReusit(new AtomicInteger(4));
                        break;
                    case "jurnalistAdaugat":
                        client.setReusit(new AtomicInteger(5));
                        break;
                    case "UsernameGresitJurnalist":
                        //System.out.println("Ai introdus un username gresit!");
                        client.setReusit(new AtomicInteger(6));
                        dos.writeUTF("Exit");
                        break;
                    case "ParolaGresitaJurnalist":
                        System.out.println("Ai introdus o parola gresita!");
                        client.setReusit(new AtomicInteger(7));
                        dos.writeUTF("Exit");
                        break;
                    case "JurnalistDejaLogat":
                        client.setReusit(new AtomicInteger(8));
                        dos.writeUTF("Exit");
                        break;
                    case "LogareReusitaJurnalist":
                        //System.out.println("Logare reusita");
                        client.setReusit(new AtomicInteger(9));
                        break;
                    case "Succes inserare articol":
                        //System.out.println("Logare reusita");
                        client.setReusit(new AtomicInteger(10));
                        break;
                    case "articole":
                        System.out.println(vectReceived[1]);
                        client.setArticolEntities(stringToArray(vectReceived[1],ArticolEntity[].class));
                        System.out.println(vectReceived[1]);
                        break;
                    default:

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

}
