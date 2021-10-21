package com.hashedin.eventhub.eventservice.controller;

import com.hashedin.eventhub.eventservice.dto.DownloadEventDetailsDto;
import com.hashedin.eventhub.eventservice.dto.UserEventQueryParamsDto;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.service.EnrollmentOperationService;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.service.EventOperationService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/event")
//@CrossOrigin
public class EnrollmentController {

    @Autowired
    private EnrollmentOperationService enrollmentOperationService;


    @Autowired
    private EventOperationService eventOperationService;

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollUser(@RequestBody UserEventQueryParamsDto userEventQueryParamsDto,
                                        HttpServletRequest request) throws RecordNotFoundException {
        enrollmentOperationService.enrollUserToEvent(request.getHeader("Authorization"), userEventQueryParamsDto);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Successfully enrolled in the event");
        return new ResponseEntity(responseMap,HttpStatus.OK);
    }

    @GetMapping("/getparticipants/{eventId}")
    public  ResponseEntity<?> getParticipantsCountForEvent(@PathVariable Long eventId){
        int count =  enrollmentOperationService.getParticipantsCountOfEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Status", String.valueOf(HttpStatus.OK))
                .body(count);
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<?> getEnrolledEventsForUser(@PathVariable Long userId) {
//        List<EventDto> eventDtoList = enrollmentOperationService.getEventsForUser(userId);
//        return ResponseEntity.status(HttpStatus.OK).header("Status", String.valueOf(HttpStatus.OK)).body(eventDtoList);
//    }

    @GetMapping("/enroll/user/{username}")
    public ResponseEntity<?> getEnrolledEventsForUser(@PathVariable String username,
                                                      HttpServletRequest request) {
        List<EventDto> userFavouriteEventDtoList = enrollmentOperationService
                .getEventsForUser(request.getHeader("Authorization"), username);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Status", String.valueOf(HttpStatus.OK))
                .body(userFavouriteEventDtoList);
    }

    @PostMapping("/cancelenroll")
    public ResponseEntity<?> cancelEnrollment(@RequestBody UserEventQueryParamsDto userEventQueryParamsDto,
                                                 HttpServletRequest request) {
        enrollmentOperationService.cancelEnrollment(request.getHeader("Authorization"), userEventQueryParamsDto);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Successfully removed from enrolled event");
        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @GetMapping(value="/download/user/{eventId}")
    public void createExcelWithTaskConfigurations(@PathVariable Long eventId, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String filename = eventOperationService.getEventById(eventId).getEventName() + "_enrollment.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
// create a csv writer
        StatefulBeanToCsv<DownloadEventDetailsDto> writer = new StatefulBeanToCsvBuilder
                <DownloadEventDetailsDto>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).
                withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false).build();
// write all employees to csv file
        writer.write(enrollmentOperationService.getDetailsForEnrollmentContent(eventId));
//        return ResponseEntity.status(HttpStatus.OK)
//                .header("Status", String.valueOf(HttpStatus.OK))
//                .body("Download Successful");
    }

}
