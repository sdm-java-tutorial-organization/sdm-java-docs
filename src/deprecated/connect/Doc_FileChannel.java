package deprecated.connect;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * #파일채널
 * 파일 읽기와 쓰기르 할때 사용합니다.
 * FileChannel은 동기화 처리가 되어 있기 때문에 멀티 스레드 환경에서 안전합니다.
 * <p>
 * #파일비동기채널
 * read()/write() 메소드는 파일 입출력 작업동안 [블로킹]됩니다.
 * UI 및 이벤트를 처리하는 스레드에서 이 메소드들을 호출하면 블로킹 되는 동안 UI 갱신이나 이벤트 처리를 할 수 없습니다.
 * 별도의 작업스레드를 생성해서 이 메소드를 호출해야 합니다.
 * 동시에 처리해야할 스레드가 많다면 스레드 수도 증가해서 문제가 될 수 있습니다.
 * 자바 NIO는 블틀정 다수의 파일 및 대용량 파일의 입출력 작업을 위해 비동기 파일 채널을 별도로 제공합니다.
 * <p>
 * #TCP 블로킹채널
 * NIO를 이용 TCP 서버/클라이언트 앱개발을 하려면 [블로킹 / 넌블로킹 / 비동기구현] 방식중에서 1개를 결정해야 합니다.
 * [NIO의 ServerSocketChannel/SocketChannel] 은 [IO의 SeverSocket/Socket] 에 대응되는 클래스로
 * 버퍼를 이용하고 블로킹과 넌블로킹 방식을 모두 지원합니다.
 */

public class Doc_FileChannel {

    public static void main(String[] args) throws Exception {

        // =======================================================================================

        /**
         * #FileChannel
         *
         * #StandardOpenOption (열거타입)
         *  - READ
         *  - WRITE
         *  - CREATE
         *  - CREATE_NEW
         *  - APPEND
         *  - DELETE_ON_CLOSE
         *  - TRUNCATE_EXISTING
         *
         * #StandardCopyOption
         *  - REPLACE_EXISTING
         * */

        String strPathLocation = System.getProperty("user.dir") + "/src/data/channel.txt";
        String strToPath = System.getProperty("user.dir") + "/src/data/copyChannel.txt";
        // System.out.println("[파일위치] " + strPathLocation);

        Path path = Paths.get(strPathLocation);
        Files.createDirectories(path.getParent());

        // 쓰기
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        String strInput = "hello _ world ? ";
        Charset cs = Charset.defaultCharset();
        ByteBuffer bb = cs.encode(strInput);

        int byteCount = fileChannel.write(bb);
        System.out.println("[StandardOpenOption] byte written : " + byteCount + "byte");
        fileChannel.close();

        // 읽기
        fileChannel = FileChannel.open(path, StandardOpenOption.READ);

        String strOutput = "";
        int readByteCount = 0;
        bb = ByteBuffer.allocate(100);

        while (true) {
            readByteCount = fileChannel.read(bb);
            // if(readByteCount == -1) break; 왜 -1이 아닐까 !?
            if (readByteCount == 0) break;
            bb.flip();
            strOutput += cs.decode(bb).toString();
        }
        fileChannel.close();
        System.out.println("[출력데이터] " + strOutput);

        // 복사 (Files.copy 사용도 가능)
        Path fromPath = Paths.get(strPathLocation);
        Path toPath = Paths.get(strToPath);

        FileChannel fileChannel_from = FileChannel.open(fromPath, StandardOpenOption.READ);
        FileChannel fileChannel_to = FileChannel.open(toPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        bb = ByteBuffer.allocateDirect(100);
        int copyByteCount;
        while (true) {
            bb.clear();
            copyByteCount = fileChannel_from.read(bb);
            // if(copyByteCount == 0) break; 앤또왜 0이 아니야 !?
            if (copyByteCount == -1) break;
            bb.flip();
            fileChannel_to.write(bb);
        }
        fileChannel_from.close();
        fileChannel_to.close();
        System.out.println("[파일복사성공]");

        // Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
        // System.out.println("[파일복사성공]");

        // =======================================================================================

        /**
         * #파일비동기채널
         *  - completed(Integer result, A attachment) => void :: 작업이 정상적으로 완료된 경우 콜백
         *  - failed(Throwable exc, A attachment) => void :: 예외로 작업이 실패 경우 콜백
         * */

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        class Attachment {
            Path path;
            AsynchronousFileChannel fileChannel;
            ByteBuffer byteBuffer;
        }

        // 비동기파일쓰기
        for (int i = 0; i < 10; i++) {
            Path pathLoop = Paths.get(System.getProperty("user.dir") + "/src/data/file_" + i + ".txt");
            Files.createDirectories(path.getParent());

            // 비동기 파일 채널 생성
            AsynchronousFileChannel aFileChannel = AsynchronousFileChannel.open(
                    pathLoop,
                    EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE),
                    es
            );

            // 파일에 저장할 데이터를 ByteButter에 저장
            Charset csB = Charset.defaultCharset();
            ByteBuffer bbB = csB.encode("HELLO ~ IT'S ME ~ ");

            // 첨부 객체생성
            Attachment attachment = new Attachment();
            attachment.path = pathLoop;
            attachment.fileChannel = aFileChannel;

            // CompletionHandler 객체생성
            CompletionHandler<Integer, Attachment> ch = new CompletionHandler<Integer, Attachment>() {
                @Override
                public void completed(Integer result, Attachment attachment) {
                    System.out.println(attachment.path.getFileName() + " : " + result + " byte written : " + Thread.currentThread().getName());
                    try {
                        attachment.fileChannel.close();
                    } catch (IOException e) {
                    }
                }

                @Override
                public void failed(Throwable exc, Attachment attachment) {
                    exc.printStackTrace();
                    try {
                        attachment.fileChannel.close();
                    } catch (IOException e) {
                    }
                }
            };

            aFileChannel.write(bbB, 0, attachment, ch);
        }

        // 비동기파일읽기
        for (int i = 0; i < 10; i++) {
            Path pathLoop = Paths.get(System.getProperty("user.dir") + "/src/data/file_" + i + ".txt");
            Files.createDirectories(path.getParent());

            // 비동기 파일 채널 생성
            AsynchronousFileChannel aFileChannel = AsynchronousFileChannel.open(
                    pathLoop,
                    EnumSet.of(StandardOpenOption.READ),
                    es
            );

            // 파일에 저장할 데이터를 ByteButter에 저장
            Charset csB = Charset.defaultCharset();
            ByteBuffer bbB = ByteBuffer.allocate((int) aFileChannel.size());

            // 첨부 객체생성
            Attachment attachment = new Attachment();
            attachment.path = pathLoop;
            attachment.fileChannel = aFileChannel;
            attachment.byteBuffer = bbB;

            // CompletionHandler 객체생성
            CompletionHandler<Integer, Attachment> ch = new CompletionHandler<Integer, Attachment>() {
                @Override
                public void completed(Integer result, Attachment attachment) {
                    attachment.byteBuffer.flip(); // [?] <- 이게없으면작업이안됨
                    String strReadData = cs.decode(attachment.byteBuffer).toString();
                    System.out.println(attachment.path.getFileName() + " : " + strReadData + " : " + Thread.currentThread().getName());
                    try {
                        attachment.fileChannel.close();
                    } catch (IOException e) {
                    }
                }

                @Override
                public void failed(Throwable exc, Attachment attachment) {
                    exc.printStackTrace();
                    try {
                        attachment.fileChannel.close();
                    } catch (IOException e) {
                    }
                }
            };

            aFileChannel.read(bbB, 0, attachment, ch);
        }

        // 스레드풀 종료
        es.shutdown();

        // =======================================================================================

        /**
         * #TCP 블로킹채널
         *
         *
         * */
    }
}
