package TCP.TCPDataStream;

import java.net.Socket;
import java.util.*;
import java.io.*;

public class TCPDataStream2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242",2207);
        
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("B23DCCN440;kRhnkuDj");
        dos.flush();
        
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String caesar = dis.readUTF();
        int s = dis.readInt();
        StringBuilder sb = new StringBuilder();
        for(char x : caesar.toCharArray()) {
            if(x >= 'A' && x <= 'Z') {
                sb.append((char)('A' + (x - 'A' - s + 26) % 26));
            } 
            else if(x >= 'a' && x <= 'z') {
                sb.append((char)('a' + (x - 'a' - s + 26) % 26));
            } 
            else {
                sb.append(x);
            }
        }
        dos.writeUTF(sb.toString());
        dos.flush();
        socket.close();
    }
}
