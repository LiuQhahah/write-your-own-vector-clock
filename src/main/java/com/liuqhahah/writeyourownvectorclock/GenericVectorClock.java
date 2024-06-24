package com.liuqhahah.writeyourownvectorclock;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class GenericVectorClock<N, V extends Version<V>> {

  public static <Nn, Vv extends Version<Vv>> GenericVectorClock<Nn, Vv> of(final Nn node,
      final Vv version) {

    return GenericVectorClock.of(node, version, HashMap::new);
  }


  public static <Nn, Vv extends Version<Vv>> GenericVectorClock<Nn, Vv> of(final Nn node,
      final Vv version, final Supplier<Map<Nn, Vv>> mapFactory) {

    Preconditions.checkNotNull(node);
    Preconditions.checkNotNull(version);
    Preconditions.checkNotNull(mapFactory);

    final Map<Nn, Vv> versions = mapFactory.get();
    versions.put(node, version);
    return new GenericVectorClock<>(node, versions, mapFactory);
  }

  private final N reference;
  private final Map<N, V> versions;
  private final Supplier<Map<N, V>> mapFactory;

  private String lazyToString;


  protected GenericVectorClock(final N reference, final Map<N, V> versions,
      final Supplier<Map<N, V>> mapFactory) {
    this.reference = Preconditions.checkNotNull(reference);
    this.mapFactory = Preconditions.checkNotNull(mapFactory);

    final Map<N, V> map = mapFactory.get();
    map.putAll(versions);
    this.versions = Collections.unmodifiableMap(map);
  }


  public GenericVectorClock<N, V> add(final GenericVectorClock<N, V> other) {
    Preconditions.checkNotNull(other);
    Preconditions.checkArgument(!other.reference.equals(reference));
    final Map<N, V> versions = mapFactory.get();
    versions.putAll(this.versions);

    final V version = other.version();
    versions.merge(other.reference, version, (n, v) -> version.nextClock(v));
    other.versions.forEach((n, v) -> versions.merge(n, v, (nn, vv) -> v.max(vv)));
    versions.put(reference, version().nextClock());

    return new GenericVectorClock<>(reference, versions, mapFactory);
  }

  public GenericVectorClock<N, V> add(final N node, final V version) {
    Preconditions.checkNotNull(node);
    Preconditions.checkNotNull(version);
    Preconditions.checkArgument(!node.equals(reference));

    final Map<N, V> versions = mapFactory.get();
    versions.putAll(this.versions);

    versions.merge(node, version, (n, v) -> version.nextClock(v));

    versions.put(reference, version().nextClock());

    return new GenericVectorClock<>(reference, versions, mapFactory);

  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    final GenericVectorClock other = (GenericVectorClock) obj;
    return reference.equals(other.reference) && versions.equals(other.versions);

  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, versions);
  }

  public GenericVectorClock<N, V> next() {
    final Map<N, V> versions = mapFactory.get();
    versions.putAll(this.versions);
    versions.put(reference, versions.get(reference).nextClock());
    return new GenericVectorClock<>(reference, versions, mapFactory);
  }

  public int size() {
    return versions.size();
  }

  public V version() {
    return versions.get(reference);
  }

  @Override
  public String toString() {
    if (lazyToString.isEmpty()) {
      final StringBuilder formatted = new StringBuilder("[");
      versions.forEach((k, v) -> formatted.append(k).append(":").append(v).append(","));
      formatted.setCharAt(formatted.length() - 1, ']');
      lazyToString = formatted.toString();
    }

    return lazyToString;
  }

  public Optional<V> version(final Node node) {
    return Optional.ofNullable(versions.get(node));
  }
}
