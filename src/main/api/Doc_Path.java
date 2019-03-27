package main.api;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class Doc_Path {

    public static void main(String[] args) {


        /**
         * #파일과디렉토리
         *  NIO에서는 IO에서 사용하는 java.io.File에 대응되는 java.nio.file.Path 파일시스템이 있습니다.
         *
         * #Path
         *  java.nio.file.Path ( <-> java.io.File )
         *  java.nio.file.Paths
         *
         * #API (Paths)
         *  - get(String str) => Path :: 파일객체얻기
         *
         * #API (Path)
         *  - compareTo(Path other) => int :: 파일경로비교
         *  - getFileName() => Path :: 부모경로제외, 파일또는 디렉토리이름 가진 Path리턴
         *  - getFileSystem() => FileSystem :: FileSystem 객체리턴
         *  - getName(int index) => Path :: /hello/world.txt, hello - index 1, world.txt - index 2
         *  - getNameCount() => int :: /hello/world.txt, count - 2
         *  - getParent() => Path :: 바로 위 부모폴더의 Path
         *  - getRoot() => Path :: 루트 디렉토리의 Path
         *  - iterator() => Iterator<Path> :: 경로에 있는 모든 디렉토리와 파일
         *  - nomalize() => Path :: 상대경로로 표기시에 불필요한 요소제거
         *  - register() => WatchKey :: WatchService를 등록(와치서비스에서설명)
         *  - toFile() => File :: java.io.FIle 객체로 리턴
         *  - toString() => String :: 파일경로 문자열리턴
         *  - toUri() => URL :: 파일경로 URL리턴
         * */

        Path path = Paths.get(System.getProperty("user.dir") + "/txt/nio.txt");
        // Path path = Paths.get(System.getProperty("user.dir"), "/data/nio.txt");

        System.out.println("[파일명] " + path.getFileName());
        System.out.println("[부모디렉토리명] " + path.getParent().getFileName());
        System.out.println("[중첩경로수] " + path.getNameCount());
        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.printf("[파일-idx%d] %s\n", i, path.getName(i));
        }

        Iterator<Path> i = path.iterator();
        int count = 0;
        while (i.hasNext()) {
            Path tmp = i.next();
            System.out.printf("[iterator%d] %s\n", count, tmp.getFileName());
            count++;
        }

        System.out.println();


    }
}
