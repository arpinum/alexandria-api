package arpinum.ddd;

public class SortOptions {

    public static SortOptions ASC(String field) {
        return new SortOptions(Order.ASC, field);
    }

    public static SortOptions DESC(String field) {
        return new SortOptions(Order.DESC, field);
    }

    public SortOptions(Order order, String field) {
        this.order = order;
        this.field = field;
    }

    public final String field;
    public final Order order;

    public enum Order {
        ASC, DESC
    }
}


