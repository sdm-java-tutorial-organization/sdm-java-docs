package main.api;

/**
 * [Wrapper클래스]
 * 자바는 기본 타입(8가지)의 값을 갖는 객체를 생성가능합니다.
 * 이런 객체를 Wrapper 객체라고 하는데, 그 이유는 기본 타입의 값을 내부에 두고 포장하기 때문이죠.
 * 포장 객체의 특징은 포장하고 있는 "기본 타입 값을 외부에서 변경할 수 없다는 것"입니다.
 * 만약 내부의 값을 변경하고 싶다면 새로운 포장 객체를 만들어야 하죠.
 * <p>
 * #박싱과언박싱 (Boxing & Unboxing)
 * 박싱 :: 기본 타입의 값을 포장 객체로 만드는 것
 * 언박싱 :: 포장 객체에서 기본 타입 값을 얻어내는 과정
 * <p>
 * #자동박싱과언박싱
 * int 값을 Integer 클래스 변수에 대입하면 자동 박싱 처리를 합니다.
 * 반대로, 기본 타입에 포장 객체가 대입될 경우에는 자동 언박싱이 발생합니다.
 * <p>
 * #문자열을 기본타입 값으로 변환
 * 포장 클래스의 주요 용도는 기본 타입의 값을 박싱해서 포장 객체로 만드는 것이지만,
 * 문자열을 기본 타입 값으로 변환할 때에도 많이 사용합니다.
 * "parse+기본타입"명으로 정적메소드가 존재합니다.
 * <p>
 * <p>
 * #포장값 비교
 * 포장 객체는 내부의 값을 비교하기 위해 "=="와 "!=" 연산자를 사용할 수 없!습니다 (얕은비교안됨)
 * 이 연산자는 내부의 값을 비교하는 것이 아니라 포장객체의 참조를 비교하니까요.
 * 만약 값을 비교하고자 한다면
 * 1. 언박싱된 값을 비교하거나
 * 2. equals() 메소드로 내부 값을 비교, (내부 값을 비교하도록 Override처리 되어 있음)
 * <p>
 * #Integer 생성의 "deprecated"
 * new Integer => Integer.valueOf
 * 생성자를 사용하는 것은 거의 없습니다.
 * 정적 팩토리 valueOf (int)는 공간과 시간 성능이 현저히 향상 될 가능성이 높기 때문에 일반적으로 더 나은 선택입니다.
 * 지정된 int 치를 나타내는 새롭게 할당 할 수 있던 Integer 객체를 구축합니다.
 */
public class Doc_Wrapper {
    public static void main(String[] args) {

        /**
         * #Wrapper클래스
         *  - byte :: Byte
         *  - char :: Character
         *  - short :: Short
         *  - int :: Integer
         *  - long :: Long
         *  - float :: Float
         *  - double :: Double
         *  - boolean :: Boolean
         * */

        // 박싱하는방법
        byte numByte = 10;
        short numShort = 100;
        // Byte obj1 = new Byte(numByte);
        Byte obj1 = Byte.valueOf(numByte);
        // Character obj2 = new Character('가');
        Character obj2 = Character.valueOf('가');
        // Short obj3 = new Short(numShort);
        Short obj3 = Short.valueOf(numShort);
        // Integer obj4 = new Integer(1000);
        Integer obj4 = Integer.valueOf(1000);
        // Long obj5 = new Long(10000);
        Long obj5 = Long.valueOf(10000);
        // Float obj6 = new Float(2.5F);
        Float obj6 = Float.valueOf(2.5F);
        // Double obj7 = new Double(3.5);
        Double obj7 = Double.valueOf(3.5);
        // Boolean obj8 = new Boolean(true);
        Boolean obj8 = Boolean.valueOf(true);

        // 언박싱하는 방법
        byte num1 = obj1.byteValue();
        char num2 = obj2.charValue();
        // ...
        boolean num8 = obj8.booleanValue();

        // 자동박싱이 일어나는 경우
        Integer obj9 = 100;

        // 자동언박싱이 일어나는 경우
        Integer obj10 = Integer.valueOf(200);
        int value1 = obj10; // 자동언박싱
        int value2 = obj10 + 100; // 자동언박싱

        // 문자열 => 기본타입
        byte num9 = Byte.parseByte("10");
        short num10 = Short.parseShort("100");
        // ...
        boolean num16 = Boolean.parseBoolean("true");


    }
}