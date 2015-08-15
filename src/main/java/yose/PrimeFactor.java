package yose;

import static com.vtence.molecule.http.MimeTypes.JSON;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimeFactor {

	private final Gson gson;

	private int number;
	private List<Integer> decomposition;
	
	private List<Prime> listDecomposition;

	public PrimeFactor(Gson gson) {
		this.gson = gson;
	}

	public void prime(Request request, Response response) {
	
		if(request.allParameters()!=null){
			
			Map<String, List<String>> paramList = request.allParameters();
			
			
			for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
				String key = entry.getKey();
//				if (key.equals("number")) {
					
					List<String> values = entry.getValue();
					listDecomposition = new ArrayList<Prime>();
					
					for (String value : values) {
						
						try {
							
							number = Integer.parseInt(value);
							int returnNumber = number;
							decomposition = new ArrayList<Integer>();
							
							if(number> 1000000){
								throw new IllegalArgumentException();
							}

							while (number != 1) {

								if (number % 2 == 0) {
									decomposition.add(2);
									number = number / 2;
								} else {
									int evenNumber = 3;
									while (evenNumber <= returnNumber) {

										if (number % evenNumber == 0) {
											decomposition.add(evenNumber);
											number = number / evenNumber;
											break;
										}
										evenNumber += 2;
									}

								}

							}

							
//							listDecomposition.add(new Prime(returnNumber, decomposition));
							listDecomposition.add(new Prime(2, decomposition));
						} catch (NumberFormatException e) {
							response.contentType(JSON).body(gson.toJson(new PrimeError(numString)));
						} catch (IllegalArgumentException ex){
							response.contentType(JSON).body(gson.toJson(new PrimeError(numString, "too big number (>1e6)")));
						}

						
					}
//				}
				
				
			}
			
			response.contentType(JSON).body(gson.toJson(listDecomposition));
			

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
