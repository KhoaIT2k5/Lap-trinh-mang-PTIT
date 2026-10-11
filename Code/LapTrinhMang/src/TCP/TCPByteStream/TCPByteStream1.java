package TCP.TCPByteStream;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class TCPByteStream1 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242",2206);
        socket.setSoTimeout(5000);
        
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        
        os.write("B23DCCN440;d8tdc4Ol".getBytes());
        os.flush();
        
        byte[] buff = new byte[2048];
        int bytes = is.read(buff);
        String s = new String(buff,0,bytes).trim();
        
        String[] arr = s.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for(String x : arr) {
            list.add(Integer.parseInt(x));
        }
        Collections.sort(list);
        int min = Integer.MAX_VALUE, a = 0, b = 0;
        for(int i=0 ; i<list.size()-1 ; i++) {
            if(list.get(i + 1) - list.get(i) <= min) {
                a = list.get(i);
                b = list.get(i+1);
                min = list.get(i + 1) - list.get(i);
            }
        }
        
        os.write((min + "," + a + "," + b).getBytes());
        os.flush();
        
        socket.close();
    }
}