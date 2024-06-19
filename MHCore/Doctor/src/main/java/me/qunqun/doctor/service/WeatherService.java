package me.qunqun.doctor.service;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.reps.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class WeatherService {

    private final WebClient.Builder webClientBuilder;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.id}")
    private String apiId;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService() {
        webClientBuilder =  WebClient.builder();
    }

    public WeatherResponse getWeather(String sheng, String place) {
        webClientBuilder.baseUrl(apiUrl);
        try {
            return webClientBuilder.build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("id", apiId)
                            .queryParam("key", apiKey)
                            .queryParam("sheng", sheng)
                            .queryParam("place", place)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(response -> {
                        // 解析响应并转换为 WeatherResponse 对象
                        return parseResponse(response);
                    })
                    .onErrorResume(WebClientResponseException.class, ex -> {
                        if (ex.getRawStatusCode() == 200 && ex.getResponseBodyAsString().contains("html")) {
                            String responseBody = ex.getResponseBodyAsString();
                            System.err.println("Received HTML response instead of JSON: " + responseBody);
                            return Mono.empty();
                        }
                        return Mono.error(ex);
                    })
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private WeatherResponse parseResponse(String response) {
        JSONObject json = new JSONObject(response);
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setPrecipitation(json.getDouble("precipitation"));
        weatherResponse.setTemperature(json.getDouble("temperature"));
        weatherResponse.setPressure(json.getInt("pressure"));
        weatherResponse.setHumidity(json.getInt("humidity"));
        weatherResponse.setWindDirection(json.getStr("windDirection"));
        weatherResponse.setWindDirectionDegree(json.getInt("windDirectionDegree"));
        weatherResponse.setWindSpeed(json.getDouble("windSpeed"));
        weatherResponse.setWindScale(json.getStr("windScale"));
        weatherResponse.setCode(json.getInt("code"));
        weatherResponse.setPlace(json.getStr("place"));
        weatherResponse.setWeather1(json.getStr("weather1"));
        weatherResponse.setWeather2(json.getStr("weather2"));
        return weatherResponse;
    }
}
