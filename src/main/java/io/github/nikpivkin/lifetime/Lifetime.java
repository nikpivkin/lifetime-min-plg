package io.github.nikpivkin.lifetime;

public record Lifetime(long value) {

  public Lifetime(long value) {
    this.value = Math.max(0, value);
  }

  public Lifetime increase(Lifetime other) {
    return new Lifetime(value + other.value);
  }

  public Lifetime decrease(Lifetime other) {
    return new Lifetime(Math.max(0, value - other.value));
  }

  public Lifetime decrement() {
    return new Lifetime(value - 1);
  }

  public Lifetime mul(float val) {
    return new Lifetime((long) (value * val));
  }

  public boolean isOut() {
    return value <= 0;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
