package model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API_TESTING {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://skyscanner50.p.rapidapi.com/api/v1/searchAirport?query=london"))
				.header("X-RapidAPI-Key", "5bdf7d1e5amshd77e7d6fbee5d6cp1cdf9ejsna5b67528fced")
				.header("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response.body());

	}

}
