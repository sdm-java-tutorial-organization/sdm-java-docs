
package deprecated.study;

import deprecated.study.feature.SDM_List;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// TODO 원인불명 cannot find symbol (SDM_List , Grade)
// => 자바실행위치 중요, "src위치"에서 파일실행시킬것.

public class GradeMain {

    // # 열거타입
    public enum Menu {
        TERMINATE("종료"),
        APPEND("성적등록"),
        REMOVE("성적제거"),
        FIND_ONE("성적조회 (선택)"),
        FIND_ALL("성적조회 (전체)"),
        SAVE("성적저장");
        private final String message;

        Menu(String message) {
            this.message = message;
        }

        String getMessage() {
            return this.message;
        }
    }

    final static String FILE_NAME = System.getProperty("user.dir") + "/txt/grade.txt";
    final static String STR_SPLIT = "_";
    static FileReader fr;
    static FileWriter fw;
    static SDM_List<Grade> sList;
    static Menu[] arrMenu = Menu.values();
    static Scanner stdIn;

    // # 메인
    public static void main(String[] args) {

        // scanner
        stdIn = new Scanner(System.in);

        // list
        sList = new SDM_List<Grade>();

        // file load
        String strFile = loadFileToString(FILE_NAME);
        parseGradeTextFile(strFile);

        // init
        int menu = 1;
        int cursor;
        String name;
        String stId;

        while (menu != 0) {
            cursor = -1; // init

            System.out.println("\n");
            System.out.println("==================성적표프로젝트(전체:" + sList.length() + "명)==================");
            for (Menu m : arrMenu) System.out.printf("(%d) %s \n", m.ordinal(), m.getMessage());
            menu = stdIn.nextInt();
            System.out.printf("(%d) %s 선택 \n", menu, arrMenu[menu].getMessage());

            System.out.println("\n");
            switch (menu) {
                case 1: // 성적등록
                    System.out.print("이름 : ");
                    name = stdIn.next();
                    System.out.print("학번 : ");
                    stId = stdIn.next();
                    System.out.print("국어 : ");
                    int kor = stdIn.nextInt();
                    System.out.print("영어 : ");
                    int eng = stdIn.nextInt();
                    System.out.print("수학 : ");
                    int math = stdIn.nextInt();

                    Grade grade = new Grade(name, stId);
                    grade.inputGrade(kor, eng, math);
                    sList.append(grade);

                    break;

                case 2: // 성적삭제
                    viewAllGrade();
                    System.out.print("번호 : ");
                    cursor = stdIn.nextInt();
                    sList.remove(cursor - 1);
                    break;

                case 3: // 성적조회 (개별)
                    viewAllGrade();
                    System.out.print("번호 : ");
                    cursor = stdIn.nextInt();
                    sList.get(cursor - 1).print();
                    break;

                case 4: // 성적조회 (전체)
                    viewAllGrade();
                    break;

                case 5: // 성적저장
                    String inputFile = "";
                    for (int i = 0; i < sList.length(); i++) {
                        inputFile +=
                                sList.get(i).name + STR_SPLIT +
                                        sList.get(i).number + STR_SPLIT +
                                        sList.get(i).kor + STR_SPLIT +
                                        sList.get(i).eng + STR_SPLIT +
                                        sList.get(i).math + "\n";
                    }
                    saveFileToString(FILE_NAME, inputFile);
                    break;
                case 0:
            }
            System.out.printf("(%d) %s이 완료되었습니다.\n", menu, arrMenu[menu].getMessage());
        }

    }

    /**
     * [ Method :: viewAllGrade ]
     *
     * @DES :: 전체성적 보여주기
     * @O.P :: void
     * @S.E :: 없음
     */
    public static void viewAllGrade() {
        System.out.printf("###########성적(전체:%d명)###########\n", sList.length());
        for (int i = 0; i < sList.length(); i++) {
            System.out.printf("번호 %d. ", i + 1);
            sList.get(i).print();
        }
        System.out.println("####################################");
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
    public static void saveFileToString(String filename, String input) {
        try {
            fw = new FileWriter(filename);
            fw.write(input);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * [ Method :: parseGradeTextFile ]
     *
     * @DES :: 로드된 스트링을 파싱하는 함수
     * @IP1 :: raw {String}
     * @O.P :: void
     * @S.E :: 없음
     */
    public static void parseGradeTextFile(String raw) {
        String[] arrLine = raw.split("\n");
        for (String line : arrLine) {
            String[] arrElement = line.split(STR_SPLIT);
            Grade grade = new Grade(arrElement[0], arrElement[1]);
            grade.inputGrade(Integer.parseInt(arrElement[2]), Integer.parseInt(arrElement[3]), Integer.parseInt(arrElement[4]));
            sList.append(grade);
        }
    }


}