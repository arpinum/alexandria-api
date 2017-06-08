package arpinum.saga;


import arpinum.command.CommandBus;
import com.google.common.reflect.TypeToken;
import io.vavr.concurrent.Future;

public interface SagaHandler<TResult, TSaga extends Saga<TResult>> {

    Future<TResult> run(CommandBus bus, TSaga saga);

    default Class<TSaga> sagaType() {
        return (Class<TSaga>) new TypeToken<TSaga>(getClass()) {
        }.getRawType();
    }
}
