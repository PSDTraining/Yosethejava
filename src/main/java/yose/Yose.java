package yose;

import com.google.gson.Gson;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.routing.DynamicRoutes;

import java.io.IOException;
import java.net.URI;
import java.util.Random;

public class Yose {

    private final WebServer server;

    public Yose(int port) {
        this.server = WebServer.create(port);
    }

    public void start() throws IOException {
        final Gson gson = new Gson();

        server.start(new DynamicRoutes() {{
        	
            get("/").to((request, response) -> response.body(frontPage()).addHeader("content-type", "text/html"));
            get("/ping").to(new Ping(gson)::pong);
            get("/primeFactors").to(new PrimeFactor(gson)::prime);
            get("/primeFactors/ui").to((request, response) -> response.body(primeFactorUI()).addHeader("content-type", "text/html"));
            
            get("/minesweeper").to((request, response) -> response.body(mineSweeper()));
            get("/aboutme").to((request, response) -> response.body(aboutMe()));
            get("/astroport").to((request, response) -> response.body(theAstroport()).addHeader("content-type", "text/html"));
        }});
    }

    public String primeFactorUI(){
    	StringBuilder builder = new StringBuilder();
    	builder.append("<html>");
    	builder.append("<head>");
		builder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		builder.append("</head>");
		builder.append("<h1 id=\"title\">Prime Factor</h1>");
		builder.append("<br>");
		builder.append("<p id=\"invitation\">Insert a number<p>");
		builder.append("<form action=\"http://yosejava.herokuapp.com/primeFactors/\" method=\"post\">");
		builder.append("<input id=\"number\" name=\"number\"/>");
		builder.append("<input type=\"submit\" id=\"go\" value=\"go\"/>");
		builder.append("</form>");
		 builder.append("</html>");
		
		return builder.toString();
    }
    public String theAstroport(){
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<head>");
        builder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        builder.append("</head>");
        builder.append("<p id=\"astroport-name\"></p>");
        for(int x=1;x<4;x++){
            builder.append("<div id=\"gate-"+x+"\">");
                builder.append("<div id=\"ship-"+x+"\">");
                    builder.append("Ship " + x);
                builder.append("</div>");
            builder.append("</div>");
        }
        builder.append("</html>");
        return builder.toString();
    }
    
    public String frontPage(){
    	StringBuilder builder = new StringBuilder();
    	builder.append("<html>");
    	builder.append("<head>");
		builder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		builder.append("</head>");
		builder.append("<p></p>");
    	builder.append("Hello Yose <br/>");
    		builder.append("<a id=\"contact-me-link\" href=\"/aboutme\"/>Contact Information</a><br>");
    		builder.append("<a id=\"ping-challenge-link\" href=\"/ping\"/>Ping</a>");
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
    
    public String mineSweeper(){
    	String[][] array = new String[8][8];
    	StringBuilder builder = new StringBuilder();
    	builder.append("<html>");
    	builder.append("<head>");
		builder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		builder.append("<script type=\"text/css\">");
		builder.append(".lost{");
		builder.append("background-color:red");
		builder.append("}");
		builder.append("</script>");
		builder.append("<script type=\"text/javascript\">");
		builder.append("function load(id){");
//		builder.append("background-color:red");
		builder.append("alert(id);");		
		builder.append("document.getElementById(id).className = \"lost\";");		
		builder.append("}");
		builder.append("</script>");
		builder.append("</head>");
    	builder.append("<h1 id=\"title\">Minesweeper</h1>");
    	builder.append("<p>&nbsp;</p>");
    	builder.append("<table style=\"border:1px solid #c5c5c5\">");
    	for(int x=0;x<array.length;x++){
    		builder.append("<tr>");
    		Random random = new Random();
    			int randomNumber = random.nextInt(8);
    			for(int y=0;y<array[x].length;y++){
    				builder.append("<td style=\"border:1px solid #c5c5c5;border-collapse:collapse\"");
    				if(randomNumber == (y+1)){
//    					builder.append(" class=\"lost\" ");
    					builder.append(" onclick=\"load('cell-");
    					builder.append((x+1)+ "x");
        				builder.append((y+1)+"')\"");
    				}
    				builder.append(" id=\"cell-");
    				builder.append((x+1)+ "x");
    				builder.append((y+1)+ "\">");
    				builder.append(x+1);
    				builder.append("x");
    				builder.append(y+1);
    				builder.append("</td>");
    			}
    		builder.append("</tr>");
    	}
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