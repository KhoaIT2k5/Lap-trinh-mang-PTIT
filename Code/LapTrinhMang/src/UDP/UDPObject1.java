package UDP;
import java.io.*;
import java.net.*;
import java.util.*;

public class UDPObject1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        
        byte[] req = ";B23DCCN440;Zp6pSmxQ".getBytes();
        socket.send(new DatagramPacket(req, req.length, address, 2209));
        
        byte[] buffer = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
        socket.receive(dp);
        
        byte[] fullData = dp.getData();
        int totalByte = dp.getLength();
        String requestId = new String(fullData,0,8);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(fullData,8,totalByte-8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Student student = (Student) ois.readObject();
        
        String[] name = student.getName().trim().split("\\s+");
        StringBuilder fullName = new StringBuilder();
        for(String x : name) {
            fullName.append(x.substring(0,1).toUpperCase())
                    .append(x.substring(1).toLowerCase())
                    .append(" ");
        }
        student.setName(fullName.toString().trim());
        
        StringBuilder email = new StringBuilder();
        email.append(name[name.length-1].toLowerCase());
        for(int i=0 ; i<name.length-1 ; i++) {
            email.append(name[i].substring(0,1).toLowerCase());
            if(i == name.length-2) email.append("@ptit.edu.vn");
        }
        student.setEmail(email.toString().trim());
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(fullData,0,8);
        
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(student);
        oos.flush();
        
        byte[] res = baos.toByteArray();
        socket.send(new DatagramPacket(res,res.length,address,2209));
        socket.close();
    }
}
