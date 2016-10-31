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
        getPresenter().output();
    }

    protected Presenter mPresenter;

    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    protected Presenter getPresenter() {
        return mPresenter;
    }

    public class Presenter{
        public void output(){
            System.out.println("A:output");
        }
    }
}

class B extends A {
    public B() {
        super();
        setPresenter(new Presenter());
    }

    private String a = "b";

    public void bb() {
        visit();
    }
    public class Presenter extends A.Presenter{
        public void output(){
            System.out.println("B:output");
        }
    }
}
