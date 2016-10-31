package com.example;

public class Test {

    public static void main(String[] args) {
        B b = new B();
        b.bb();
    }
}

class A {
    private String a = "a";

    public void visit() {
        System.out.println(this.a);
        System.out.println(this.getClass());
    }
}

class B extends A {
    private String a = "b";

    public void bb() {
        visit();
    }
}
