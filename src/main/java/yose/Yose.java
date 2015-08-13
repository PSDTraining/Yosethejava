package yose;

import com.google.gson.Gson;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.routing.DynamicRoutes;

import java.io.IOException;
import java.net.URI;

public class Yose {

    private final WebServer server;

    public Yose(int port) {
        this.server = WebServer.create(port);
    }

    public void start() throws IOException {
        final Gson gson = new Gson();

        server.start(new DynamicRoutes() {{
        	
        	get("/").to((request, response) -> response.body("Hello Yose<br/>" + contactMeLink()));
            get("/ping").to(new Ping(gson)::pong);
            get("/primeFactors").to(new PowerOfTwo(gson)::prime);

            get("/aboutme").to((request, response) -> response.body(aboutMe()));
        }});
    }

    public String contactMeLink(){
    	StringBuilder builder = new StringBuilder();
    	builder.append("<html>");
    		builder.append("<a id=\"contact-me-link\" href=\"/aboutme\"/>Contact Information</a>");
    	builder.append("</html>");
    	return builder.toString();
    }
    
    public String aboutMe(){
    	StringBuilder builder = new StringBuilder();
    	builder.append("<html>");
    		builder.append("<table>");
    			builder.append("<tr>");
	    			builder.append("<td>");
	    				builder.append("taufiq");
	    			builder.append("</td>");
    			builder.append("</tr>");
    			builder.append("<tr>");
	    			builder.append("<td>");
	    				builder.append("ALI");
	    			builder.append("</td>");
				builder.append("</tr>");
				builder.append("<tr>");
				builder.append("<td>");
					builder.append("ZAHARY");
				builder.append("</td>");
			builder.append("</tr>");
    		builder.append("</table>");
    	builder.append("</html>");
    	return builder.toString();
    }
    
    public URI uri() {
        return server.uri();
    }

    public void stop() throws IOException {
        server.stop();
    }

    private static final int PORT = 0;

    private static int port(String[] args) {
        return args.length > 0 ? Integer.parseInt(args[PORT]) : 8080;
    }

    public static void main(String[] args) throws IOException {
        Yose yose = new Yose(port(args));
        yose.start();
        System.out.print("To play the game visit " + yose.uri());
    }
}