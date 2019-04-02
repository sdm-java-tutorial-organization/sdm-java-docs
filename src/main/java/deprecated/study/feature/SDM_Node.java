
package deprecated.study.feature;

import deprecated.study.Grade;

class SDM_Node {

    // [#] 필드영역

    public Grade grade;
    public SDM_Node front;
    public SDM_Node next;

    // [#] 메소드영역

    public SDM_Node(Grade grade) {
        this.grade = grade;
    }

    public void pushFront(SDM_Node node) {
        this.front = node;
    }

    public void pushBack(SDM_Node node) {
        this.next = node;
    }
}