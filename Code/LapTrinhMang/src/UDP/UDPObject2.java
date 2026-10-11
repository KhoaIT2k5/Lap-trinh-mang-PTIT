package UDP;
import java.io.*;
import java.net.*;
import java.util.*;

public class UDPObject2 {
    public static void main(String[] args) throws  IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        
        byte[] req = ";B23DCCN440;CArEJ9MI".getBytes();
        socket.send(new DatagramPacket(req,req.length,address,2209));
        
        byte[] buffer = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
        socket.receive(dp);
        
        byte[] fullData = dp.getData();
        int totalByte = dp.getLength();
        
        String requestId = new String(fullData,0,8);
        ByteArrayInputStream bais = new ByteArrayInputStream(fullData,8,totalByte-8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Customer customer = (Customer) ois.readObject();
        
        String[] ten = customer.getName().toLowerCase().trim().split("\\s+");
        StringBuilder formatTen = new StringBuilder();
        formatTen.append(ten[ten.length-1].toUpperCase()).append(", ");
        for(int i=0 ; i<ten.length-1 ; i++) 
            formatTen.append(ten[i].substring(0,1).toUpperCase())
                    .append(ten[i].substring(1)).append(" ");
        customer.setName(formatTen.toString().trim());
        
        String[] dateOld = customer.getDayOfBirth().trim().split("-");
        customer.setDayOfBirth(String.format("%02d/%02d/%04d",
                Integer.parseInt(dateOld[1]),
                Integer.parseInt(dateOld[0]),
                Integer.parseInt(dateOld[2])
        ));
        
        StringBuilder userName = new StringBuilder();
        for(int i=0 ; i<ten.length ; i++)
            if(i != ten.length-1) userName.append(ten[i].substring(0,1));
            else userName.append(ten[i]);
        customer.setUserName(userName.toString().trim());
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(fullData,0,8);
        
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(customer);
        oos.flush();
        
        byte[] res = baos.toByteArray();
        socket.send(new DatagramPacket(res,res.length,address,2209));
        
    }
}
