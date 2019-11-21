package apiConnection.apiServices;

import exceptions.MyException;
import json.generic.GenericConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public abstract class ApiService<T> {

    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    static HttpRequest requestGet(final String path, String[] headersHost, String[] headersKey) throws URISyntaxException {

        return HttpRequest.newBuilder()
                .uri(new URI(path))
                .version(HttpClient.Version.HTTP_2)
                .header(headersHost[0], headersHost[1])
                .header(headersKey[0], headersKey[1])
                .timeout(Duration.ofSeconds(10)) // HttpTimeoutException
                .GET()
                .build();
    }

    public T getDataFromApi(final String apiUrl, GenericConverter<T> api, String[] headersHost, String[] headersKey) {
        final AtomicReference<String> reference = new AtomicReference<>();

        try {
            if (apiUrl == null) {
                throw new MyException("URL EXCEPTION - API SERVICE");
            }
            if (api == null) {
                throw new MyException("API GENERIC CONVERTER EXCEPTION - API SERVICE");
            }

            CountDownLatch countDownLatch = new CountDownLatch(1);
            CompletableFuture<HttpResponse<String>> response1 = HttpClient
                    .newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .sendAsync(requestGet(apiUrl, headersHost, headersKey), HttpResponse.BodyHandlers.ofString());
            response1.thenAccept(res -> {

             //   System.out.println("RES BODY" + res.body());
                reference.set(res.body());
                countDownLatch.countDown();
            });
        //    System.out.println("AWAIT");
            countDownLatch.await(10, TimeUnit.SECONDS);
            countDownLatch.countDown();

            if (reference.get() == null) {
                throw new MyException("VALUE FROM API IS NULL - EXCEPTION API SERVICE");
            }
            if (Pattern.compile("ValidationErrors").matcher(reference.get()).find()) {
                throw new MyException("VALUE FROM API VALIDATION ERROR" + reference.get());
            }
            if (Pattern.compile("Parameter 'matchday' is expected to be an integer in the range")
                    .matcher(reference.get()).find()) {
                throw new MyException("PODALES ZLA KOLEJKE LIGOWA");
            }
            if (Pattern.compile("city not found")
                    .matcher(reference.get()).find()) {
                throw new MyException("NIE ZNALEZIONO MIASTA");
            }

        } catch (InterruptedException |
                URISyntaxException e) {
            throw new MyException("API SERVICE EXCEPTION");
        }
        return api.fromJson(reference.get(), type)
                .orElseThrow(() -> new MyException("PARSING JSON EXCEPTION, CANNOT MAP OBJECT TO CLASS: " +
                        type.getTypeName() + " - API SERVICE"));
    }
}

