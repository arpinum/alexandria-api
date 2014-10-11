package fr.arpinum.graine.infrastructure.bus;


import com.google.common.reflect.TypeToken;

public interface CapteurMessage<TCommande extends Message<TReponse>, TReponse> {

    TReponse execute(TCommande commande);

    default Class<TCommande> typeCommande() {
        return (Class<TCommande>) new TypeToken<TCommande>(getClass()) {
        }.getRawType();
    }


}
