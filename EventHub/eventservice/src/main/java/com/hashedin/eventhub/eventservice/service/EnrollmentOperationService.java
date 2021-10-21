package com.hashedin.eventhub.eventservice.service;

import com.hashedin.eventhub.eventservice.dto.DownloadEventDetailsDto;
import com.hashedin.eventhub.eventservice.dto.UserEventQueryParamsDto;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface EnrollmentOperationService {

    public int getParticipantsCountOfEvent(Long eventId);
    /**
     * This api will enroll an user to an event
     *
     */
    public void enrollUserToEvent(@RequestHeader("Authorization") String token, UserEventQueryParamsDto userEventQueryParamsDto) throws RecordNotFoundException;

    public List<EventDto> getEventsForUser(@RequestHeader("Authorization") String token, String username);

    public void cancelEnrollment(@RequestHeader("Authorization") String token, UserEventQueryParamsDto userEventQueryParamsDto) throws RecordNotFoundException;

    public byte[] createExcel();

    public List<DownloadEventDetailsDto> getDetailsForEnrollmentContent(Long eventId);
}
