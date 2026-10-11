package TCP.TCPCharacterStream;

import java.io.*;
import java.util.*;
import java.net.Socket;

public class TCPCharacterStream1 {
    public static void main(String[] args) throws IOException  {
        Socket socket = new Socket("36.50.135.242",2208);
        socket.setSoTimeout(5000);
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("B23DCCN440;eNHvT129\n");
        writer.flush();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = reader.readLine();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        String[] arr = s.trim().split(",");
        for(int i=0 ; i<arr.length ; i++) {
            String x = arr[i].trim();
            if(x.endsWith(".edu")) list.add(x);
        }
        for(int i=0 ; i<list.size() ; i++) 
            if(i != list.size()-1) sb.append(list.get(i)).append(", ");
            else sb.append(list.get(i));
        writer.write(sb.toString() + "\n");
        writer.flush();
        socket.close();
    }
}