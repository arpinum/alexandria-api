package arpinum.query;

import arpinum.command.Command;
import io.vavr.concurrent.Future;

@SuppressWarnings("UnusedDeclaration")
public interface QueryBus  {

    <TResponse> Future<TResponse> send(Query<TResponse> query);

}
