package com.alabtaal.library.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LambdaExceptionUtil {

  private static final Logger LOG = LoggerFactory.getLogger(LambdaExceptionUtil.class);

  /**
   * .forEach(rethrowConsumer(name -> System.out.println(Class.forName(name)))); or .forEach(rethrowConsumer(ClassNameUtil::println));
   */
  public static <T, E extends Exception> Consumer<T> rethrowConsumer(
      final Consumer_WithExceptions<T, E> consumer) throws E {
    return t -> {
      try {
        consumer.accept(t);
      } catch (final Exception e) {
        Miscellaneous.logException(LOG, e);
        throwAsUnchecked(e);
      }
    };
  }

  public static <T, U, E extends Exception> BiConsumer<T, U> rethrowBiConsumer(
      final BiConsumer_WithExceptions<T, U, E> biConsumer) throws E {
    return (t, u) -> {
      try {
        biConsumer.accept(t, u);
      } catch (final Exception e) {
        Miscellaneous.logException(LOG, e);
        throwAsUnchecked(e);
      }
    };
  }

  /**
   * .map(rethrowFunction(name -> Class.forName(name))) or .map(rethrowFunction(Class::forName))
   */
  public static <T, R, E extends Exception> Function<T, R> rethrowFunction(
      final Function_WithExceptions<T, R, E> function) throws E {
    return t -> {
      try {
        return function.apply(t);
      } catch (final Exception e) {
        Miscellaneous.logException(LOG, e);
        throwAsUnchecked(e);
        return null;
      }
    };
  }

  /**
   * rethrowSupplier(() -> new StringJoiner(new String(new byte[]{77, 97, 114, 107}, "UTF-8"))),
   */
  public static <T, E extends Exception> Supplier<T> rethrowSupplier(
      final Supplier_WithExceptions<T, E> function) throws E {
    return () -> {
      try {
        return function.get();
      } catch (final Exception e) {
        Miscellaneous.logException(LOG, e);
        throwAsUnchecked(e);
        return null;
      }
    };
  }

  /**
   * uncheck(() -> Class.forName("xxx"));
   */
  public static void uncheck(final Runnable_WithExceptions<Exception> t) {
    try {
      t.run();
    } catch (final Exception e) {
      Miscellaneous.logException(LOG, e);
      throwAsUnchecked(e);
    }
  }

  /**
   * uncheck(() -> Class.forName("xxx"));
   */
  public static <R, E extends Exception> R uncheck(final Supplier_WithExceptions<R, E> supplier) {
    try {
      return supplier.get();
    } catch (final Exception e) {
      Miscellaneous.logException(LOG, e);
      throwAsUnchecked(e);
      return null;
    }
  }

  /**
   * uncheck(Class::forName, "xxx");
   */
  public static <T, R, E extends Exception> R uncheck(
      final Function_WithExceptions<T, R, E> function,
      final T t) {
    try {
      return function.apply(t);
    } catch (final Exception e) {
      Miscellaneous.logException(LOG, e);
      throwAsUnchecked(e);
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  private static <E extends Throwable> void throwAsUnchecked(final Exception exception) throws E {
    throw (E) exception;
  }

  @FunctionalInterface
  public interface Consumer_WithExceptions<T, E extends Exception> {

    void accept(T t) throws E;
  }

  @FunctionalInterface
  public interface BiConsumer_WithExceptions<T, U, E extends Exception> {

    void accept(T t, U u) throws E;
  }

  @FunctionalInterface
  public interface Function_WithExceptions<T, R, E extends Exception> {

    R apply(T t) throws E;
  }

  @FunctionalInterface
  public interface Supplier_WithExceptions<T, E extends Exception> {

    T get() throws E;
  }

  @FunctionalInterface
  public interface Runnable_WithExceptions<E extends Exception> {

    void run() throws E;
  }

}
