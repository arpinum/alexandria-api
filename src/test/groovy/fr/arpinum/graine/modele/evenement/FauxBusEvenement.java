package fr.arpinum.graine.modele.evenement;

import com.google.common.collect.Maps;

import java.util.Map;

public class FauxBusEvenement implements BusEvenement {

    @Override
    public void publie(Evenement evenement) {
        evenements.put(evenement.getClass(), evenement);
    }

    public <T extends Evenement> T dernierEvement(final Class<T> type) {
        return (T) evenements.get(type);
    }


    private Map<Class<? extends Evenement>, Evenement> evenements = Maps.newHashMap();
}
