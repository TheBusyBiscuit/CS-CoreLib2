package io.github.thebusybiscuit.cscorelib2.collections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class OptionalBoolean {

    private static final OptionalBoolean EMPTY = new OptionalBoolean();

    private final boolean isPresent;
    private final boolean value;

    private OptionalBoolean() {
        this.isPresent = false;
        this.value = false;
    }

    private OptionalBoolean(boolean value) {
        this.isPresent = true;
        this.value = value;
    }

    @Nonnull
    public static OptionalBoolean empty() {
        return EMPTY;
    }

    @Nonnull
    public static OptionalBoolean of(boolean value) {
        return new OptionalBoolean(value);
    }

    public boolean getAsBoolean() {
        if (!isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void ifPresent(@Nonnull Consumer<Boolean> consumer) {
        if (isPresent)
            consumer.accept(value);
    }

    public boolean orElse(boolean other) {
        return isPresent ? value : other;
    }

    public boolean orElseGet(@Nonnull BooleanSupplier other) {
        return isPresent ? value : other.getAsBoolean();
    }

    public <X extends Throwable> boolean orElseThrow(@Nonnull Supplier<X> exceptionSupplier) throws X {
        if (isPresent) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalBoolean)) {
            return false;
        }

        OptionalBoolean other = (OptionalBoolean) obj;
        return (isPresent && other.isPresent)
            ? value == other.value
            : isPresent == other.isPresent;
    }

    @Override
    public int hashCode() {
        return isPresent ? Boolean.hashCode(value) : 0;
    }

    @Nonnull
    @Override
    public String toString() {
        return "OptionalBoolean{value=" + (isPresent ? "empty" : value) + '}';
    }
}
