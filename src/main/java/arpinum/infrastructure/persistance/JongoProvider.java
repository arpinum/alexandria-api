package arpinum.infrastructure.persistance;

import org.jongo.Jongo;

public interface JongoProvider {

    Jongo current();
}
