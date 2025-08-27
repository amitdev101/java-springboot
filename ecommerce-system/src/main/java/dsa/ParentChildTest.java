package dsa;

class Parent {
    private String privateVariable;
    public String publicVariable;

    private void display() {
        System.out.println("Parent display");
    }

    public void parentMethod() {
        System.out.println("Parent instance method is called");
    }

    public static void staticDisplay(){
        System.out.println("Parent staticDisplay called");
    }

    public String getPrivateVariable() {
        return privateVariable;
    }

    public void setPrivateVariable(String privateVariable) {
        this.privateVariable = privateVariable;
    }
}

class Child extends Parent {
    public void display() {
        System.out.println("Child display");
    }

    public void childMethod(){
        System.out.println("child instance method is called");
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
            // Method calls are bound by declaration.
            childObj.display();  // output: Child display
            // parentObj.display(); // compilation error display() has private access in dsa.Parent

            parentObj.staticDisplay(); // output: Parent staticDisplay called
            // parentObj.staticDisplay(10); // compilation error as parent pointer don't have the overloaded method.
            childObj.staticDisplay(); // output: Parent staticDisplay called
            childObj.staticDisplay(5); // output: Child staticDisplay called 5

            Parent pp = new Parent();
            pp.publicVariable = "Parent public Variable";
            Parent pc = new Child();
            pc.publicVariable = "Child set through parent type";
            System.out.println(pp.publicVariable); // Parent public Variable
            System.out.println(pc.publicVariable); // Child set through parent type

            parentObj.parentMethod();
            childObj.childMethod();

    }
}
