package main.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Doc_Files {

    /**
     * #Files ( java.nio.file.Files )
     * 파일속성읽기 및 파일,디렉토리 생성/삭제
     * <p>
     * #API
     * - copy() => long or Path :: 복사
     * - createDirectories() => Path :: 모든부모디렉토리 생성
     * - createDirectory() => Path :: 경로의마지막디렉토리만 생성
     * - createFile() => Path :: 파일 생성
     * - delete() => void :: 삭제
     * - deleteIfExists() => boolean :: 존재한다면 삭제
     * - exists() => boolean :: 존재여부
     * - notExists() => boolean :: 존재하지않는지 여부
     * - getFileStore() => FileStore :: 파일이 위치한 FileStore(드라이브) 리턴
     * - getLastModifiedTime() => FileTime :: 마지막 수정시간 리턴
     * - getOwner() => UserPrincipal :: 소유자 정보 리턴
     * - isDirectory() => boolean :: 디렉토리인지 여부
     * - isExecutable() => boolean :: 실행가능한지 여부
     * - isHidden() => boolean :: 숨김 야부
     * - isReadable() => boolean :: 읽기가능 여부
     * - isRegularFile() => boolean :: 일반파일인지 여부
     * - isSameFile() => boolean :: 같은 파일인지 여부
     * - isWritable() => boolean :: 쓰기가능 여부
     * - move() => Path :: 파일이동
     * - newBufferedReader() => BufferedReader :: 텍스트 파일을 읽는 BufferedReader 리턴
     * - newBufferedWriter() => BufferedWriter :: 텍스트 파일을 쓰는 BufferedWriter 리턴
     * - newByteChannel() => SeekableByteChannel :: 파일에 읽고 쓰는 바이트 채널 리턴
     * - newDirectoryStream() => DirectoryStream<Path> :: 디렉토리의 모든 내용을 스트림으로 리턴
     * - newInputStream() => InputStream :: 파일의 InputStream 리턴
     * - newOutputStream() => OutputStream :: 파일의 OutputStream 리턴
     * - probeContentType() => String :: 파일의 MIME 타입 리턴
     * - readAllBytes() => byte[] :: 파일의 모든 바이트를 읽고 배열로 리턴
     * - readAllLines() => List<String> :: 텍스트 파일의 모든 라인을 읽고 리턴
     * - size() => long :: 파일의 크기 리턴
     * - write() => Path :: 파일에 바이트나 문자열을 저장
     */

    public static void main(String[] args) {
        try {
            Path pathB = Paths.get(System.getProperty("user.dir") + "/src/data/txt/nio.txt");
            System.out.println(System.getProperty("user.dir") + "/data/nio.txt에 대한 정보");
            System.out.println("[디렉토리여부] " + Files.isDirectory(pathB));
            System.out.println("[파일여부] " + Files.isRegularFile(pathB));
            System.out.println("[마지막수정시간] " + Files.getLastModifiedTime(pathB));
            System.out.println("[파일크기] " + Files.size(pathB));
            System.out.println("[소유자] " + Files.getOwner(pathB));
            System.out.println("[숨김파일여부] " + Files.isHidden(pathB));
            System.out.println("[읽기가능여부] " + Files.isReadable(pathB));
            System.out.println("[쓰기가능여부] " + Files.isWritable(pathB));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
    }
}
