package TCP.TCPCharacterStream;

import java.io.*;
import java.net.Socket;
import java.util.*;
public class TCPCharacterStream2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("36.50.135.242",2208);
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("B23DCCN440;0SaWssYr\n");
        writer.flush();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = reader.readLine();
        LinkedHashMap<Character,Integer> map = new LinkedHashMap<>();
        for(Character x : s.toCharArray()) {
            if(('a' <= x && x <= 'z') || ('A' <=x && x <= 'Z') || ('0' <= x && x <= '9'))
                map.put(x, map.getOrDefault(x,0) + 1);
        } 
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Character,Integer> entry : map.entrySet()) {
            if(entry.getValue() > 1) {
                sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            }
        }
        writer.write(sb.toString() + "\n");
        writer.flush();
        socket.close();
    }
}
