package ClientManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
                //System.out.println(received);
                // creating Date object

                // write on output stream based on the
                // answer from the client
                switch (received) {
                    case "UsernameGresit":
                        System.out.println("Ai introdus un username gresit!");
                        client.setReusit(false);
                        dos.writeUTF("Exit");
                        break;
                    case "ParolaGresita":
                        System.out.println("Ai introdus o parola gresita!");
                        client.setReusit(false);
                        dos.writeUTF("Exit");
                        break;
                    default:
                        client.setReusit(true);
                        continue;
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

}
