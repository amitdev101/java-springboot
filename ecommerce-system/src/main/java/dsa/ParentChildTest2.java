package dsa;

class ParentTest {

    // ---------- FIELDS (no polymorphism) ----------
    public String label = "Parent.field"; // will be SHADOWED by Child.label

    // ---------- PRIVATE METHOD (not inherited) ----------
    // Not visible to Child; cannot be overridden.
    private void privateHook() {
        System.out.println("Parent.privateHook()");
    }

    // ---------- INSTANCE METHODS (runtime polymorphism) ----------
    public void speak() {
        System.out.println("Parent.speak()");
    }

    // A template method calling a hook → shows dynamic dispatch cross-calls
    public void runTemplate() {
        System.out.println("Parent.runTemplate() -> about to call hook()");
        hook(); // if object is Child, Child.hook() runs (runtime dispatch)
    }

    protected void hook() {
        System.out.println("Parent.hook()");
    }

    // ---------- STATIC METHODS (compile-time binding / hiding) ----------
    public static void identify() {
        System.out.println("Parent.identify() [static]");
    }
}

class ChildTest extends ParentTest {

    // ---------- FIELD SHADOWING ----------
    public String label = "Child.field"; // shadows Parent.label (no override possible)

    // ---------- PRIVATE METHOD (unrelated to Parent.privateHook) ----------
    // This does NOT override anything; it’s a brand-new method.
    private void privateHook() {
        System.out.println("Child.privateHook()");
    }

    // ---------- INSTANCE OVERRIDING ----------
    @Override
    public void speak() {
        System.out.println("Child.speak()");
    }

    @Override
    protected void hook() {
        System.out.println("Child.hook()");
    }

    // ---------- STATIC HIDING ----------
    // Hides Parent.identify() with same signature (no overriding for statics).
    public static void identify() {
        System.out.println("Child.identify() [static]");
    }

    // Overload (different signature) → chosen at compile time by parameters.
    public static void identify(int x) {
        System.out.println("Child.identify(" + x + ") [static overload]");
    }
}

public class ParentChildTest2 {
    public static void main(String[] args) {

        ParentTest p  = new ParentTest();
        ParentTest up = new ChildTest(); // upcast: reference type=Parent, object type=Child
        ChildTest  c  = new ChildTest();

        System.out.println("==== FIELDS (compile-time by REFERENCE TYPE) ====");
        // Fields do NOT polymorph. The reference type controls which field is used.
        System.out.println("p.label  = " + p.label);     // Parent.field
        System.out.println("up.label = " + up.label);    // Parent.field (ref type is Parent)
        System.out.println("c.label  = " + c.label);     // Child.field
        // Casting changes the *reference type* used for lookup:
        System.out.println("((Child)up).label = " + ((ChildTest) up).label); // Child.field

        System.out.println("\n==== INSTANCE METHODS (runtime by OBJECT TYPE) ====");
        p.speak();   // Parent.speak()
        up.speak();  // Child.speak()   ← runtime dispatch (object is Child)
        c.speak();   // Child.speak()

        System.out.println("\n==== TEMPLATE CALL (dynamic dispatch across calls) ====");
        p.runTemplate();   // Parent.runTemplate() -> Parent.hook()
        up.runTemplate();  // Parent.runTemplate() -> Child.hook()  ← polymorphism within parent code
        c.runTemplate();   // Parent.runTemplate() -> Child.hook()

        System.out.println("\n==== STATIC METHODS (compile-time by REFERENCE TYPE) ====");
        // Static calls are bound by the REFERENCE type at compile time:
        p.identify();      // Parent.identify() [static]
        up.identify();     // Parent.identify() [static]  ← ref type is Parent
        c.identify();      // Child.identify()  [static]  ← ref type is Child
        // Preferred style is ClassName.method():
        ParentTest.identify(); // Parent.identify() [static]
        ChildTest.identify();  // Child.identify()  [static]

        System.out.println("\n==== STATIC OVERLOAD (compile-time by PARAMETERS) ====");
        ChildTest.identify(42); // Child.identify(42) [static overload]

        System.out.println("\n==== PRIVATE METHODS (not inherited) ====");
        // p.privateHook();   // ❌ compile error: private in Parent
        // up.privateHook();  // ❌ compile error: still private in Parent
        // c.privateHook();   // ❌ compile error: private in Child
        // Note: Child.privateHook() is unrelated to Parent.privateHook().

        System.out.println("\n==== BONUS: CAST + STATIC ====");
        // Even with a cast, static binding is by the reference type used in the call site:
        ((ChildTest) up).identify(); // Child.identify() [static] because reference type after cast is Child
    }
}

