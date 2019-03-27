package main.basic;

public class Doc_Switch {

    public static void main(String[] args) {
        switchString();
    }

    /**
     * 객체의 문자열은 Switch 문에서 문자열과 같다고 판단할까 => 같다고 판단
     * */
    static public void switchString() {
        MyModel myModel = new MyModel();
        switch (myModel.strA) {
            case "a" :
                System.out.println("strA == a");
                break;
            default:
                System.out.println("strA != a");
                break;
        }
    }

    public static class MyModel {
        public String strA = "a";
        public String strB = "b";
    }
}
