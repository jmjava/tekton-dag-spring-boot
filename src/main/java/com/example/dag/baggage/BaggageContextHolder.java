package com.example.dag.baggage;

public final class BaggageContextHolder {

  private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

  private BaggageContextHolder() {}

  public static void set(String value) {
    HOLDER.set(value);
  }

  public static String get() {
    return HOLDER.get();
  }

  public static void clear() {
    HOLDER.remove();
  }
}
