package com.example.userprofilecoindata.service;

import com.example.userprofilecoindata.entity.CoinDataEntity;
import com.example.userprofilecoindata.entity.UserEntity;
import com.example.userprofilecoindata.repository.CoinDataRepository;
import com.example.userprofilecoindata.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Log4j2
public class CoinDataService {
    private final RestTemplate restTemplate;
    private final CoinDataRepository coinDataRepository;
    private final UserRepository userRepository;

    @Value("${coinmarketcap.api.url}")
    private String apiUrl;
    @Value("${coinmarketcap.api.key}")
    private String apiKey;
    @Value("${coinmarketcap.api.key.value}")
    private String apiKeyValue;

    public CoinDataService(RestTemplate restTemplate, CoinDataRepository coinDataRepository, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.coinDataRepository = coinDataRepository;
        this.userRepository = userRepository;
    }
    public String fetchAndStoreCoinData(Long userId, String symbol) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        String responseData = fetchDataFromApi(apiUrl, apiKey, symbol);
        saveCoinData(coinDataRepository, userId, symbol, responseData);

        log.info("Data Saved in coin data");
        return responseData;
    }

    public String fetchDataFromApi(String apiUrl, String apiKey, String symbol) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(apiKey, apiKeyValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "?symbol=" + symbol, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public static void saveCoinData(CoinDataRepository coinDataRepository, Long userId, String symbol, String responseData) {
        CoinDataEntity coinDataEntity = new CoinDataEntity();
        coinDataEntity.setUserId(userId);
        coinDataEntity.setRequest(symbol);
        coinDataEntity.setResponse(responseData);
        try {
            coinDataRepository.save(coinDataEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}