package arpinum.ddd;

@SuppressWarnings("UnusedDeclaration")
public abstract class BaseAggregate<TId> extends BaseEntity<TId> implements AggregateRoot<TId> {

    protected BaseAggregate() {
    }

    protected BaseAggregate(TId tId) {
        super(tId);
    }

}
