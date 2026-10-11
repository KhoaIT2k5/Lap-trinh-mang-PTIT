package TCP.TCPNIOStream;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCPNIOStream1 {

    public static void readFully(SocketChannel channel, ByteBuffer buffer) throws Exception {
        while (buffer.hasRemaining()) {
            int n = channel.read(buffer);
            if (n == -1) {
                throw new Exception("Server da dong ket noi");
            }
        }
    }

    public static String readFrame(SocketChannel channel) throws Exception {
        // Doc 4 byte length
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFully(channel, lengthBuffer);
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        // Doc payload
        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        readFully(channel, dataBuffer);
        dataBuffer.flip();

        return StandardCharsets.UTF_8.decode(dataBuffer).toString();
    }

    public static void sendFrame(SocketChannel channel, String s) throws Exception {
        byte[] data = s.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4 + data.length);
        buffer.putInt(data.length);
        buffer.put(data);
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    public static void main(String[] args) throws IOException {
        String host = "36.50.135.242";
        int port = 2211;

        String studentCode = "B23DCCN440";
        String qCode = "SUi089sD";

        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(host, port));
            System.out.println("Ket noi thanh cong");

            // a. Gui studentCode;qCode
            String request = studentCode + ";" + qCode;
            sendFrame(channel, request);

            // b. Nhan dung 3 frame
            String p1 = readFrame(channel);
            String p2 = readFrame(channel);
            String p3 = readFrame(channel);

            String http = p1 + p2 + p3;
            System.out.println("HTTP:\n" + http);

            // c. Lay METHOD, PATH
            String[] lines = http.split("\r\n");
            String[] first = lines[0].split("\\s+");

            String method = first[0];
            String path = first[1];

            // Lay HOST
            String hostHeader = "";
            for (String line : lines) {
                if (line.toLowerCase().startsWith("host:")) {
                    hostHeader = line.substring(5).trim();
                    break;
                }
            }

            String result = method + ";" + path + ";" + hostHeader;
            System.out.println("Gui: " + result);

            sendFrame(channel, result);

            // d. Dong ket noi
            channel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}