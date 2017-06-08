package arpinum.command;

import io.vavr.concurrent.Future;

public interface CommandBus {

    <TReponse> Future<TReponse> send(Command<TReponse> message);

}
