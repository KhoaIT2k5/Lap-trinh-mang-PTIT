package TCP.TCPCharacterStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(2208)) {
            System.out.println("Mock Server đã mở cổng 2208, đang chờ Client kết nối...");
            Socket client = server.accept();
            System.out.println("Client đã kết nối thành công!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            // Nhận mã SV và mã câu hỏi
            String initRequest = reader.readLine();
            System.out.println("1. Server nhận từ Client: " + initRequest);

            // Gửi dữ liệu mẫu cho Client xử lý
            String mockData = "giHg.io, MdpIzhxDVtTFTF.edu, TUHuMfn.vn, HHJE9.com, 4hXfJe9giAA.edu";
            writer.write(mockData);
            writer.newLine();
            writer.flush();
            
            // Nhận kết quả lọc từ Client
            String finalResult = reader.readLine();
            System.out.println("2. Server nhận kết quả lọc: " + finalResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}