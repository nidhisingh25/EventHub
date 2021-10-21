package com.hashedin.eventhub.eventservice.service;

import com.hashedin.eventhub.eventservice.dto.EnrollmentDto;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.exception.FileStorageException;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventOperationService {

    /**
     * This api will persist an Event in the database.
     *
     * @param eventDto
     */
    public Long addEvent(@RequestHeader("Authorization") String token,EventDto eventDto); //dtotodb

    /**
     * This api will return an Event for the given eventid
     *
     * @param eventId
     * @return EventDto
     */
    public EventDto getEventById(Long eventId) throws RecordNotFoundException;

    /**
     * This api will return a List of Event for the given list of eventid
     *
     * @param eventIds
     * @return EventDto
     */
//    public List<EventDto> getEvent(List<Long> eventIds);

    public List<EventDto> getAllEvents();

    public List<EventDto> getEventByCategory(String category) throws RecordNotFoundException;

    public List<EventDto> getUpcomingEvents();

    public List<EventDto> getPopularEvents();

    public String updateEventImage(MultipartFile eventPic, Long id) throws FileStorageException;

    public List<EventDto> eventsCreatedByUser(@RequestHeader("Authorization") String token,
                                      String username);

    public Long updateEvent(String authorization, EventDto eventDto);

}
