package com.phorvat.weatherforecastingapp.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HttpClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> T get(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    public <T> T parseJson(String jsonResponse, String rootNode, Class<T> responseType) throws Exception {
        JsonNode node = objectMapper.readTree(jsonResponse).path(rootNode);
        return objectMapper.treeToValue(node, responseType);
    }
}
