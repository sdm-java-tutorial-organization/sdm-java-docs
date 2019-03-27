package deprecated.connect;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Doc_Html {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            String str = "http://search.dcinside.com/combine/q/.EC.9D.B8.EB.B2.A" + i;
            String content = null;
            URLConnection connection = null;
            try {
                connection = new URL(str).openConnection();
                Scanner scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            content = content.substring(0, 10);
            System.out.println(content);
        }
    }
}
