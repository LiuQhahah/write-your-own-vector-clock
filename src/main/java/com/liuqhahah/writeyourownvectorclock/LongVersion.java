package com.liuqhahah.writeyourownvectorclock;

/**
 * Represents a version
 */
public class LongVersion implements Version<LongVersion> {

  private static final LongVersion FIRST = new LongVersion(0);

  @Override
  public LongVersion max(LongVersion other) {
    return version < other.version ? other : this;
  }

  public static LongVersion first() {
    return LongVersion.FIRST;
  }


  public static LongVersion of(final long value) {
    Preconditions.checkArgument(value > 0);
    return new LongVersion(value);
  }

  private final long version;

  private LongVersion(final long version) {
    this.version = version;
  }

  @Override
  public LongVersion nextClock() {
    return LongVersion.of(version + 1);
  }


  @Override
  public LongVersion nextClock(LongVersion other) {

    Preconditions.checkNotNull(other);
    return LongVersion.of(Math.max(version, other.version) + 1);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    LongVersion that = (LongVersion) object;
    return version == that.version;
  }

  @Override
  public int hashCode() {
    return Long.hashCode(version);
  }

  @Override
  public String toString() {
    return String.valueOf(version);
  }
}
