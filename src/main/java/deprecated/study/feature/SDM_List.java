package deprecated.study.feature;

/**
 * #자료구조에서리스트란?
 * <p>
 * LinkedList (Single-LinkedList//Double-LinkedList//Circular-LinkedList)
 * 자료를 순서대로 한 줄로 저장하는 자료구조
 * 여러 자료가 일직선으로 서로 연결된 선형구조
 * 제일 처음 데이터를 Head
 * 제일 마지막 데이터를 Tail
 * <p>
 * http://interconnection.tistory.com/104
 */

public class SDM_List<T> {

    // [#] 자식노드

    class Node {

        // 노드필드
        public T data;
        public Node prev;
        public Node next;

        // 생성자1
        public Node(T data) {
            this.data = data;
        }

        // 생성자2
        public Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    // [#] 필드영역

    private Node head;
    private Node tail;
    private int size;

    // [#] 메소드영역

    public SDM_List() { /**/ }

    // 삽입 (뒤에서)
    public void append(T t) {
        try {
            if (this.head == null) {
                // System.out.println("최초접근");
                // 최초접근
                Node node = new Node(t);
                this.head = node;
                this.head.prev = this.head;
                this.head.next = this.head;
            } else {
                // System.out.println("후반접근");
                // 후반접근
                Node lastNode = this.loopUntilIndex(this.size - 1); // lastNode;
                Node prevNode = lastNode.prev;                           // prevNode;
                Node node = new Node(t, lastNode, prevNode);
                prevNode.prev = node;
                lastNode.next = node;
            }
            size++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 삭제 (인덱스)
    public void remove(int idx) {
        if (this.size == 1) {
            this.head = null;
            this.size = 0;
        } else if (this.size > 0) {
            Node node = loopUntilIndex(idx);
            node.prev.next = node.next;
            node.next.prev = node.prev;
            this.size--;
        } else {
            // 제거할수없음
        }
    }

    // 얻기 (인덱스)
    public T get(int index) {
        Node node = loopUntilIndex(index);
        return node.data;
    }

    // 검색 (인덱스)
    public int indexOf(T obj) {
        Node loopNode = this.head;
        int count = 0;
        while (loopNode.next != null) {
            if (loopNode.data == obj) return count;
            loopNode = loopNode.next;
            count++;
        }
        return -1;
    }

    // 길이 (전체)
    public int length() {
        return this.size;
    }

    // 검색 (데이터)
    private Node loopUntilIndex(int idx) {
        if (idx == 0) {
            // index "0"
            return this.head;
        } else {
            // index "그외"
            Node node = this.head.next;
            int count = 1;
            while (node != this.head) {
                if (count == idx) return node;
                node = node.next;
                count++;
            }
            return null;
        }
    }

}