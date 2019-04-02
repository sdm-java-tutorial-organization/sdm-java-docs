package deprecated.study.feature;

import deprecated.study.Grade;

/**
 * #자료구조에서Array가뭐에요?
 * <p>
 * 데이터가 많아졌을때 그룹관리의 필요성에 의해서 필요한 것이 배열입니다.
 * 최초 요청한 데이터 사이즈에 맞게 메모리를 할당받기 때문에, 인덱스를 가지고 있다는 장점이 있습니다.
 * 리스트에 비해 데이터의 탐색은 빠르나, 가변적인 크기수정은 속도가 느리다는 것이 일반적인 견해입니다.
 */

public class SDM_Array {

    private int size = 2;
    private Grade[] arrGrade;

    // 생성자1
    public SDM_Array() {
        this.size = 2;
        arrGrade = new Grade[2];
    }

    // 생성자2
    public SDM_Array(int size) {
        this.size = size;
        arrGrade = new Grade[size];
    }

    // 수정
    public void put(int index, Grade grade) {
        if (index >= this.size) {
            this.resize(index);
        }
        if (index < 0) {
            // throw error
        }
        System.out.println(this.size);
        this.arrGrade[index] = grade;
    }

    // 얻기
    public Grade get(int index) {
        if (index >= this.size) {
            // throw nullPointExceptiom
        }
        if (index < 0) {
            // throw error
        }
        return this.arrGrade[index];
    }

    // 길이
    public int length() {
        return this.arrGrade.length;
    }

    // 재조정 (가변배열)
    // TODO (size => "call by value"가 좋나? "call by reference"가 좋나? :: 참조객체를 사용하는게 더 좋다는 판단)
    private void resize(int index) {
        while (index >= this.size) {
            this.size *= 2;
        }
        Grade[] newGrade = new Grade[this.size];
        for (int i = 0; i < this.arrGrade.length; i++) {
            newGrade[i] = this.arrGrade[i];
        }
        this.arrGrade = newGrade;
    }

}
