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
        
        String s = new String(dp.getData(),0,dp.getLength());
        String[] a = s.trim().split(";");
        String requestId = a[0], data = a[1];
        
        HashMap<Character,Integer> map = new HashMap<>();
        int max = Integer.MIN_VALUE;
        for(Character x : data.toCharArray()) map.put(x,map.getOrDefault(x,0)+1);
        
        StringBuilder sb = new StringBuilder();
        sb.append(requestId).append(";");
        for(Character x : data.toCharArray()) max = Math.max(map.get(x),max);
        Character charMax = null;
        for(int i=0 ; i<data.length() ; i++) {
            if(max == map.get(data.charAt(i))) {
                if(charMax == null) {
                    charMax = data.charAt(i);
                    sb.append(charMax).append(":");
                }
                if(charMax == data.charAt(i)) sb.append(i+1).append(",");
            }
        }
        byte[] res = sb.toString().trim().getBytes();
        socket.send(new DatagramPacket(res,res.length,address,2208));
        socket.close();
    }
}