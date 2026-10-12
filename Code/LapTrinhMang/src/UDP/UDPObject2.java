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
        
        StringBuilder sb1 = new StringBuilder();
        String[] name = customer.getName().toLowerCase().trim().split("\\s+");
        sb1.append(name[name.length-1].toUpperCase()).append(", ");
        for(int i=0 ; i<name.length-1 ; i++) 
            sb1.append(name[i].substring(0,1).toUpperCase()).append(name[i].substring(1)).append(" ");
        customer.setName(sb1.toString().trim());
        
        String[] dob = customer.getDayOfBirth().trim().split("-");
        String newDob = String.format("%02d/%02d/%04d",
                Integer.parseInt(dob[1]),
                Integer.parseInt(dob[0]),
                Integer.parseInt(dob[2]));
        customer.setDayOfBirth(newDob);
        
        StringBuilder sb2 = new StringBuilder();
        for(int i=0 ; i<name.length ; i++) 
            if(i != name.length-1) sb2.append(name[i].substring(0,1));
            else sb2.append(name[i]);
        customer.setUserName(sb2.toString().trim());
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(fullData,0,8);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(customer);
        oos.flush();
        byte[] res = baos.toByteArray();
        socket.send(new DatagramPacket(res,res.length,address,2209));
        socket.close();    
    }
}
