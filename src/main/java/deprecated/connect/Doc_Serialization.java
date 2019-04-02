package deprecated.connect;

import java.io.Serializable;

/**
 * #직렬화가뭔가요?
 * <p>
 * 직렬화는 객체의 내용을 바이트 단위로 변환하여 파일 또는 네트워크를 통해서 스트림(송수신)이 가능하게 하는 것을 의미합니다.
 * 객체를 입출력형식에 구애받지 않고 객체를 파일에 저장함으로써 영속성을 제공 객체를 네트워크를 통해 손쉽게 교환이 가능
 * <p>
 * - Serializable 인터페이스
 * - ObjectInputStream 보조스트림
 * - ObjectOutputStream 보조스트림
 */

public class Doc_Serialization {
    public static void main(String[] args) {

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
    }
}
