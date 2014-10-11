package fr.arpinum.graine.infrastructure.bus;

import com.google.common.util.concurrent.ListenableFuture;

public interface Bus {

    <TReponse> ListenableFuture<ResultatExecution<TReponse>> envoie(Message<TReponse> message);

    <TReponse> ResultatExecution<TReponse> envoieEtAttendReponse(Message<TReponse> message);

}
