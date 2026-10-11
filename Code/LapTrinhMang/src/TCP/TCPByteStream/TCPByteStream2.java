
package TCP.TCPByteStream;

import java.util.*;
import java.io.*;
import java.net.Socket;

public class TCPByteStream2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242", 2206);
        
        OutputStream os = socket.getOutputStream();
        os.write("B23DCCN440;0oBGimK3".getBytes());
        os.flush();
        
        InputStream is = socket.getInputStream();
        byte[] buff = new byte[2048];
        int bytes = is.read(buff);
        
        String s = new String(buff,0,bytes);
        
        ArrayList<Integer> list = new ArrayList<>();
        String[] arr = s.trim().split(",");
        for(String x : arr) list.add(Integer.parseInt(x));
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;
        for(int x : list) {
            if(x > max1) {
                max2 = max1;
                max1 = x;
            } else if(x > max2 && x != max1) max2 = x;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0 ; i<list.size() ; i++) 
            if(max2 == list.get(i)) sb.append(max2).append(",").append(i);
        os.write(sb.toString().getBytes());
        os.flush();
        socket.close();
    }
}
