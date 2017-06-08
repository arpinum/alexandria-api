package arpinum.ddd;


import com.google.common.base.*;

public abstract class BaseEntity<TId> implements Entity<TId> {

    protected BaseEntity() {
    }

    protected BaseEntity(TId id) {
        this.id = id;
    }

    @Override
    public TId getId() {
        return id;
    }

    protected void setId(TId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return java.util.Objects.equals(((BaseEntity) o).id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).add("id", id).toString();
    }

    private TId id;
}
