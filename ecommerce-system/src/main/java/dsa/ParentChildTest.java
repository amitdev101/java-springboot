package dsa;

class Parent {
    private int baseVariable;

    private void display() {
        System.out.println("Parent display");
    }

    public static void staticDisplay(){
        System.out.println("Parent staticDisplay called");
    }

    public int getBaseVariable() {
        return baseVariable;
    }

    public void setBaseVariable(int baseVariable) {
        this.baseVariable = baseVariable;
    }
}

class Child extends Parent {
    public void display() {
        System.out.println("Child display");
    }

    public static void staticDisplay(){
        System.out.println("Child staticDisplay called ");
    }

    public static void staticDisplay(int x){
        System.out.println("Child staticDisplay called " + x);
    }


}

public class ParentChildTest {
        public static void main(String[] args) {
            Parent parentObj = new Child();
            Child childObj = new Child();
            childObj.display();  // output: Child display
            // parentObj.display(); // compilation error display() has private access in dsa.Parent

            parentObj.staticDisplay(); // output: Parent staticDisplay called
            // parentObj.staticDisplay(10); // compilation error as parent pointer don't have the overloaded method.
            childObj.staticDisplay(); // output: Parent staticDisplay called
            childObj.staticDisplay(5); // output: Child staticDisplay called 5


            //Parent p = new ();

    }
}
