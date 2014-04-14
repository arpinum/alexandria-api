package fr.arpinum.graine.web.restlet.status;

import fr.arpinum.graine.commande.ValidationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

public class ResolveurValidationException implements ResolveurException {


    @Override
    public boolean peutRésourdre(Throwable throwable) {
        return ValidationException.class.isAssignableFrom(throwable.getClass());
    }

    @Override
    public Status status() {
        return Status.CLIENT_ERROR_BAD_REQUEST;
    }

    @Override
    public Representation representation(Throwable throwable) {
        try {
            return new JsonRepresentation(enJson((ValidationException) throwable));
        } catch (JSONException e) {
            return new JsonRepresentation("{}");
        }
    }

    private JSONObject enJson(ValidationException validationException) throws JSONException {
        JSONObject résultat = new JSONObject();
        JSONArray errors = new JSONArray();
        résultat.put("errors", errors);
        validationException.messages().forEach((message) -> {
            JSONObject error = new JSONObject();
            placeMessage(message, error);
            errors.put(error);
        });
        return résultat;
    }

    private void placeMessage(String message, JSONObject error) {
        try {
            error.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
