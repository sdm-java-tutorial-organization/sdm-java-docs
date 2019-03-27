package deprecated.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Doc_Socket {

    public static void main(String[] args) {

        // #Socket
        Socket socket = null;

        try {

            // #소켓연결
            System.out.println("[연결요청]");
            socket = new Socket("localhost", 5001);
            System.out.println("[연결성공]");

            byte[] bytes = null;
            String message = null;

            // #데이터송신 to Server
            OutputStream os = socket.getOutputStream();
            message = "Hello World?";
            bytes = message.getBytes("UTF-8");
            os.write(bytes);
            os.flush();
            System.out.println("[데이터 송신 완료] " + message);

            // #데이터수신 from Server
            InputStream is = socket.getInputStream();
            bytes = new byte[100];
            int readByteCount = is.read(bytes);
            message = new String(bytes, 0, readByteCount, "UTF-8");
            System.out.println("[데이터 수신 완료] " + message);

            // #종료
            os.close();
            is.close();

        } catch (Exception errA) { /**/ }

        // 해당 포트의 서버에 연결할 수 없는 경우
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException errB) { /**/ }
        }

    }
}
