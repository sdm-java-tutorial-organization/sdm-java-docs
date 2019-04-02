package deprecated.library.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RESTful {

    public static void main(String[] args) {
        restA("https://openapi.naver.com/v1/language/translate");
    }

    /**
     * RESTful API
     *
     * @param link 주소
     * @date
     * @author SDM
     * @version 1.0
     */
    public static void restA(String link) {
        String clientId = "YOUR_CLIENT_ID";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "YOUR_CLIENT_SECRET";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode("만나서 반갑습니다.", "UTF-8");
            String apiURL = link;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            // post request
            String postParams = "source=ko&target=en&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public static void restB() {
//        HostnameVerifier hv = new HostnameVerifier() {
//            public boolean verify(String urlHostName, SSLSession session) {
//                System.out.println("Warning: URL Host: " + urlHostName + " vs. "
//                        + session.getPeerHost());
//                return true;
//            }
//        };
//
//        // set this property to the location of the cert file
//        System.setProperty("javax.net.ssl.trustStore", "jssecacerts.cert");
//
//        HttpsURLConnection.setDefaultHostnameVerifier(hv);
//        URL url = new
//                URL("https://cab.tivlab.austin.ibm.com:9431/rest/model/" +
//                "Repository?depth=1&feed=json");
//        HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();
//
//        System.out.println("sending request...");
//        urlConn.setRequestMethod("GET");
//        urlConn.setAllowUserInteraction(false); // no user interaction
//        urlConn.setDoOutput(true); // want to send
//        urlConn.setRequestProperty("Content-type", "text/xml");
//        urlConn.setRequestProperty("accept", "text/xml");
//        urlConn.setRequestProperty("authorization", "Basic " +
//                encode("administrator:collation"));
//        Map headerFields = urlConn.getHeaderFields();
//        System.out.println("header fields are: " + headerFields);
//
//        int rspCode = urlConn.getResponseCode();
//        if (rspCode == 200) {
//            InputStream ist = urlConn.getInputStream();
//            InputStreamReader isr = new InputStreamReader(ist);
//            BufferedReader br = new BufferedReader(isr);
//
//            String nextLine = br.readLine();
//            while (nextLine != null) {
//                System.out.println(nextLine);
//                nextLine = br.readLine();
//            }
//        }
//    }
}
