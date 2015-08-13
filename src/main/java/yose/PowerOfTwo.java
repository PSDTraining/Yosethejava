package yose;

import static com.vtence.molecule.http.MimeTypes.JSON;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import java.util.ArrayList;
import java.util.List;

public class PowerOfTwo {

    private final Gson gson;

    private int number;
    private List<Integer> decomposition;

    public PowerOfTwo(Gson gson) {
        this.gson = gson;
    }

    public void prime(Request request, Response response){
//        String number = request.parameter("number");

        if (request.parameter("number") != null) {
            String numString = request.parameter("number");
            try{
                number = Integer.parseInt(numString);
                double prime = number;
                decomposition = new ArrayList<Integer>();
                int i = 0;

                while (prime != 1) {
                    decomposition.add(2);
                    prime = prime / 2;

                }
                response.contentType(JSON).body(gson.toJson(new Prime(number, decomposition)));
            }catch(NumberFormatException e){
                response.contentType(JSON).body(gson.toJson(new PrimeError(numString)));
            }            
        }
        
    }

    public static class Prime {
        private int number;
        private List<Integer> decomposition;
        
        public Prime(int number, List<Integer> decomposition){
            this.number = number;
            this.decomposition = decomposition;
        }
//        public final boolean alive = true;
    }
    
    public static class PrimeError {
        private String number;
        private String error;
        
        public PrimeError(String notANumber){
            this.number = notANumber;
            this.error = "not a number";
        }
    }
}
