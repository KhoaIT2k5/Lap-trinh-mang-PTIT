package TCP.TCPGZipStream;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCPGzipStream1 {
    private static final String SERVER_IP = "36.50.135.242";
    private static final int SERVER_PORT = 2210;
    private static final String STUDENT_CODE = "B23DCCN440";
    private static final String Q_CODE = "4YntwbXk";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            socket.setSoTimeout(5000);
            OutputStream os = socket.getOutputStream();

            // a. Gửi mã sinh viên và mã câu hỏi: studentCode;qCode\n
            String request = STUDENT_CODE + ";" + Q_CODE + "\n";
            os.write(compress(request));
            os.flush();
            System.out.println("Đã gửi yêu cầu: " + request.trim());

            // b. Nhận chuỗi văn bản đã giải nén từ server
            GZIPInputStream gzis = new GZIPInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzis, StandardCharsets.UTF_8));
            String received = reader.readLine();
            System.out.println("Nhận từ server: " + received);

            if (received != null && !received.isEmpty()) {
                // c. Đảo ngược chuỗi nhận được và mã hóa Base64
                String reversed = new StringBuilder(received).reverse().toString();
                String base64Str = Base64.getEncoder().encodeToString(reversed.getBytes(StandardCharsets.UTF_8));

                // Chuẩn bị kết quả theo định dạng: <reversed_string>|<base64_string>\n
                String response = reversed + "|" + base64Str + "\n";

                // Gửi kết quả đã nén GZIP lên server
                os.write(compress(response));
                os.flush();
                System.out.println("Đã gửi kết quả: " + response.trim());
            }

            // d. Đóng luồng và kết thúc
            reader.close();
            gzis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm nén chuỗi thành mảng byte GZIP hoàn chỉnh (gồm Header + Data + Trailer)
    private static byte[] compress(String data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzos = new GZIPOutputStream(baos)) {
            gzos.write(data.getBytes(StandardCharsets.UTF_8));
            gzos.finish(); // Đảm bảo ghi xong toàn bộ CRC32 và trailer của GZIP
        }
        return baos.toByteArray();
    }
}