package fr.arpinum.graine.infrastructure.bus;

import com.google.common.util.concurrent.ListenableFuture;

public interface Bus {

    <TReponse> ListenableFuture<ResultatExecution<TReponse>> poste(Message<TReponse> message);

    <TReponse> ResultatExecution<TReponse> posteToutDeSuite(Message<TReponse> message);

}
