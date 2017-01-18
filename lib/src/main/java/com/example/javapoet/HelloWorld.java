package com.example.javapoet;

public class HelloWorld {
  public static HelloWorld getInstance() {
    return InnerClass.INSTANCE;
  }

  private static class InnerClass {
    private static HelloWorld INSTANCE = new HelloWorld();;
  }
}