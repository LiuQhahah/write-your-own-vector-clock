package com.liuqhahah.writeyourownvectorclock;

public class Preconditions {


  /**
   * check if the expression is true
   *
   * @param expression
   */
  public static void checkArgument(final boolean expression) {
    if (!expression) {
      throw new IllegalArgumentException();
    }
  }


  /**
   * check if the reference is null
   *
   * @param reference
   * @param <T>
   * @return
   */
  public static <T> T checkNotNull(final T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }


  private Preconditions() {
  }
}
