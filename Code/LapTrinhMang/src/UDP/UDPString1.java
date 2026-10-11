package UDP;
import java.io.*;
import java.net.*;
import java.util.*;
public class UDPString1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        byte[] req = ";B23DCCN440;pbwsr9Qz".getBytes();
        socket.send(new DatagramPacket(req,req.length,address,2208));
        
        byte[] buffer = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
        socket.receive(dp);
        
        String receiveString = new String(dp.getData(),0,dp.getLength()).trim();
        String[] s =  receiveString.trim().split(";");
        String requestId = s[0].trim(), data = s[1].trim();
        LinkedHashMap<Character,Integer> map = new LinkedHashMap<>();
        for(Character x : data.toCharArray()) map.put(x,map.getOrDefault(x,0)+1);
        
        StringBuilder res = new StringBuilder();
        res.append(requestId).append(";");
        
        int max = Integer.MIN_VALUE;
        Character charMax = null;
        for(Character x : data.toCharArray()) if(map.get(x) >= max) max = map.get(x);
        for(int i=0 ; i<data.length() ; i++) {
            if(map.get(data.charAt(i)) == max) {
                if(charMax == null) {
                    charMax = data.charAt(i);
                    res.append(charMax).append(":");
                }
                if(charMax == data.charAt(i)) res.append(i+1).append(",");
            }
        }  
        
        byte[] kq = res.toString().getBytes();
        socket.send(new DatagramPacket(kq,kq.length,address,2208));
        socket.close();
    }
}