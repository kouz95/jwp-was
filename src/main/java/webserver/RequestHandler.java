package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Queue<String> requests = acceptRequestOf(in);
            exportResponseTo(out, requests);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Queue<String> acceptRequestOf(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        Queue<String> requests = new LinkedList<>();

        while (true) {
            String line = bufferedReader.readLine();
            if (line.equals("")) {
                break;
            }
            requests.add(line);
        }

        return requests;
    }

    private void exportResponseTo(OutputStream out, Queue<String> requests) {
        DataOutputStream dos = new DataOutputStream(out);
        String request = requests.poll();
        RequestParser requestParser = new RequestParser(request);
        String URI = requestParser.getURI();
        byte[] body = "Hello World".getBytes();
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
