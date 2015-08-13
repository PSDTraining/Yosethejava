package yose;

import static com.vtence.molecule.http.MimeTypes.JSON;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;


public class PowerOfTwo {
    private final Gson gson;

	double prime = 0;
	double square = 0;
	
	public PowerOfTwo(Gson gson){
//		Math.pow(a, b);
		this.gson = gson;
	}
	
	 public void prime(Request request, Response response) throws Exception {
		String number = request.parameter("number");
		StringBuilder result = new StringBuilder();
		if(number!=null){
			int prime = Integer.parseInt(number);
			int i = 0;
		
			
			while(prime!=1){
				
				result.append("2");
				prime = prime/2;
				
				if(prime!=1){
					result.append(",");
				}
			}
		}
        response.contentType(JSON).body(gson.toJson(result));
    }
	
	public static class Prime {
//        public final boolean alive = true;
    }
}
