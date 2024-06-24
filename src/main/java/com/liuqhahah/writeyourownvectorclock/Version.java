package com.liuqhahah.writeyourownvectorclock;

public interface Version<T> {

  T max(T other);

  T nextClock();

  T nextClock(T other);
}
