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
        
        byte[] buffer = new byte[2048];
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
        socket.receive(dp);
        
        String requestStr = new String(dp.getData(),0,dp.getLength()).trim();
        String[] s = requestStr.split(";");
        String requestId = s[0];
        String[] listNum = s[1].split(",");
        TreeSet<Integer>set = new TreeSet<>();
        for(String x : listNum) set.add(Integer.parseInt(x.trim()));
        String res = String.format("%s;%d,%d",requestId,set.last(),set.first());
        byte[] result = res.getBytes();
        socket.send(new DatagramPacket(result,result.length,address,2207));
        socket.close();
    }
}
