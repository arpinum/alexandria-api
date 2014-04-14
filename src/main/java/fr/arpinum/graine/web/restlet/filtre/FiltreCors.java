package fr.arpinum.graine.web.restlet.filtre;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.routing.Filter;
import org.restlet.util.Series;

@SuppressWarnings("UnusedDeclaration")
public class FiltreCors extends Filter {
    public FiltreCors(Context context) {
        super(context);
    }

    @Override
    protected int beforeHandle(Request request, Response response) {
        if (Method.OPTIONS.equals(request.getMethod())) {
            Series<Header> requestHeaders = getRequestHeaders(request);
            String origin = requestHeaders.getFirstValue("Origin", true);
            Series<Header> responseHeaders = getResponseHeaders(response);
            responseHeaders.add("Access-Control-Allow-Origin", origin);
            responseHeaders.add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            responseHeaders.add("Access-Control-Allow-Headers", "accept, origin, X-Requested-With, Content-Type");
            responseHeaders.add("Access-Control-Allow-Credentials", "true");
            responseHeaders.add("Access-Control-Max-Age", "60");
            response.setEntity(new EmptyRepresentation());
            response.setStatus(Status.SUCCESS_OK);
            return Filter.SKIP;
        }

        return super.beforeHandle(request, response);
    }

    @Override
    protected void afterHandle(Request request, Response response) {
        if (!Method.OPTIONS.equals(request.getMethod())) {
            Series<Header> requestHeaders = getRequestHeaders(request);
            String origin = requestHeaders.getFirstValue("Origin", true);
            getResponseHeaders(response).add("Access-Control-Allow-Origin", origin);
        }
        super.afterHandle(request, response);
    }

    private Series<Header> getResponseHeaders(Response response) {
        Series<Header> responseHeaders = responseHeaders(response);
        if (responseHeaders == null) {
            responseHeaders = new Series<>(Header.class);
            response.getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        return responseHeaders;
    }

    @SuppressWarnings("unchecked")
    private Series<Header> responseHeaders(Response response) {
        return (Series<Header>) response.getAttributes().get("org.restlet.http.headers");
    }

    @SuppressWarnings("unchecked")
    private Series<Header> getRequestHeaders(Request request) {
        return (Series<Header>) request.getAttributes().get("org.restlet.http.headers");
    }

}
