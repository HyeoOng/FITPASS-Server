package com.ssafy.fitpass.place;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loc")
public class PlaceController {

    PlaceService placeService;
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }



}
