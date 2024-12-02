package com.ssafy.fitpass.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.exception.UserException;
import com.ssafy.fitpass.post.Post;
import com.ssafy.fitpass.user.dto.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/loc")
@CrossOrigin(origins = "http://localhost:5173")
public class PlaceController {

    PlaceService placeService;
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public Map<String, Object> getAllPlaces(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.put("flag", false);
            response.put("code", "SPL0001");
            return response;
        }
        try {
            RetUser retUser = (RetUser) session.getAttribute("user");
            if(retUser == null) {
                response.put("flag", false);
                response.put("code", "SPL0001");
                return response;
            }
            int userId = retUser.getUserId();
            List<Place> places = placeService.getAllPlaces(userId);
            response.put("flag", true);
            response.put("places", places);
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @GetMapping("/name/{placeId}")
    public Map<String, Object> getPlaceName(@PathVariable("placeId") int placeId) {
        Map<String, Object> response = new HashMap<>();
        if(placeId <= 0) {
            response.put("flag", false);
            response.put("code", "SPL0001");
            return response;
        }
        try{
            String name = placeService.getPlaceName(placeId);
            response.put("flag", true);
            response.put("name", name);
            return response;
        }catch(UserException e){
            response.put("flag", false);
            if(e.getErrorCode().equals("NI")){
                response.put("code", "UAL0007");
            }
            return response;
        }catch(RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @PostMapping("/search")
    public Map<String, Object> searchPlace(@RequestBody String keywordJson) {
        Map<String, Object> response = new HashMap<>();
        try {
            if(keywordJson.isEmpty() || keywordJson.equals("")) {
                response.put("flag", false);
                response.put("code", "UAL0007");
                return response;
            }

            // keywordJson에서 "keyword" 값을 추출
            ObjectMapper keywordMapper = new ObjectMapper();
            String keyword = "";
            JsonNode jsonNode = keywordMapper.readTree(keywordJson);
            keyword = jsonNode.get("keyword").asText(); // "keyword" 값 추출

            if(keyword.isEmpty() || keyword.equals("")) {
                response.put("flag", false);
                response.put("code", "UAL0007");
                return response;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK {KAKAO_RESTAPI_KEY}");
            HttpEntity<String> entity = new HttpEntity<>("", headers);

            String baseUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + keyword;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

            // JSON 문자열을 JsonNode로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(resp.getBody());

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
            response.put("flag", true);
            response.put("places", places);
            return response;

        }catch(JsonProcessingException e){
            response.put("flag", false);
            response.put("code", "JAL0001");
            return response;
        }catch(RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @PostMapping("/list")
    public Map<String, Object> getPlaces(@RequestBody List<Post> posts) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Place> places = placeService.getMyPlaces(posts);
            response.put("flag", true);
            response.put("places", places);
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

}
