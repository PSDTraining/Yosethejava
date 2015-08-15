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
		
//		if (request.parameter("number") != null) {
		if(request.allParameters()!=null){
			Map<String, List<String>> paramList = request.allParameters();
			
			List<Object> listDecomposition = new ArrayList<>();
			for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
				String key = entry.getKey();
				
				if (key.equals("number")) {
				List<String> values = entry.getValue();
			
				for (String value : values) {
					
//					try {
						
						number = Integer.parseInt(value);
						int returnNumber = number;
						decomposition = new ArrayList<Integer>();
						
//						if(number> 1000000){
//							throw new IllegalArgumentException();
//						}

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
						
						listDecomposition.add(new Prime(2, decomposition));
						
						
						
//					} catch (NumberFormatException e) {
//						response.contentType(JSON).body(gson.toJson(new PrimeError(numString)));
//					} catch (IllegalArgumentException ex){
//						response.contentType(JSON).body(gson.toJson(new PrimeError(numString, "too big number (>1e6)")));
//					}
				}
				
				response.contentType(JSON).body(gson.toJson(listDecomposition));
			}
			}
			

		}

	}

	public static class Prime {
		private int number;
		private List<Integer> decomposition;
//		private Map<String, List<String>> param;
		private List<String> lstParam;
		
		
		public Prime(int number, List<Integer> decomposition) {
			this.number = number;
			this.decomposition = decomposition;
		}

//		public Prime(int number, List<Integer> decomposition, Map<String, List<String>> param) {
//			this.number = number;
//			this.decomposition = decomposition;
//			this.param = param;
//		}
		
		public Prime(int number, List<Integer> decomposition, List<String> lstParam) {
			this.number = number;
			this.decomposition = decomposition;
			this.lstParam = lstParam;
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
