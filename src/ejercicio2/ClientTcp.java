package ejercicio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTcp extends Thread {
    String hostname;
    int port;
    boolean continueConnected;
    int intents;
    Llista llista;

    public ClientTcp(String hostname, int port, Llista llista) {
        this.hostname = hostname;
        this.port = port;
        this.llista = llista;
        continueConnected = true;
        intents=0;
    }

    public void run() {
        Socket socket;
        ObjectOutputStream out;
        ObjectInputStream in;
        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            while (continueConnected) {
                out.writeObject(llista);
                out.flush();
                Llista llista1 = (Llista) in.readObject();
                imprimirLista(llista1);
                continueConnected = false;
            }
            close(socket);
        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de connexió indefinit: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void imprimirLista(Llista llista1) {
        System.out.println(llista1);
    }

    private void close(Socket socket){
        try {
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        nums.add(3);
        nums.add(2);
        nums.add(7);
        nums.add(4);
        nums.add(5);
        nums.add(9);
        nums.add(3);
        nums.add(3);
        ClientTcp clientTcp = new ClientTcp("localhost", 5558, new Llista("Carlos", nums));
        clientTcp.start();
    }
}