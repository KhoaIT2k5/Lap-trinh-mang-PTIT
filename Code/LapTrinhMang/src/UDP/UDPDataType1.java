package UDP;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPDataType1 {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        
        byte[] req = ";B23DCCN440;68vXINOE".getBytes();
        socket.send(new DatagramPacket(req,req.length,address,2207));
        
        byte[] buffer = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
        socket.receive(dp);
        
        String s = new String(dp.getData(),0,dp.getLength());
        String[] arr = s.trim().split(";");
        String requestId = arr[0];
        int n = Integer.parseInt(arr[1]);
        String[] listNum = arr[2].trim().split(",");
        
        TreeSet<Integer> set = new TreeSet<>();
        for(String x : listNum) set.add(Integer.parseInt(x));
        
        StringBuilder sb = new StringBuilder();
        sb.append(requestId).append(";");
        for(int i=1 ; i<=n ; i++) if(!set.contains(i)) sb.append(i).append(",");
        sb.deleteCharAt(sb.length()-1);
        
        byte[] res = sb.toString().trim().getBytes();
        socket.send(new DatagramPacket(res,res.length,address,2207));
        socket.close();
        
    }
}