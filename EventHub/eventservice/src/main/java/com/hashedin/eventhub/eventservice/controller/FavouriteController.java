package com.hashedin.eventhub.eventservice.controller;

import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.dto.UserEventQueryParamsDto;
import com.hashedin.eventhub.eventservice.dto.UserFavouriteEventDto;
import com.hashedin.eventhub.eventservice.service.FavouriteOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/event")
public class FavouriteController {

    @Autowired
    private FavouriteOperationService favouriteOperationService;

    @PostMapping("/addtofavourite")
    public ResponseEntity<?> addToFavourite(@RequestBody UserEventQueryParamsDto userEventQueryParamsDto,
                                        HttpServletRequest request) {
        favouriteOperationService.addToFavourite(request.getHeader("Authorization"), userEventQueryParamsDto);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Successfully added as favourite");
        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @PostMapping("/removefavourite")
    public ResponseEntity<?> removeFromFavourite(@RequestBody UserEventQueryParamsDto userEventQueryParamsDto,
                                            HttpServletRequest request) {
        favouriteOperationService.removeFromFavourite(request.getHeader("Authorization"), userEventQueryParamsDto);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Successfully remove from favourite");
        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @GetMapping("/favourite/user/{username}")
    public ResponseEntity<?> getFavouriteEventsForUser(@PathVariable String username,
                                                      HttpServletRequest request) {
        List<EventDto> userFavouriteEventDtoList = favouriteOperationService
                .getFavouriteEvents(request.getHeader("Authorization"), username);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Status", String.valueOf(HttpStatus.OK))
                .body(userFavouriteEventDtoList);
    }
}
