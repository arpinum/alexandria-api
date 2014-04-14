package fr.arpinum.graine.commande;

import com.google.common.collect.Lists;

import java.util.List;

public class ValidationException extends RuntimeException {


    public ValidationException(List<String> messages) {
        this.messages.addAll(messages);
    }

    public List<String> messages() {
        return messages;
    }

    private List<String> messages = Lists.newArrayList();
}
