package TCP.TCPNIOStream;

import java.io.EOFException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TCPNIOStream2 {
    private static final String SERVER_IP = "36.50.135.242";
    private static final int SERVER_PORT = 2211;
    private static final String STUDENT_CODE = "B23DCCN440";
    
    // Lưu ý: Kiểm tra lại mã câu hỏi trên giao diện web xem có còn là ayHzDIl1 không
    private static final String Q_CODE = "ayHzDIl1";

    public static void main(String[] args) throws Exception {
        // Mở kết nối TCP
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

        // a. Gửi mã sinh viên và mã câu hỏi dưới dạng chuỗi UTF-8 thuần
        String request = STUDENT_CODE + ";" + Q_CODE + "\n";
        ByteBuffer reqBuffer = ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8));
        while (reqBuffer.hasRemaining()) {
            channel.write(reqBuffer);
        }
        System.out.println("Đã gửi yêu cầu: " + request.trim());

        // b. Nhận đúng 2 frame liên tiếp từ server và ghép lại
        String frame1 = readFrame(channel);
        String frame2 = readFrame(channel);
        String jsonStr = frame1 + frame2;
        System.out.println("JSON nhận được: " + jsonStr);

        // c. Trích xuất các trường event, user, ok
        String event = extractJsonField(jsonStr, "event");
        String user = extractJsonField(jsonStr, "user");
        String okRaw = extractJsonField(jsonStr, "ok");
        String ok = ("true".equalsIgnoreCase(okRaw) || "1".equals(okRaw)) ? "1" : "0";

        // Gửi kết quả theo frame: event=<event>;user=<user>;ok=<0|1>
        String response = "event=" + event + ";user=" + user + ";ok=" + ok;
        sendFrame(channel, response);
        System.out.println("Đã gửi kết quả: " + response);

        // d. Đóng kết nối
        channel.close();
    }

    // Gửi dữ liệu theo cấu trúc Frame: 4 byte int32 độ dài + payload UTF-8
    private static void sendFrame(SocketChannel channel, String text) throws Exception {
        byte[] payload = text.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4 + payload.length);
        buffer.putInt(payload.length);
        buffer.put(payload);
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    // Đọc một frame hoàn chỉnh: đọc đủ 4 byte độ dài, sau đó đọc đủ payload
    private static String readFrame(SocketChannel channel) throws Exception {
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        readFully(channel, lenBuffer);
        lenBuffer.flip();
        int length = lenBuffer.getInt();

        ByteBuffer payloadBuffer = ByteBuffer.allocate(length);
        readFully(channel, payloadBuffer);
        payloadBuffer.flip();

        return StandardCharsets.UTF_8.decode(payloadBuffer).toString();
    }

    // Vòng lặp đảm bảo đọc đủ dữ liệu vào buffer (readFully)
    private static void readFully(SocketChannel channel, ByteBuffer buffer) throws Exception {
        while (buffer.hasRemaining()) {
            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                throw new EOFException("Server ngắt kết nối trước khi đọc đủ frame");
            }
        }
    }

    // Trích xuất trường từ chuỗi JSON đơn giản
    private static String extractJsonField(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*(?:\"([^\"]*)\"|([^,}\\s]+))");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1) != null ? matcher.group(1) : matcher.group(2).trim();
        }
        return "";
    }
}