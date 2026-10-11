package TCP;

import java.io.*;
import java.net.Socket;
import java.io.Serializable;
class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;
    private int id;
    private String code,name;
    private int quantity;

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    } 
}

public class TCPObjectStream1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("36.50.135.242",2209);
        
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("B23DCCN440;y5v4TBi9");
        oos.flush();
        
        ObjectInputStream ois = new  ObjectInputStream(socket.getInputStream());
        Laptop laptop = (Laptop)ois.readObject();
        
        String[] tenCu = laptop.getName().trim().split("\\s+");
        String tmp = tenCu[0];
        tenCu[0] = tenCu[tenCu.length-1];
        tenCu[tenCu.length-1] = tmp;
        StringBuilder tenMoi = new StringBuilder();
        for(String x : tenCu) tenMoi.append(x).append(" ");
        laptop.setName(tenMoi.toString().trim());
        
        StringBuilder quantityMoi =  new StringBuilder(String.valueOf(laptop.getQuantity()));
        laptop.setQuantity(Integer.parseInt(quantityMoi.reverse().toString().trim()));
        
        oos.writeObject(laptop);
        oos.flush();
        
        socket.close();
    }
}