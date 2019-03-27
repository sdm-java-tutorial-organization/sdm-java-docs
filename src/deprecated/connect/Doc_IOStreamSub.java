package deprecated.connect;

import java.io.*;

/**
 * #보조스트림
 * 보조스트림은 다른 스트림과 연결되어 여러가지 편리한 기능을 제공해주는 스트림을 말합니다.
 * 보조스트림은 문자 변환, 입출력 성능향상, 기본 데이터 타입 입출력, 객체 입출력 등의 기능을 제공합니다.
 * <p>
 * #직렬화&역직렬화
 * http://woowabros.github.io/experience/2017/10/17/java-serialize.html
 */

public class Doc_IOStreamSub {

    public static void main(String[] args) {

        // #05 보조스트림 (chaining 형태로 구성가능)
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        // 문자변환 (InputStreamReader / OutputStreamReader)
        // Reader reader = new InputStreamReader("바이트입력스트림");
        // Writer write = new OutputStreamWriter("바이트출력스트림")

        // FileInputStream fis = new FileInputStream("path");
        // Reader reader = new InputStreamReader(fis);
        // <=> FileReader reader = new FileReader("path");

        // FileOutputStream fos = new FileOutputStream("path");
        // Writer writer = new OutputStreamReader(fos);
        // <=> FileWriter writer = new FileWriter("path");

        // 성능향상 보조스트림 (메모리버퍼와 함께 작업함으로써 실행성능 향상)
        // BufferedInputStream
        // BufferedOutputStream
        // BufferedReader
        // BufferedWriter

        // BufferedInputStream bis = new BufferedInputStream(입력바이트스트림);
        // BufferedReader br = new BufferedReader(문자입력스트림);

        try {
            Reader reader = new InputStreamReader(System.in);
            BufferedReader bReader = new BufferedReader(reader);
            String inputStr = bReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // BufferedOutputStream bis = new BufferedOutputStream(입력바이트스트림);
        // BufferedWriter bw = new BufferedWriter(문자입력스트림);

        // 기본타입입출력 보조스트림 ( DataInputStream / DataOutputStream )

        // DataInputStream dis = new DataInputStream(바이트입력스트림);
        // DataOutputStream dis = new DataOutputStream(바이트출력스트림);

        // readBoolean() => boolean & writeBoolean(boolean b)
        // readByte() => byte & writeByte(byte b)
        // readChar() => char & writeChar(char c)
        // readDouble() => double & writeDouble(double d)
        // ...
        // readUTF() => String & writeUTF(String s)

        // #프린터 보조스트림 (PrintStream/PrintWriter)
        // PrintStream ps = new PrintStream(바이트출력스트림);
        // PrintWriter pw = new PrintWriter(문자출력스트림);

        // #객체입출력 보조스트림
        // #직렬화 :: 객체의 데이터(필드값)를 일렬로 늘어선 연속적인 바이트로 변경
        // #역직렬화 :: 연속적인 바이트로 변경된 데이터를 다시 객체의 데이터 형태로 변경

        // ObjectInputStream ois = new ObjectInputStream(바이트입력스트림);
        // ObjectOutputStream oos = new ObjectOutputStream(바이트출력스트림):

        // oos.writeObject(객체);
        // 객체타입 변수 = (객체타입)ois.readObject(객체);

        // #직렬화가 가능한 클래스 (serializable)
        // 자바는 Serializable 인터페이스를 구현한 클래스만 직렬화할 수 있도록 제한하고 있습니다.
        class EnableSerializable implements Serializable {
            // int field1; // => 직렬화
            // static int field2; => static 메소드는 직렬화에서 제외
        }

        // #serialVersionUID 필드 - 같은 클래스임을 알려주는 식별자역할
        // 만약 역직렬화가 되어있는데 클래스를 변경했다면, 임의로 serialVersionUID값을 수정해 줘야 합니다.
        // class SerialVersionUID implements Serializable {
        //     static final long serialVersionUID = 정수값;
        // }

        // #부모클래스를직렬화하고 싶다면?
        // - 부모클래스에 serializable 구현
        // - 자식클래스에서 writeObject() / readObject() 메소드를 선언해서 부모 객체의 필드 직접 출력

        try {
            FileOutputStream fos = new FileOutputStream("");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            SerializableClass sc = new SerializableClass();
        } catch (IOException e) {
            e.printStackTrace();
        }


    } // main

    static class SerializableClass implements Serializable {
        String A = "a";
        String B = "b";
    }
}
