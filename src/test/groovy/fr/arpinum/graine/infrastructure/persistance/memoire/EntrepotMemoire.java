package fr.arpinum.graine.infrastructure.persistance.memoire;

import com.google.common.collect.Sets;
import fr.arpinum.graine.modele.Entrepot;
import fr.arpinum.graine.modele.Racine;

import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
public class EntrepotMemoire<TId, TRacine extends Racine<TId>> implements Entrepot<TId, TRacine> {

    @Override
    public TRacine get(TId tId) {
        return entites.stream().filter(element -> element.getId().equals(tId)).findFirst().get();
    }

    @Override
    public void ajoute(TRacine racine) {
        entites.add(racine);
    }

    @Override
    public void supprime(TRacine racine) {
        entites.remove(racine);
    }

    protected final Set<TRacine> entites = Sets.newHashSet();
}
