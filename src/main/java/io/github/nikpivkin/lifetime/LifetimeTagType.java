package io.github.nikpivkin.lifetime;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class LifetimeTagType implements PersistentDataType<Long, Lifetime> {

  public static final LifetimeTagType INSTANCE = new LifetimeTagType();

  @Override
  public @NotNull Class<Long> getPrimitiveType() {
    return Long.class;
  }

  @Override
  public @NotNull Class<Lifetime> getComplexType() {
    return Lifetime.class;
  }

  @Override
  public @NotNull Long toPrimitive(
      @NotNull Lifetime complex,
      @NotNull PersistentDataAdapterContext context
  ) {
    return complex.value();
  }

  @Override
  public @NotNull Lifetime fromPrimitive(@NotNull Long primitive,
      @NotNull PersistentDataAdapterContext context) {
    return new Lifetime(primitive);
  }
}
