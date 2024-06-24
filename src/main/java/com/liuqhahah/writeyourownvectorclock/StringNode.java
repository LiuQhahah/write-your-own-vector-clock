package com.liuqhahah.writeyourownvectorclock;

import java.util.UUID;

public class StringNode implements Node, Comparable<StringNode> {

  /**
   * Compares this object with the specified object for order.  Returns a negative integer, zero, or
   * a positive integer as this object is less than, equal to, or greater than the specified
   * object.
   *
   * <p>The implementor must ensure {@link Integer#signum
   * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for all {@code x} and {@code y}.
   * (This implies that {@code x.compareTo(y)} must throw an exception if and only if
   * {@code y.compareTo(x)} throws an exception.)
   *
   * <p>The implementor must also ensure that the relation is transitive:
   * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
   *
   * <p>Finally, the implementor must ensure that {@code
   * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z)) == signum(y.compareTo(z))}, for
   * all {@code z}.
   *
   * @param o the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   * or greater than the specified object.
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException   if the specified object's type prevents it from being compared to
   *                              this object.
   * @apiNote It is strongly recommended, but <i>not</i> strictly required that
   * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any class that implements
   * the {@code Comparable} interface and violates this condition should clearly indicate this fact.
   * The recommended language is "Note: this class has a natural ordering that is inconsistent with
   * equals."
   */
  @Override
  public int compareTo(StringNode o) {
    return name.compareToIgnoreCase(o.name);
  }

  private final String name;
  private final int hashCode;

  /**
   * Constructs a new instance of {@link StringNode} with the given name. hashcode is calculated
   * based on the lowercase of the name.
   *
   * @param name
   */
  private StringNode(final String name) {
    this.name = name;
    this.hashCode = name.toLowerCase().hashCode();
  }

  /**
   * Creates a new instance of {@link StringNode} with the given name
   * @param name
   * @return
   */
  public static StringNode newStringNode(final String name) {
    Preconditions.checkNotNull(name);
    Preconditions.checkArgument(!name.isEmpty());
    return new StringNode(name);
  }

  public static StringNode random() {
    return StringNode.newStringNode(UUID.randomUUID().toString());
  }

  /**
   * Compares this object to the specified object. The result is {@code true} if and only if the
   * argument is not
   *
   * @param obj
   * @return
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    return name.equalsIgnoreCase(((StringNode) obj).name);
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  @Override
  public String toString() {
    return name;
  }
}
