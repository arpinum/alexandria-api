package arpinum.ddd;

public class Pair<T, U> {

    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair(first, second);
    }

    public static <T, U> Pair<T, U> of() {
        return (Pair<T, U>) EMPTY_PAIR;
    }

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public final T first;
    public final U second;

    private static final Pair<Object, Object> EMPTY_PAIR = Pair.of(null, null);
}
