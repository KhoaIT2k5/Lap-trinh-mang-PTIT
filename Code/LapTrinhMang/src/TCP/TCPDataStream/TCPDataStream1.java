package TCP.TCPDataStream;
//
import java.io.*;
import java.net.Socket;

public class TCPDataStream1 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242",2207);
        socket.setSoTimeout(5000);
        
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("B23DCCN440;xNCwKctT");
        dos.flush();
        
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int a = dis.readInt();
        int b = dis.readInt();
        
        int tong = a + b, tich = a * b;
        dos.writeInt(tong);
        dos.writeInt(tich);
        dos.flush();
        socket.close();
    }
}
