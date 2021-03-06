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
            get("/readme").to((request, response) -> response.body(readMe()));
            get("/astroport").to((request, response) -> response.body(theAstroport()).addHeader("content-type", "text/html"));
        }});
    }

    public String readMe(){
    	StringBuilder builder = new StringBuilder();
    	builder.append("<html>");
    	builder.append("<head>");
		builder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		builder.append("</head>");
		builder.append("<p id=\"readme\">YoseTheGame</p>");
		builder.append("</html>");
		
		return builder.toString();
    }
    
    public String primeFactorUI(){
    	StringBuilder builder = new StringBuilder();
    	builder.append("<html>");
    	builder.append("<head>");
		
		builder.append("</head>");
		builder.append("<body>");
		builder.append("<h1 id=\"title\">Prime Factor - version 69 </h1>");
		builder.append("<form >");
		builder.append("<p id=\"invitation\">Insert a number</p>");
		builder.append("<input  type=\"text\" id=\"number\" name=\"number\" >");
		builder.append(" <button id=\"go\" type=\"button\"  onclick=\"ajax_get_json()\">Go</button> ");
		builder.append("<p id=\"result\" name=\"result\"></p>");
		builder.append("</form>");
		
		builder.append("</body>");
		
		builder.append("<script>");
    	builder.append("function ajax_get_json(){ ");
    	builder.append("var results = document.getElementById(\"result\");");
    	builder.append("var num = document.getElementById(\"number\").value;");
    	builder.append("var hr = new XMLHttpRequest();");
    	builder.append("hr.open(\"GET\", \"/primeFactors?number=\"+num, true);");
    	builder.append("hr.setRequestHeader(\"Content-type\", \"application/json\", true);");
    	builder.append("hr.onreadystatechange = function() { ");
    	builder.append("if(hr.readyState == 4 && hr.status == 200) {");
    	builder.append("var data = JSON.parse(hr.responseText); ");
    	builder.append("results.innerHTML =data.number +\" = \"; ");
    	builder.append("for(var obj in data.decomposition){");
    	builder.append("results.innerHTML +=  data.decomposition[obj];");
    	builder.append("if((parseInt(obj)+1) != data.decomposition.length){");
    	builder.append("results.innerHTML += \" x \";");
    	builder.append("}");
    	builder.append("}");
    	builder.append("}");
    	builder.append("}; ");
    	builder.append("hr.send(); ");
    	builder.append("results.innerHTML = \"requesting...\";");
    	builder.append("}");
    	builder.append("</script>");
    
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
    	builder.append("<br>");
    	builder.append("<a id=\"repository-link\" href=\"http://yosejava.herokuapp.com/readme\"> Repository link </a>");
    		
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
		builder.append("function click(id){");
//		builder.append("background-color:red");	
		builder.append("document.getElementById(id).className = \"lost safe\";");	
//		builder.append("document.getElementById(id).className = \"safe\";");	
		builder.append("}");
                
                builder.append("function safeClick(id){");
//		builder.append("background-color:red");	
//		builder.append("document.getElementById(id).className = \"safe\";");		
//		builder.append("document.getElementById(id).innerHTML = \"1\";");	
		builder.append("}");
		builder.append("function load(){");
//			
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
    				builder.append("<td width=\"100px\" style=\"border:1px solid #c5c5c5;border-collapse:collapse\"");
                                builder.append(" class=\"safe\"");
                                builder.append(" onclick=\"click('cell-");
                                builder.append((x+1)+ "x");
                                builder.append((y+1)+"');safeClick('cell-");
                                builder.append((x+1)+ "x");
                                builder.append((y+1)+"');\"");    				
    				builder.append(" id=\"cell-");
    				builder.append((x+1)+ "x");
    				builder.append((y+1)+ "\">123");
//                                if((x+1)==4&&(y+1)==4){
//    					builder.append(1);
//                                }else{
//                                    builder.append(x+1);
//                                    builder.append("x");
//                                    builder.append(y+1);
//                                }    				    				
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