package arpinum.ddd;

import java.util.UUID;

@SuppressWarnings("UnusedDeclaration")
public abstract class EntiteDeBaseAvecUuid extends BaseEntity<UUID> {

    protected EntiteDeBaseAvecUuid() {
        super(UUID.randomUUID());
    }
}
