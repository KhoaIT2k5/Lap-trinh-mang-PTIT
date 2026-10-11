
package TCP;
import java.util.*;
import java.io.*;
import java.net.Socket;

public class TCPObjectStream2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("36.50.135.242",2209);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("B23DCCN440;G6DYJCVZ");
        oos.flush();
        
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Customer customer = (Customer) ois.readObject();
        
        String[] tenCu = customer.getName().trim().toLowerCase().split("\\s+");
        StringBuilder tenMoi = new StringBuilder();
        tenMoi.append(tenCu[tenCu.length-1].toUpperCase()).append(", ");
        for(int i=0 ; i<tenCu.length-1 ; i++) {
            tenMoi.append(tenCu[i].substring(0,1)
                    .toUpperCase()).append(tenCu[i]
                    .substring(1)).append(" ");
        }
        customer.setName(tenMoi.toString().trim());
        
        String[] dobCu = customer.getDayOfBirth().trim().split("-");
        String ngay = dobCu[1], thang = dobCu[0], nam = dobCu[2];
        String dobMoi = String.format("%02d/%02d/%04d"
                ,Integer.parseInt(ngay)
                ,Integer.parseInt(thang)
                ,Integer.parseInt(nam)
        );
        customer.setDayOfBirth(dobMoi);
        
        StringBuilder userNameMoi =  new StringBuilder();
        for(int i=0 ; i<tenCu.length ; i++) {
            if(i != tenCu.length-1) userNameMoi.append(tenCu[i].substring(0,1));
            else userNameMoi.append(tenCu[i]);
        }
        customer.setUserName(userNameMoi.toString().trim());
        
        oos.writeObject(customer);
        oos.flush();
        
        socket.close();
    } 
}
