package ejercicio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadSrv implements Runnable {

    Socket clientSocket = null;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    boolean fin;
    Llista llista;

    public ThreadSrv(Socket clientSocket) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        fin = false;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        while(!fin) {
            try {
                llista = (Llista) in.readObject();
                out.writeObject(ordenarLista(llista));
                out.flush();
                fin = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Llista ordenarLista(Llista llista) {
        List<Integer> nums = llista.getNumberList();
        Collections.sort(nums);
        List<Integer> numsUnicos = nums.stream().distinct().collect(Collectors.toList());
        llista.setNumberList(numsUnicos);
        return llista;
    }
}