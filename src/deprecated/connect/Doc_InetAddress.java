package deprecated.connect;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * #네트워크가뭐에요?
 * <p>
 * <p>
 * #포트바인딩이뭐에요?
 * 포트란 서버까지온 request가 알맞은 서버프로그램을 찾기위해 필요한 번호입니다/
 * 그리고 프로그램에 해당 번호를 연결해주는 것을 "포트바인딩"이라고 합니다.
 */

public class Doc_InetAddress {

    public static void main(String[] args) {
        try {
            InetAddress localIP = InetAddress.getLocalHost();
            System.out.printf("내컴퓨터 IP : %s", localIP);
            System.out.println();

            InetAddress naverIP = InetAddress.getByName("www.naver.com");
            System.out.printf("네이버 IP : %s", naverIP);
            System.out.println();

            InetAddress[] arrNaverIP = InetAddress.getAllByName("www.naver.com");
            for (InetAddress naver : arrNaverIP) {
                System.out.printf("네이버 IP : ", naver);
                System.out.println();
            }

            // String ip = InetAddress.getHostAddress();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
