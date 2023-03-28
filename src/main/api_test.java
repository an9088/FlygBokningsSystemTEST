package main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class api_test {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://skyscanner-api.p.rapidapi.com/v3/culture/currencies"))
				.header("X-RapidAPI-Key", "5bdf7d1e5amshd77e7d6fbee5d6cp1cdf9ejsna5b67528fced")
				.header("X-RapidAPI-Host", "skyscanner-api.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());

	}

}
