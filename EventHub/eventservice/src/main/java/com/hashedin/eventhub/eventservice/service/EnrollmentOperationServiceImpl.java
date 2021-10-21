package com.hashedin.eventhub.eventservice.service;

import com.hashedin.eventhub.eventservice.dto.CategoryDto;
import com.hashedin.eventhub.eventservice.dto.DownloadEventDetailsDto;
import com.hashedin.eventhub.eventservice.dto.UserEventQueryParamsDto;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.entity.Event;
import com.hashedin.eventhub.eventservice.entity.UserDetailsEntity;
import com.hashedin.eventhub.eventservice.entity.UserEnrollmentEntity;
import com.hashedin.eventhub.eventservice.exception.AlreadyExistsException;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.feign.UserFeign;
import com.hashedin.eventhub.eventservice.repository.EventRepository;
import com.hashedin.eventhub.eventservice.repository.UserEnrollmentRepository;
import com.hashedin.eventhub.eventservice.utils.ObjectConversion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentOperationServiceImpl implements EnrollmentOperationService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectConversion convertObject;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private UserEnrollmentRepository userEnrollmentRepository;


    @Override
    public int getParticipantsCountOfEvent(Long eventId) {
        return userEnrollmentRepository.getCountOfParticipants(eventId);
    }

    public void enrollUserToEvent(@RequestHeader("Authorization") String token, UserEventQueryParamsDto userEventQueryParamsDto) throws RecordNotFoundException {
        persist(token,userEventQueryParamsDto,"ENROLLED");
    }

//    @Override
//    public List<EventDto> getEventsForUser(Long userId) {
//        return userEnrollmentRepository.getEnrolledEventForUser(userId).stream()
//                .map( e -> (EventDto) convertObject.convert(e,EventDto.class))
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public List<EventDto> getEventsForUser(String token, String username) {
        UserDetailsEntity userEntity = (UserDetailsEntity) convertObject
                .convert(userFeign.getUser(token,username), UserDetailsEntity.class);
        List<EventDto> eventDtos = new ArrayList<>();
        userEnrollmentRepository.findByUserDetailsEntityAndStatus(userEntity, "ENROLLED").forEach(enroll -> {
            Event event = enroll.getEvent();
            EventDto eventDto = ((EventDto) convertObject.convert(event, EventDto.class));
            String file = "";
            String baseEncode = null;
            byte[] encoded = Base64Utils.encode(event.getEventImage());
            try {
                baseEncode = new String(encoded, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            file = "data:image/jpg;base64," + baseEncode;
            eventDto.setEventImage(file);
            eventDto.setEnrolledSeats(this.getParticipantsCountOfEvent(event.getEventId()));
            eventDto.setOwnerUsername(enroll.getUserDetailsEntity().getEmail());
            eventDto.setCategoryDto(new CategoryDto(event.getCategory().getCategoryName()));
//            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println(event.getCategory().getCategoryName());
            eventDtos.add(eventDto);
        });
        return eventDtos;
    }

    @Override
    public void cancelEnrollment(String token, UserEventQueryParamsDto userEventQueryParamsDto) throws RecordNotFoundException {
        persist(token,userEventQueryParamsDto,"CANCEL");
    }

    @Override
    public byte[] createExcel() {
        return new byte[0];
    }

    private void persist(String token, UserEventQueryParamsDto userEventQueryParamsDto,
                         String status)throws RecordNotFoundException {
        Optional<Event> optEvent = eventRepository.findById(userEventQueryParamsDto.getEventId());
        if(optEvent.isPresent()) {
            UserEnrollmentEntity userEnrollmentEntity = new UserEnrollmentEntity();
            Event event = optEvent.get();
            UserDetailsEntity userEntity = (UserDetailsEntity) convertObject
                    .convert(userFeign.getUser(token,userEventQueryParamsDto.getUsername() ), UserDetailsEntity.class);
            Optional<UserEnrollmentEntity> optUserEnrollmentEntity = userEnrollmentRepository
                    .findByUserDetailsEntityAndEvent(userEntity,event);
            if(optUserEnrollmentEntity.isPresent()) {
                userEnrollmentEntity= optUserEnrollmentEntity.get();
                if(StringUtils.equals(status,"ENROLLED") && StringUtils.equals(userEnrollmentEntity.getStatus(),"ENROLLED")) {
                    throw new AlreadyExistsException("RST-00110");
                }
            }
            userEnrollmentEntity.setEvent(event);
            userEnrollmentEntity.setUserDetailsEntity(userEntity);
            userEnrollmentEntity.setStatus(status);
            userEnrollmentEntity.setRegistrationDate(new Date());
            userEnrollmentRepository.save(userEnrollmentEntity);
        } else {
            throw new RecordNotFoundException("Event Id "+ String.valueOf(userEventQueryParamsDto.getEventId())+" not found");
        }
    }

    @Override
    public List<DownloadEventDetailsDto> getDetailsForEnrollmentContent(Long eventId) {
        Optional<Event> optEvent = eventRepository.findById(eventId);
        if(optEvent.isPresent()) {
            return userEnrollmentRepository.findByEvent(optEvent.get())
                    .stream()
                    .map( entity -> {
                        DownloadEventDetailsDto downloadEventDetailsDto = new DownloadEventDetailsDto();
                        downloadEventDetailsDto.setEmail(entity.getUserDetailsEntity().getEmail());
                        downloadEventDetailsDto.setName(entity.getUserDetailsEntity().getName());
                        return downloadEventDetailsDto;
                    }).collect(Collectors.toList());
        } else {
            throw new RecordNotFoundException("Event Id "+ String.valueOf(eventId)+" not found");
        }
    }


}
