package UDP;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UDPDataType2 {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        byte[] req = ";B23DCCN440;qJp6ahHn".getBytes();
        socket.send(new DatagramPacket(req,req.length,address,2207));
        
        byte[] buffer = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
        socket.receive(dp);
        
        String s = new String(dp.getData(),0,dp.getLength());
        String[] a = s.trim().split(";");
        String requestId = a[0], data = a[1];
        String[] listNum = data.trim().split(",");
        TreeSet<Integer> set = new TreeSet<>();
        for(String x : listNum) set.add(Integer.parseInt(x.trim()));
        
        String res = String.format("%s;%d,%d",requestId,set.last(),set.first());
        byte[] result = res.getBytes();
        socket.send(new DatagramPacket(result,result.length,address,2207));
        socket.close();
    }
}
