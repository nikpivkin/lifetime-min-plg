package io.github.nikpivkin;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceUtil {

  private ResourceUtil() {
    // companion object
  }

  public static Optional<Exception> listResourcesInJar(Consumer<JarEntry> consumer) {
    try {
      var dir = new File(
          ResourceUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI());
      try (var jar = new JarFile(dir)) {
        jar.entries()
            .asIterator()
            .forEachRemaining(consumer);
      }
    } catch (Exception e) {
      return Optional.of(e);
    }
    return Optional.empty();
  }

}
