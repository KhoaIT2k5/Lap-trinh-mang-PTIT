package UDP;
import java.util.*;
import java.io.*;
import java.net.*;

public class UDPString2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        
        byte[] req = ";B23DCCN440;T2FGmk69".getBytes();
        socket.send(new DatagramPacket(req,req.length,address,2208));
        
        byte[] buffer = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
        socket.receive(dp);
        
        String s = new String(dp.getData(),0,dp.getLength());
        String[] arr = s.trim().split(";");
        String requestId = arr[0], data = arr[1];
        System.out.println(data);
        
        StringBuilder sb = new StringBuilder();
        sb.append(arr[0]).append(";");
        
        String[] words = data.trim().split("\\s+");
        for(String x : words) sb.append(x.substring(0,1).toUpperCase()).append(x.substring(1).toLowerCase()).append(" ");
        System.out.println(sb);
        
        byte[] res = sb.toString().trim().getBytes();
        socket.send(new DatagramPacket(res,res.length,address,2208));
        socket.close();
    }
}
