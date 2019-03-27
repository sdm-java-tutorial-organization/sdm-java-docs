package main.api;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Doc_FileSystem {

    /**
     * #파일시스템(FileSystem)
     * 운영체제의 파일시스템은 FileSystem 인터페이스를 통해 접근이 가능합니다.
     * FileSystem구현객체는 정적메소드인 getDefault()로 얻을 수 있습니다.
     * <p>
     * #API
     * - getFileStores() => Iterable<FileStore> :: 드라이버정보를 가진 FileStore객체들 리턴
     * - getRootDirectories() => Iterable<Path> :: 루트 디렉토리 정보가진 Path객체들 리턴
     * - getSeparator() => String :: 디렉토리 구분자 리턴
     * <p>
     * #FileStore
     * - getTotalSpace() => long :: 드라이버전체공간 크기
     * - getUnallocatedSpace() => long :: 할당되지않은공간 크기
     * - getUsableSpace() => long :: 사용가능공간 크기
     * - isReadOnly() => boolean :: 읽기전용여부
     * - name() => String :: 드라이버명 리턴
     * - type()0 => String :: 파일시스템종류
     */

    public static void main(String[] args) {

        // 선언
        FileSystem fs = FileSystems.getDefault();

        // 맴버 확인
        try {
            for (FileStore store : fs.getFileStores()) {
                System.out.println("[드라이버명] " + store.name());
                System.out.println("[파일시스템] " + store.type());
                System.out.println("[전체공간] " + store.getTotalSpace() + "바이트");
                System.out.println("[사용공간] " + (store.getTotalSpace() - store.getUnallocatedSpace()) + "바이트");
                System.out.println("[여유공간] " + store.getUsableSpace() + "바이트");
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[파일구분자] " + fs.getSeparator());
        for (Path root : fs.getRootDirectories())
            System.out.println("[루트디렉토리]" + root);

        System.out.println();
    }
}
