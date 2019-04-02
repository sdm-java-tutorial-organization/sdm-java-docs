package deprecated.connect;

import java.io.*;

/**
 * #스트림이뭐에요?
 * 스트림은 단일 방향으로 연속적으로 흘러가는 것을 말합니다. (물이 높은 곳에서 낮은 곳으로 흐르듯 데이터는 출발지에서 도착지로 들어간다는 개념)
 * 프로그램이 데이터를 입력 받으면 "입력스트림"
 * 프로그램이 데이터를 보낼때는 "출력스트림"
 * <p>
 * #자바는입출력을어떻게해요?
 * 자바는 입출력 API를 "java.io" 패키지에서 처리합니다.
 * 스트림은 크게 두 종류로 구분됩니다. 하나는 바이트(byte)기반 스트림이고, 다른 하나는 문자(character)기반 스트림입니다.
 * 두개의 차이점은 사용하는 파일이 다르다는 점입니다.
 * <p>
 * #주요클래스
 * 파일 시스템의 파일정보를 얻기 위한 클래스
 * - File
 * 콘솔로부터 문자를 입출력하기 위한 클래스
 * - Console
 * 바이트 단위 입출력을 위한 상위 스트림 클래스
 * - InputStream/OutputStream
 * 바이트 단위 입출력을 위한 하위 스트림 클래스
 * - FileInputStream/FileOutputStream
 * - DtaInputStream/DataOutputStream
 * - ObjectInputStream/ObjectOutputStream
 * - PrintStream
 * - BufferedInputStream/BufferedOutputStream
 * 문자 단위 입출력을 위한 최상위 스트림 클래스
 * - Reader/Writer
 * 문자 단위 입출력을 위한 하위 스트림 클래스
 * - FileReader/FileWriter
 * - InputStreamReader/InputStreamWriter
 * - PrintWriter
 * - BufferedReder/BufferedWriter
 *
 * @TIP "try-catch"로 안묶어주면 컴파일이 안됩니다. (method에 throws Exception을 걸어주어도 됩니다.)
 */

public class Doc_IOStream {
    public static void main(String[] args) {

        String file = System.getProperty("user.dir") + "/txt/tutorial.txt";

        // ================================================================================================================

        /**
         * #InputStream (바이트기반 입력스트림)
         *  - FileInputStream
         *  - DtaInputStream
         *  - ObjectInputStream
         *  - BufferedInputStream
         *  - PrintStream
         *
         * */

        try {
            // File f = new File(file);
            // InputStream is = new FileInputStream(f);
            InputStream is = new FileInputStream(file);

            int readByte;
            int readByteNo;
            byte[] readBytes = new byte[100];

            // read() => int
            while ((readByte = is.read()) != -1) { /**/ }

            // read(byte[] b) => int
            while ((readByteNo = is.read(readBytes)) != -1) { /**/ }

            // read(byte[] b, int off, int len) => int
            readByteNo = is.read(readBytes, 0, 100);

            // close() => void
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ================================================================================================================

        /**
         * #OutputStream (바이트기반 출력스트림)
         *  - FileOutputStream
         *  - DataOutputStream
         *  - ObjectOutputStream
         *  - BufferedOutputStream
         *  - PrintStream
         * */

        try {
            // File f = new File(file);
            // OutputStream is = new FileOutputStream(f);
            OutputStream os = new FileOutputStream(file);

            // @TIP 파일이 덮어씌어 지는게 싫다면, 2번째 매개값에 "true"를 입력해주세요.
            // OutputStream os = new FileOutputStream(file, true);

            byte[] data = "ABC".getBytes();

            // write(int b) => void
            for (int i = 0; i < data.length; i++)
                os.write(data[i]);

            // write(byte[] b) => void
            os.write(data);

            // write(byte[] b, int off, int len) => void
            os.write(data, 1, 2);

            // flush() - 잔류버퍼모두출력, 버퍼비우기
            os.flush();

            // close()
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ================================================================================================================

        /**
         * #Reader
         *  문자기반 입력스트림 - 이미지, 오디오, 비디오 등등을 읽을 수 없습니다.
         *      - FileReader
         *      - InputStreamReader
         *      - BufferedReder
         *      - PrintWriter
         * */

        try {
            // File f = new File(file);
            // Reader is = new FileReader(f);
            Reader reader = new FileReader(file);

            int readData;
            int readCharNo;
            char[] cbuf2 = new char[2];
            char[] cbuf100 = new char[100];

            // read() => int
            while ((readData = reader.read()) != -1) { /**/ }

            // read(char[] c) => int
            while ((readCharNo = reader.read(cbuf2)) != -1) { /**/ }

            // read(char[] c, int off, int len) => int
            reader.read(cbuf100, 0, 100);

            // close() => void
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ================================================================================================================

        /**
         * #Writer
         *  문자기반 입력스트림 - 이미지, 오디오, 비디오 등등을 읽을 수 없습니다.
         *      - FileWriter
         *      - InputStreamWriter
         *      - BufferedWriter
         *      - PrintWriter
         * */

        try {
            // File f = new File(file);
            // Writer is = new FileWriter(f);
            Writer writer = new FileWriter(file);

            // @TIP 파일이 덮어씌어 지는게 싫다면, 2번째 매개값에 "true"를 입력해주세요.
            // Writer writer = new FileWriter(file, true);

            char[] data = "홍길동".toCharArray();
            String str = "Hello Java?";

            // write(int c) => void
            for (int i = 0; i < data.length; i++)
                writer.write(data[i]);

            // write(char[] cbuf) => void
            writer.write(data);

            // write(char[] cbuf, int off, int len) => void
            writer.write(data, 1, 2);

            // write(Strig str) => void
            writer.write(str);

            // write(Strig str, int off, int len) => void
            writer.write(str, 1, 3);

            // flush() => void
            writer.flush();

            // close() => void
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * [ Method :: loadFileToString ]
     *
     * @DES :: 파일 => String 로드하기
     * @IP1 :: filename {String}
     * @O.P :: {String}
     * @S.E :: 없음
     */
    public static String loadFileToString(String filename) {
        FileReader fr;
        String rtnStr = "";
        int readCharNum;
        char[] cbuf = new char[100];
        try {
            fr = new FileReader(filename);
            while ((readCharNum = fr.read(cbuf)) != -1) {
                rtnStr += new String(cbuf, 0, readCharNum);
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rtnStr;
    }

    /**
     * [ Method :: saveFileToString ]
     *
     * @DES :: String => 파일 저장하기
     * @IP1 :: filename {String}
     * @IP2 :: input {String}
     * @O.P :: void
     * @S.E :: 없음
     */
    public static void saveStringToFile(String filename, String file) {
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write(file);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
