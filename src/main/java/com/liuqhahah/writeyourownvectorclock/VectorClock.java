package com.liuqhahah.writeyourownvectorclock;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class VectorClock {


  public static VectorClock init(final String name) {

    return VectorClock.init(StringNode.newStringNode(name));
  }

  public static VectorClock init(final StringNode name) {
    return VectorClock.of(name, LongVersion.first());
  }

  /**
   * Creates a new vector clock with the given node and version
   * @param node
   * @param version
   * @return
   */
  public static VectorClock of(final StringNode node, final LongVersion version) {
    Preconditions.checkNotNull(node);
    Preconditions.checkNotNull(version);

    final Map<StringNode, LongVersion> versions = new TreeMap<>();
    versions.put(node, version);
    return new VectorClock(node, versions);
  }


  private final StringNode currentVersion;
  private final Map<StringNode, LongVersion> versions;

  private String lazyToString;

  protected VectorClock(final StringNode currentVersion, final Map<StringNode, LongVersion> versions) {
    this.currentVersion = Preconditions.checkNotNull(currentVersion);
    this.versions = Collections.unmodifiableMap(new TreeMap<>(versions));
  }

  public int size() {
    return versions.size();
  }

  public VectorClock add(final StringNode node, final LongVersion version) {
    Preconditions.checkNotNull(node);
    Preconditions.checkNotNull(version);
    Preconditions.checkArgument(!node.equals(currentVersion));

    final Map<StringNode, LongVersion> versions = new TreeMap<>(this.versions);
    versions.merge(node, version, (n, v) -> version.nextClock(v));

    versions.put(currentVersion, version().nextClock());

    return new VectorClock(currentVersion, versions);
  }


  public VectorClock add(final VectorClock other) {
    Preconditions.checkNotNull(other);
    Preconditions.checkArgument(!other.currentVersion.equals(currentVersion));

    final Map<StringNode, LongVersion> versions = new TreeMap<>(this.versions);
    versions.merge(other.currentVersion, other.version(), (n, v) -> other.version().nextClock(v));
    other.versions.forEach((n, v) -> versions.merge(n, v, (nn, vv) -> v.max(vv)));

    versions.put(currentVersion, version().nextClock());

    return new VectorClock(currentVersion, versions);
  }

  public LongVersion version() {
    return versions.get(currentVersion);
  }

  public Optional<LongVersion> version(final Node node) {
    return Optional.ofNullable(versions.get(node));
  }


  public VectorClock nextClock() {
    final Map<StringNode, LongVersion> versions = new TreeMap<>(this.versions);
    versions.put(currentVersion, versions.get(currentVersion).nextClock());
    return new VectorClock(currentVersion, versions);
  }

  @Override
  public String toString() {
    if (lazyToString == null) {
      final StringBuilder formatted = new StringBuilder("[");
      versions.forEach((k, v) -> formatted.append(k).append(":").append(v).append(","));
      formatted.setCharAt(formatted.length() - 1, ']');

      lazyToString = formatted.toString();
    }
    return lazyToString;
  }
}
