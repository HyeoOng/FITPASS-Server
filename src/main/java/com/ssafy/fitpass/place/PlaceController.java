package com.ssafy.fitpass.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/loc")
@CrossOrigin(origins = "http://localhost:5173")
public class PlaceController {

    PlaceService placeService;
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/{usersId}")
    public List<Place> getAllPlaces(@PathVariable("usersId") int usersId) {
        return placeService.getAllPlaces(usersId);
    }

    @PostMapping("/search")
    public List<Place> searchPlace(@RequestBody String keywordJson) {
        System.out.println("keywordJson: "+keywordJson);

        // keywordJson에서 "keyword" 값을 추출
        ObjectMapper keywordMapper = new ObjectMapper();
        String keyword = "";
        try {
            JsonNode jsonNode = keywordMapper.readTree(keywordJson);
            keyword = jsonNode.get("keyword").asText(); // "keyword" 값 추출
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }

        // "keyword" 값만 출력
        System.out.println("keyword: " + keyword);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 9fef9c43ffd4340c40f0a1e672d06559");
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String baseUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + keyword;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

        // JSON 문자열을 JsonNode로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // "documents" 배열을 추출하여 Place 객체로 변환
            List<Place> places = new ArrayList<>();
            for (JsonNode node : rootNode.path("documents")) {
                double lat = node.path("y").asDouble();
                double lon = node.path("x").asDouble();
                String placeAddress = node.path("address_name").asText();
                int kakaoMapId = node.path("id").asInt();
                String placeName = node.path("place_name").asText();

                Place place = new Place();
                place.setLatitude(lat);
                place.setLongitude(lon);
                place.setPlaceAddress(placeAddress);
                place.setPlaceName(placeName);
                place.setKakaoMapId(kakaoMapId);

                places.add(place);
            }
            return places;
        } catch (JsonProcessingException e) {
            // JSON 파싱 처리 중 에러 발생 시 처리
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

}
