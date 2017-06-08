package arpinum.ddd.event;


import com.google.common.reflect.TypeToken;

@SuppressWarnings("UnusedDeclaration")
public interface EventCaptor<TEvenement extends Event<?>> {

    void execute(TEvenement evenement);

    default Class<TEvenement> eventType() {
        return (Class<TEvenement>) new TypeToken<TEvenement>(getClass()) {
        }.getRawType();
    }
}
