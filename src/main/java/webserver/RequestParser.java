package webserver;

public class RequestParser {
    private static final String SP = " ";
    private static final int REQUEST_URI = 1;

    private final String request;

    public RequestParser(String request) {
        this.request = request;
    }

    public String getURI() {
        String[] tokens = request.split(SP);
        return tokens[REQUEST_URI];
    }
}
