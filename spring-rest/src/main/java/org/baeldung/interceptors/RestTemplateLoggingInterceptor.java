package org.baeldung.interceptors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        response.getHeaders().add("Foo", "bar");
        return response;
    }

    private void logRequest(byte[] body) {
        String payLoad = StringUtils.EMPTY;
        if (body.length > 0) {
            payLoad = new String(body, StandardCharsets.UTF_8);
        }
        System.out.println("Request Body > " + payLoad);
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        String payLoad = StringUtils.EMPTY;
        long contentLength = response.getHeaders()
            .getContentLength();
        if (contentLength != 0) {
            payLoad = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        }
        System.out.println("Response Body > " + payLoad);
    }
}
