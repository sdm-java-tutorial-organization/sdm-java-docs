
package deprecated.study;

public class Grade {

    String name;
    String number;
    int kor;
    int eng;
    int math;
    int total;
    float avg;
    int rank;
    int subjectCount = 3;

    // 생성자1 ()
    public Grade() {
        this.name = "";
        this.number = "";
        this.kor = 0;
        this.eng = 0;
        this.math = 0;
        this.total = 0;
        this.avg = 0.0f;
        this.rank = 0;
    }

    // 생성자2 (name, number)
    public Grade(String name, String number) {
        this.name = name;
        this.number = number;
        this.kor = 0;
        this.eng = 0;
        this.math = 0;
        this.total = 0;
        this.avg = 0.0f;
        this.rank = 0;
    }

    // 점수넣기
    public void inputGrade(int kor, int eng, int math) {
        this.kor = kor;
        this.eng = eng;
        this.math = math;
        this.total = this.getTotal(this.kor, this.eng, this.math);
        this.avg = this.getAvg(this.total, this.subjectCount);
    }

    // 총점계산
    public int getTotal(int kor, int eng, int math) {
        return kor + eng + math;
    }

    // 평균계산
    public float getAvg(int total, int count) {
        return ((float) total / (float) count);
    }

    // 점수출력
    public void print() {
        System.out.printf("[%s(%s)] 국:%d, 영:%d, 수:%d, 총점:%d, 평균:%.2f\n",
                this.name,
                this.number,
                this.kor,
                this.eng,
                this.math,
                this.total,
                this.avg);
    }
}