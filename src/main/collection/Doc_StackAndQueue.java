package main.collection;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * #LIFO & FIFO
 * Stack(LIFO)
 * Stack의 대표적인 예는 JVM스택입니다.
 * - push(E item) => E
 * - peek() => E
 * - pop() => E
 * - isEmpty()
 * Queue()
 * Queue의 대표적인 예는 스레드풀의 작업큐입니다.
 * - offer(E e) => boolean
 * - peek() => E
 * - poll() => E
 */

public class Doc_StackAndQueue {

    public static void main(String[] args) {

        // # Stack
        Stack<String> stack = new Stack<String>();
        stack.push("a");
        System.out.println(stack.peek()); // a
        stack.push("b");
        System.out.println(stack.peek()); // b
        stack.push("c");
        System.out.println(stack.peek()); // c

        // # Queue
        Queue<String> queue = new LinkedList<String>();
        queue.offer("a");
        System.out.println(queue.peek()); // a
        queue.offer("b");
        System.out.println(queue.peek()); // a
        queue.offer("c");
        System.out.println(queue.peek()); // a
    }
}
