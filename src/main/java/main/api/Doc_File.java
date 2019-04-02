package main.api;


import java.io.File;

/**
 * #파일클래스
 * <p>
 * "java.io"에서 제공하는 File클래스는
 * 파일을 관리하는 전반적인 (파일 크기, 파일 속성, 파일 이름 등의 정보를 얻어내는 기능과 파일 생성 및 파일 삭제) 기능을 가지고 있습니다.
 * <p>
 * #API
 * - exists() => boolean :: 존재여부
 * - createNewFile() => boolean :: 파일 생성
 * - mkdir() => boolean :: 디렉토리 생성
 * - mkdirs() => boolean :: 경로상에 없는 모든 디렉토리 생성
 * - delete() => boolean :: 삭제
 * - canExecute() => boolean :: 실행가능파일인지
 * - canRead() => boolean :: 읽기가능파일인지
 * - canWrite() => boolean :: 쓰기가능파일인지
 * - getName() => String :: 파일 이름
 * - getParent() => String :: 부모 디렉토리
 * - getParentFile() => File ::
 * - getPath() => String ::
 * - isDirectory => boolean ::
 * - isFile => boolean ::
 * - isHidden => boolean :: 숨김파일인지
 * - lastModified => long :: 최종수정
 * - length() => long :: 파일크기
 * - list() => String[] :: 디렉토리에 포함된 파일 및 서브 디렉토리 목록 모두
 * - list(FilenameFilter filter) => String[] ::
 * - listFiles() => File[] :: 디렉토리에 포함된 파일 및 서브 디렉토리 목록 모두
 * - listFiles(FilenameFilter filter) => File[] ::
 *
 * @TIP 파일의 입출력은 스트림을 사용해야 합니다.
 */

public class Doc_File {
    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "/txt/tutorial.txt");
    }
}
