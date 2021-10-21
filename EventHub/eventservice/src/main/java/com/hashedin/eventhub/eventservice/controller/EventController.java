package com.hashedin.eventhub.eventservice.controller;

import com.hashedin.eventhub.eventservice.exception.FileStorageException;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.service.EventOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/event")
//@CrossOrigin
public class EventController {

    @Autowired
    EventOperationService eventOperationService;

    @PostMapping("/addevent")
    public ResponseEntity<?> save(@RequestBody EventDto eventDto,  HttpServletRequest request) {
        Long eventId = eventOperationService.addEvent(request.getHeader("Authorization"),eventDto);
        return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(eventId);
    }

    @PutMapping("/updateevent")
    public ResponseEntity<?> update(@RequestBody EventDto eventDto,  HttpServletRequest request) {
        Long eventId = eventOperationService.updateEvent(request.getHeader("Authorization"),eventDto);
        return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(eventId);
    }

    @GetMapping("/eventid/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) throws RecordNotFoundException {
        EventDto eventDto = eventOperationService.getEventById(id);
        return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(eventDto);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getEventById(@PathVariable String category) throws RecordNotFoundException {
        List<EventDto> eventDto = eventOperationService.getEventByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(eventDto);
    }


    @GetMapping("/allevents")
    public ResponseEntity<?> getAllEvents() {
        List<EventDto> eventDtoList = eventOperationService.getAllEvents();
        return ResponseEntity.status(HttpStatus.OK).header("Status", String.valueOf(HttpStatus.OK)).body(eventDtoList);
    }

    @GetMapping("/upcomingevents")
    public ResponseEntity<?> getUpcomingEvents() {
        List<EventDto> eventDtoList = eventOperationService.getUpcomingEvents();
        return ResponseEntity.status(HttpStatus.OK).header("Status", String.valueOf(HttpStatus.OK)).body(eventDtoList);
    }

    @GetMapping("/popularevents")
    public ResponseEntity<?> getPpopularEvents() {
        List<EventDto> eventDtoList = eventOperationService.getPopularEvents();
        return ResponseEntity.status(HttpStatus.OK).header("Status", String.valueOf(HttpStatus.OK)).body(eventDtoList);
    }

    @PostMapping(value = "/image/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> updateProfilePic(@RequestParam("image") MultipartFile eventPic,
                                              @PathVariable Long id) throws FileStorageException {
        String message= eventOperationService.updateEventImage(eventPic, id);
        return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(message);
    }

    @GetMapping(value = "/created/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> createdByUser(@PathVariable String username,
                                           HttpServletRequest request) {


//            List<EventDto> userFavouriteEventDtoList = favouriteOperationService
//                    .getFavouriteEvents(request.getHeader("Authorization"), username);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .header("Status", String.valueOf(HttpStatus.OK))
//                    .body(userFavouriteEventDtoList);


        List<EventDto> eventDtos= eventOperationService.eventsCreatedByUser(request.getHeader("Authorization"),username);
        return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(eventDtos);
    }






}
