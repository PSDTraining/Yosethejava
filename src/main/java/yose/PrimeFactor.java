package yose;

import static com.vtence.molecule.http.MimeTypes.JSON;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PrimeFactor {

	private final Gson gson;

	private int number;
	private List<Integer> decomposition;

	public PrimeFactor(Gson gson) {
		this.gson = gson;
	}

	public void prime(Request request, Response response) {
		
		if(request.allParameters()!=null){
			Map<String, List<String>> paramList = request.allParameters();
			
			List<Object> listDecomposition = new ArrayList<>();
			
			
			
			for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
				String key = entry.getKey();
				
				
				if (key.equals("number")) {
				List<String> values = entry.getValue();
			
				for (String value : values) {
					
					try {
						
						number = Integer.parseInt(value);
						
						
						int tempNumber = number;
						decomposition = new ArrayList<Integer>();
						
						if(number> 1000000){
							throw new IllegalArgumentException();
						}

						while (tempNumber != 1) {

							if (tempNumber % 2 == 0) {
								decomposition.add(2);
								tempNumber = tempNumber / 2;
							} else {
								int evenNumber = 3;
								while (evenNumber <= number) {

									if (tempNumber % evenNumber == 0) {
										decomposition.add(evenNumber);
										tempNumber = tempNumber / evenNumber;
										break;
									}
									evenNumber += 2;
								}

							}

						}
						
						listDecomposition.add(new Prime(number, decomposition));

					} catch (NumberFormatException e) {
						listDecomposition.add(new PrimeError(value));
					}catch (IllegalArgumentException ex){
						listDecomposition.add(new PrimeError(value, "too big number (>1e6)"));
					}
						
				}
				
				if(listDecomposition.size()==1){
					response.contentType(JSON).body(gson.toJson(listDecomposition.get(0)));
					
					
				}else{
					response.contentType(JSON).body(gson.toJson(listDecomposition));
				}
			}
			}
			

		}

	}

	public static class Prime {
		private int number;
		private List<Integer> decomposition;
		
		
		public Prime(int number, List<Integer> decomposition) {
			this.number = number;
			this.decomposition = decomposition;
		}
		
	}

	public static class PrimeError {
		private String number;
		private String error;

		public PrimeError(String notANumber) {
			this.number = notANumber;
			this.error = "not a number";
		}
		
		public PrimeError(String notANumber, String error) {
			this.number = notANumber;
			this.error = error;
		}
	}
}
