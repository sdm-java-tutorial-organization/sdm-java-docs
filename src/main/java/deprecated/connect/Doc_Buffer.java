package deprecated.connect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * #버퍼란 무엇인가요?
 * 버퍼는 읽고 쓰기가 가능한 메모리 배열입니다.
 * NIO는 입출력시에 항상 버퍼를 사용합니다.
 *
 * 입력되어온데이터가 "1byte"씩온다고 가정했을때 마냥 기다리고만 있는 것은 비효율적
 * 데이터를 버퍼라는 메모리영역에 넣어두고 버퍼가 생길때마다 처리해줌
 *
 * #넌다이렉트버퍼 vs 다이렉트버퍼
 * - 사용하는 메모리공간 :: JVM 힙 vs 운영체제
 * - 버퍼 생성시간 :: 빠름 vs 느림
 * - 버퍼 크기 :: 작다 vs 크다 ( 큰데이터처리에 유리 )
 * - 입출력성능 :: 낮다 vs 높다 ( 입출력이 빈번할때 유리 )
 *
 * #버퍼처리순서
 * 버퍼처리준서는 운영체제마다 다릅니다.
 * 앞쪽우선처리 = Big endian (<= JVM처리)
 * 뒤쪽우선처리 = Little endian
 *
 * #버퍼위치속성
 * - Position :: 현재 읽거나 쓰는 위치값
 * - limit :: 버퍼에서 읽거나 쓸 수 있는 위치의 한계
 * - capacity :: 버퍼의 최대 데이터 갯수
 * - mark :: reset() 메소드를 실행했을때 돌아오는 위치를 지정하는 인덱스
 */

public class Doc_Buffer {
    public static void main(String[] args) {
        /**
         *
         * #API
         *  - ByteBuffer.allocate(int capacity) => ByteBuffer
         *  - ByteBuffer.allocateDirect(int capacity) => ByteBuffer
         *  - ShortBuffer.allocate(int capacity) => ShortBuffer
         *  - ShortBuffer.allocateDirect(int capacity) => ShortBuffer
         *  - IntBuffer.allocate(int capacity) => IntBuffer
         *  - IntBuffer.allocateDirect(int capacity) => IntBuffer
         *  - LongBuffer.allocate(int capacity) => LongBuffer
         *  - LongBuffer.allocateDirect(int capacity) => LongBuffer
         *  - CharBuffer.allocate(int capacity) => CharBuffer
         *  - CharBuffer.allocateDirect(int capacity) => CharBuffer
         *  - FloatBuffer.allocate(int capacity) => FloatBuffer
         *  - FloatBuffer.allocateDirect(int capacity) => FloatBuffer
         *  - DoubleBuffer.allocate(int capacity) => DoubleBuffer
         *  - DoubleBuffer.allocateDirect(int capacity) => DoubleBuffer
         *  - wrap() => XXXBuffer :: 배열을 버퍼로 치환, 넌다이렉트 (JVM 힙생성)
         *
         * #API (버퍼메소드 - 상위버퍼추상클래스메소드)
         *  - array() => Object :: 버퍼가 래핑(wrap)한 배열을 리턴
         *  - arrayOffset() => int :: 버퍼의 첫번째 요소가 있는 내부 배열의 인덱스를 리턴
         *  - capacity() => int :: 버퍼전체 크기리턴
         *  - clear() => Buffer :: 버퍼의 위치속성 초기화 (데이터는 변함없음)
         *  - rewind() => Buffer :: position 위치를 0인덱스 위치로 이동
         *      => 읽었던 데이터를 다시 읽을때 사용
         *  - flip() => Buffer :: limit 위치를 position 위치로, position 위치를 0인덱스 위치로 이동
         *      => 버퍼를 재사용하기 위해 [clear()]하고 데이터를 버퍼에 쓴후 [filp()]으로 재사용 범위를 지정한 후 버퍼에서 데이터르 읽음
         *  - hasArray() => boolean :: 버퍼가 래핑(wrap)한 배열을 가지고 있는지 여부
         *  - remaining() => int :: position 위치와 limit 위치사이에 요소 갯수
         *  - hasRemaining() => boolean :: position 위치와 limit 위치사이에 요소가 있는지 여부 (position<limit)
         *  - isDirect() => boolean :: 운영체제버퍼인지 여부
         *  - isReadOnly() => boolean :: 버퍼가 읽기전용인지
         *  - position() => int :: position 위치 리턴
         *  - position(int newPosition) => Buffer :: newPosition으로 position 위치 설정
         *  - limit() => int :: limit 위치 리턴
         *  - limit(int newLimit) => Buffer :: newLimit으로 limit 위치 설정
         *  - mark() => Buffer :: 현재 위치를 mark 위치로 표시
         *  - reset() => Buffer :: position 위치를 mark 위치로 이동
         *  - compact() => ? :: 읽지않는 데이터는 0인덱스 부터 복사
         *      => p에서 l사이 남아있는 데이터를 버퍼의 맨 앞에부터 순서대로 쓴다.
         *
         * #데이터를읽고저장하는메소드
         *  상대적
         *      - get()
         *      - get(byte[] bytes)
         *      - get(byte[] bytes, offset, int length)
         *      - getChar()
         *      - getShort()
         *      - getInt()
         *      - getLong()
         *      - getFloat()
         *      - getDouble()
         *      - put()
         *      - put(byte[] bytes)
         *      - put(byte[] bytes, offset, int length)
         *      - put(ByteButter bb)
         *      - putChar(char c)
         *      - putShort(short s)
         *      - putInt(int i)
         *      - putLong(long l)
         *      - putFloat(float f)
         *      - putDouble(double d)
         *  절대적
         *      - get(int index)
         *      - getChar(int index)
         *      - getShort(int index)
         *      - getInt(int index)
         *      - getDouble(int index)
         *      - getFloat(int index)
         *      - getDouble(int index)
         *      - put(int index, byte b)
         *      - putChar(int index, char c)
         *      - putShort(int index, short s)
         *      - putInt(int index, int i)
         *      - putLong(int index, long l)
         *      - putFloat(int index, float f)
         *      - putDouble(int index, double d)
         *
         * @상대적vs절대적 :: position 위치부터 저장하는 것이 상대적 vs position 위치상관없이 index부터 저장하는 것이 절대적
         * */

        ByteBuffer directBuffer = ByteBuffer.allocateDirect(2 * 1024 * 1024); // 2mb
        System.out.println("[다이렉트버퍼생성]");

        ByteBuffer nonDirectBuffer = ByteBuffer.allocate(2 * 1024 * 1024); // 2mb
        System.out.println("[넌다이렉트버퍼생성]");

        System.out.println("[운영체제종류] " + System.getProperty("os.name")); // mac
        System.out.println("[네이티브 바이트해석순서] " + ByteOrder.nativeOrder()); // little-endian
        System.out.println();

        // Buffer사용
        ByteBuffer buffer = ByteBuffer.allocateDirect(5);

        System.out.printf("[버퍼채우기] ");
        buffer.put((byte) 10); // 상대적
        buffer.put((byte) 11); // 상대적
        buffer.put((byte) 12); // 상대적
        buffer.put((byte) 13); // 상대적
        buffer.put((byte) 14); // 상대적
        printBuffer(buffer);

        System.out.printf("[2바이트읽기] ");
        buffer.flip();
        buffer.get(new byte[2]);
        printBuffer(buffer);

        System.out.printf("[버퍼컴팩트] ");
        buffer.compact(); // 빈공간유지 => 삭제되지않음
        printBuffer(buffer);

        System.out.printf("[마크생성] ");
        buffer.mark();
        printBuffer(buffer);

        System.out.println();
        // ================================================================================================================

        /**
         * #버퍼의변환
         *  - ByteBuffer <=> String
         *  - ByteBuffer <=> IntBuffer
         *
         * #API
         *  - asShortBuffer() => ShortBuffer
         *  - asIntBuffer() => IntBuffer
         *  - asLongBuffer() => LongBuffer
         *  - asFloatBuffer() => FloatBuffer
         *  - asDoubleBuffer() => DoubleBuffer
         * */

        Charset charset = Charset.forName("UTF-8");

        // 문자열 => 인코딩 => ByteBuffer
        String data = "Hello_Wrold!";
        ByteBuffer bb = charset.encode(data);

        // ByteBuffer => 디코딩 => CharBuffer => 문자열
        data = charset.decode(bb).toString();
        System.out.println("[복원문자열] " + data);

        // int[] => IntBuffer => ByteButter
        int[] writeData = {1, 2, 3, 4, 5};
        IntBuffer writeIB = IntBuffer.wrap(writeData);
        ByteBuffer writeBB = ByteBuffer.allocate(writeIB.capacity() * 4);
        for (int i = 0; i < writeIB.capacity(); i++) {
            writeBB.putInt(writeIB.get(i));
        }
        writeBB.flip();

        // ByteBuffer => IntBuffer => int[]
        ByteBuffer readBB = writeBB;
        IntBuffer readIB = readBB.asIntBuffer();
        int[] readData = new int[readIB.capacity()];
        readIB.get(readData);
        System.out.println("[배열복원] " + Arrays.toString(readData));

        // ================================================================================================================

        /**
         * #버퍼예외
         *
         * BufferOverflowException => limit에서 put()
         * BufferUnderflowException => limit에서 get()
         * InvalidMarkException => 마크가 없는 상태에서 reset()
         * ReadOnlyBufferException => 읽기전용버퍼에서 put(), compat()
         *
         * */
    }

    static void printBuffer(ByteBuffer buffer) {
        System.out.printf("(pos : %d, lmt : %d) ", buffer.position(), buffer.limit());
        for (int j = 0; j < buffer.capacity(); j++) {
            System.out.printf(buffer.get(j) + " ");
        }
        System.out.println();
    }
}
