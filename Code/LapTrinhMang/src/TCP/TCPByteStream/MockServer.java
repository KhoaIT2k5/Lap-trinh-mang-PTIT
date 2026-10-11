package TCP.TCPByteStream;

//package TCPByteStream;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class MockServer {
//    public static void main(String[] args) {
//        try (ServerSocket server = new ServerSocket(2206)) {
//            System.out.println("Mock Server đã mở cổng 2206, đang chờ Client kết nối...");
//            Socket client = server.accept();
//            System.out.println("Client đã kết nối thành công!");
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//
//            // Nhận mã SV và mã câu hỏi
//            String initRequest = reader.readLine();
//            System.out.println("1. Server nhận từ Client: " + initRequest);
//
//            // Gửi dữ liệu mẫu đúng định dạng đề bài
//            String mockData = "1,3,9,19,33,20";
//            writer.write(mockData);
//            writer.newLine();
//            writer.flush();
//
//            // Nhận kết quả lọc từ Client
//            String finalResult = reader.readLine();
//            System.out.println("2. Server nhận kết quả: " + finalResult);
//            // Kỳ vọng: "1,19,20"
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}