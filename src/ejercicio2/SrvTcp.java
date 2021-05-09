package ejercicio2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SrvTcp {

    int port;

    public SrvTcp(int port){
        this.port = port;
    }

    public void listen(){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket= new ServerSocket(port);
            while (true){
                clientSocket = serverSocket.accept();
                ThreadSrv threadSrv = new ThreadSrv(clientSocket);
                Thread clientTcp = new Thread(threadSrv);
                clientTcp.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SrvTcp srvTcp = new SrvTcp(5557);
        srvTcp.listen();
    }
}
