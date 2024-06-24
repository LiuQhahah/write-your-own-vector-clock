package com.liuqhahah.writeyourownvectorclock;

import java.util.UUID;
import java.util.regex.Pattern;

public class UuidNode implements Node, Comparable<UuidNode> {


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
  public int compareTo(UuidNode o) {
    return key.compareTo(o.key);
  }

  private static final Pattern REGEX = Pattern.compile(
      "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");


  public static UuidNode of(final String key) {
    Preconditions.checkNotNull(key);
    Preconditions.checkArgument(key.length() == 36);
    Preconditions.checkArgument(UuidNode.REGEX.matcher(key).matches());
    return UuidNode.of(UUID.fromString(key));
  }

  public static UuidNode of(final UUID key) {
    Preconditions.checkNotNull(key);
    return new UuidNode(key);
  }

  private final UUID key;

  private UuidNode(final UUID key) {
    this.key = key;
  }


  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    return key.equals(((UuidNode) obj).key);
  }


  public UUID getKey() {
    return key;
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public String toString() {
    return key.toString();
  }
}
