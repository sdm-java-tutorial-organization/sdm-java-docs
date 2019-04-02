package main.basic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Date;

class Doc_Object {

    public static void main(String[] args) {

        /**
         * #Object
         *  자바의 모든 최상위 객체는 Object 클래스입니다.
         *
         * #기능
         *  - equals() :: 객체비교 - 비교연산자인 "=="과 동일한 결과를 도출
         *      - 논리적 동등을 기본으로 하는데 객체는 상관없이 객체가 저장하고 있는 데이터가 동일함을 나타낸다.
         *  - hashCode() :: 객체의 해시코드 - 객체의 해시코드란 객체를 식별할 하나의 정수값을 말합니다.
         *  - toString() :: 객체 문자 정보 - 객체의 문자 정보 리턴
         *  - clone() :: 객체 복제 - 객체 복제는 원본 필드와 동일한 값을 가지는 새로운 객체를 생성하는 일을 말합니다.
         *       - java.lang.Cloneable 인터페이스를 구현해야 .clone() 메소드를 사용할 수 있습니다.
         *       - default가 얄은 복제 임으로 깊은 복제는 @Override를 사용해야 합니다.
         *          - 얕은 복제 (thin clone) :: 단순히 필드값을 복사해서 객체를 복제하는 것을 말함 (참조타입은 공유함)
         *          - 깊은 복제 (deep clone) :: 참조하고 있는 객체도 복제하는 것을 말함, 메소드 @Override 필요
         *  - finalize() :: 객체 소멸자 - 참조하지 않는 객체는 GC가 힙영역에서 자동으로 소멸합니다.
         *       - 객체를 소멸 시킬때 마지막으로 finalize()를 호출합니다.
         *       - 기본적인 실행 내용이 없습니다.
         *       - 만약 객체가 소멸되지 전에 마지막으로 했던 내용을 저장하고 싶다면 @Override할 수 있습니다.
         *
         * */

        // #equals (==) - 논리적동등을 비교합니다. ( 주소값비교(얕은비교) => @Override 값비교 )
        Object objA = new Object();
        Object objB = new Object();

        boolean resultA = objA.equals(objB);
        boolean resultB = objA == objB;
        boolean resultC = objA.equals(objA);
        System.out.println("[equals 비교결과] " + resultA);
        System.out.println("[equals 비교결과] " + resultC);
        System.out.println("[== 비교결과] " + resultB);

        class Member {
            public String id;

            public Member(String id) {
                this.id = id;
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof Member) {
                    Member m = (Member) o;
                    if (id.equals(m.id)) {
                        return true;
                    }
                }
                return false;
            }
        }
        Member mA = new Member("idA");
        Member mB = new Member("idA");
        Member mC = new Member("idB");
        Member mD = new Member("idB");
        boolean mResultA = mA.equals(mB);
        boolean mResultB = mA.equals(mC);
        System.out.println("[Member id 비교 (같은ID)] " + mResultA);
        System.out.println("[Member id 비교 (다른ID)] " + mResultB);

        // #deepEquals (깊은비교) - Objects 클래스
        Objects.deepEquals(null, null);      // true
        Objects.deepEquals(new Object(), null); // false
        Objects.deepEquals(null, new Object()); // false

        // #hashCode() - 객체를 식별할 하나의 정수값

        class Key {
            public int number;

            public Key(int n) {
                this.number = n;
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof Key) {
                    Key k = (Key) o;
                    if (this.number == k.number) {
                        return true;
                    }
                }
                return false;
            }
            //            @Override
            //            public int hashCode() {
            //                return number;
            //            }
        }

        class KeyB {
            public String number;

            public KeyB(String n) {
                this.number = n;
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof KeyB) {
                    KeyB k = (KeyB) o;
                    if (this.number == k.number) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public int hashCode() {
                return number.hashCode();
            }
        }

        HashMap<Key, String> hashMap = new HashMap<Key, String>();
        hashMap.put(new Key(1), "A");
        String value = hashMap.get(new Key(1));
        System.out.println("[hashMap에서 같은 키로 가져온 값] " + value); // null

        HashMap<KeyB, String> hashMapB = new HashMap<KeyB, String>();
        hashMapB.put(new KeyB("1"), "A");
        String valueB = hashMapB.get(new KeyB("1"));
        System.out.println("[hashMapB에서 같은 키로 가져온 값] " + valueB); // null

        // #toString() - 클래스별로 재정의가 되어있음
        Object objC = new Object();
        Date dateA = new Date();
        System.out.println("[Object의 toString] " + objC.toString());
        System.out.println("[Date의 toString] " + dateA.toString());

        // #clone - 기본얕은복제

        class CloneClass implements Cloneable {
            public String id;
            public String[] messages;

            public CloneClass(String id, String[] messages) {
                this.id = id;
                this.messages = messages;
            }

            public CloneClass getClone() {
                CloneClass cloned = null;
                try {
                    cloned = (CloneClass) clone(); // clone() => Object리턴
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return cloned;
            }
        }
        CloneClass cc = new CloneClass("hello clone", new String[]{"a", "b", "c"});
        CloneClass ccClone = cc.getClone();
        System.out.println("[오리지널의 아이디] " + cc.id + " " + Arrays.toString(cc.messages));
        System.out.println("[클론의 아이디] " + ccClone.id + " " + Arrays.toString(ccClone.messages));
        System.out.println("[오리지널과 클론의 비교] " + (cc == ccClone)); // false
        cc.id = "bye world";
        cc.messages[0] = "얕은복제?";
        System.out.println("[오리지널의 아이디] " + cc.id + " " + Arrays.toString(cc.messages));
        System.out.println("[클론의 아이디] " + ccClone.id + " " + Arrays.toString(ccClone.messages));

        // 기본으로 제공되는 clone 메소드는 얕은 복제라는 것을 알 수 있다 !

        class DeepCloneClass implements Cloneable {
            public String id;
            public String[] messages;
            public Object obj;

            public DeepCloneClass(String id, String[] messages) {
                this.id = id;
                this.messages = messages;
                this.obj = new Object();
            }

            public CloneClass getClone() {
                CloneClass cloned = null;
                try {
                    cloned = (CloneClass) clone(); // clone() => Object리턴
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return cloned;
            }

            @Override
            public Object clone() throws CloneNotSupportedException {
                DeepCloneClass cloned = (DeepCloneClass) clone(); // 얕은복제

                // 배열의 깊은복제
                cloned.messages = Arrays.copyOf(this.messages, this.messages.length);

                // 객체의 깊은복제
                cloned.obj = new Object();

                return cloned;
            }
        }

        //        // #finalize - 객체소멸자 (deprecated)
        //        class FinalObject {
        //            private int id;
        //            public FinalObject(int id) {
        //                this.id = id;
        //            }
        //            @Override
        //            protected void finalize() throws Throwable {
        //                System.out.println("bye bye, " + id);
        //            }
        //        }
        //        FinalObject fo = new FinalObject(123);
        //        fo = null;
        //        System.gc(); // GC요청

        // => GC의 시점이 불명확해서 시스템의 불안정을 가져옵니다. close() 메소드를 만들어서 직접 호출하는 것이 좋습니다.

    }
}