package com.hashedin.eventhub.eventservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.eventhub.eventservice.dto.CategoryDto;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.dto.UserDetailsDto;
import com.hashedin.eventhub.eventservice.entity.Category;
import com.hashedin.eventhub.eventservice.entity.Event;
import com.hashedin.eventhub.eventservice.entity.UserDetailsEntity;
import com.hashedin.eventhub.eventservice.exception.FileStorageException;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.feign.UserFeign;
import com.hashedin.eventhub.eventservice.repository.EventRepository;
import com.hashedin.eventhub.eventservice.utils.ObjectConversion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventOperationServiceImpl implements EventOperationService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectConversion convertObject;

    @Autowired
    private CategoryOperationService categoryOperationService;

    @Autowired
    private EnrollmentOperationService enrollmentOperationService;

    @Autowired
    private UserFeign userFeign;


    @Override
    @Transactional
    public Long addEvent(@RequestHeader("Authorization") String token, EventDto eventDto) {
        Event event = (Event) convertObject.convert(eventDto, Event.class);
        Category category = (Category) convertObject.convert(eventDto.getCategoryDto(), Category.class);
        UserDetailsDto user = userFeign.getUser(token,eventDto.getOwnerUsername());
        event.setOwner((UserDetailsEntity) convertObject.convert(user,UserDetailsEntity.class));
        event.setCategory(category);
        eventRepository.save(event);
        return event.getEventId();
    }

    @Override
    public EventDto getEventById(Long eventId) throws RecordNotFoundException {
        Optional<Event> event = eventRepository.findById(eventId);
        if(event.isPresent()) {
            CategoryDto categoryDto = (CategoryDto) convertObject.convert(event.get().getCategory(),CategoryDto.class);

            EventDto eventDto = ((EventDto) convertObject.convert(event.get(), EventDto.class));

            String file = "";
            String baseEncode = null;
            byte[] encoded = Base64Utils.encode(event.get().getEventImage());
            try {
                baseEncode = new String(encoded, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            file = "data:image/jpg;base64," + baseEncode;
            eventDto.setEventImage(file);
            eventDto.setOwnerUsername(event.get().getOwner().getEmail());
//            EventDto eventDto = (EventDto) convertObject.convert(event.get(), EventDto.class);
            eventDto.setCategoryDto(categoryDto);
            eventDto.setEnrolledSeats(this.enrollmentOperationService.getParticipantsCountOfEvent(eventId));
            return eventDto;
        }
        else
            throw new RecordNotFoundException("Event Id "+eventId+" not found");
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();
        events.forEach(event -> {
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
            System.out.println(event.getOwner());
            eventDto.setOwnerUsername(event.getOwner().getEmail());
            eventDto.setEnrolledSeats(this.enrollmentOperationService.getParticipantsCountOfEvent(event.getEventId()));
            eventDtos.add(eventDto);
        });
        List<CategoryDto> categoryDtos= events.stream().map(c -> (CategoryDto) convertObject.convert(c, CategoryDto.class)).collect(Collectors.toList());
        for(int i=0; i<eventDtos.size(); i++){
            eventDtos.get(i).setCategoryDto(categoryDtos.get(i));
        }

        Comparator<EventDto> reverseComparator = (c1, c2) -> {
            return c2.getEventDate().compareTo(c1.getEventDate());
        };
        Collections.sort(eventDtos, reverseComparator);

        return eventDtos;
    }

    @Override
    @Transactional
    public List<EventDto> getEventByCategory(String category) throws RecordNotFoundException {
        List<Event> events = eventRepository.findByCategory(category);
        List<EventDto> eventDtos = new ArrayList<>();
        events.forEach(event -> {
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
            eventDto.setOwnerUsername(event.getOwner().getEmail());
            eventDto.setEnrolledSeats(this.enrollmentOperationService.getParticipantsCountOfEvent(event.getEventId()));
            eventDtos.add(eventDto);
        });
        List<CategoryDto> categoryDtos= events.stream().map(c -> (CategoryDto) convertObject.convert(c, CategoryDto.class)).collect(Collectors.toList());
        for(int i=0; i<eventDtos.size(); i++){
            eventDtos.get(i).setCategoryDto(categoryDtos.get(i));
        }
        return eventDtos;
    }


    @Override
    public List<EventDto> getUpcomingEvents() {
        List<EventDto> eventDto =  this.getAllEvents().stream()
                .filter(e -> new java.util.Date().compareTo(e.getEventDate()) < 0  )
                .collect(Collectors.toList());
        Collections.reverse(eventDto);
        return eventDto;
    }

    @Override
    public List<EventDto> getPopularEvents() {
        List<EventDto> eventDtos = this.getUpcomingEvents();
        List<Integer> participants = new ArrayList<>();
        eventDtos.forEach( e -> participants.add(enrollmentOperationService.getParticipantsCountOfEvent(e.getEventId())));
        int key;
        EventDto eventKey;
        int j;
        for (int i = 1; i < participants.size(); i++) {
            key = participants.get(i);
            eventKey = eventDtos.get(i);
            j = i - 1;

            while (j >= 0 && participants.get(j) < key) {
                participants.set(j + 1, participants.get(j));
                eventDtos.set(j + 1, eventDtos.get(j));
                j = j - 1;
            }
            participants.set(j + 1, key);
            eventDtos.set(j + 1, eventKey);
        }
        return eventDtos;
    }

    @Override
    public String updateEventImage(MultipartFile eventPic, Long id) throws FileStorageException {
        String fileName = StringUtils.cleanPath(eventPic.getOriginalFilename());

        System.out.println("file name"+fileName);
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Event res = null;
            if (this.eventRepository.existsById(id)) {
                Event updated_res = (Event) this.eventRepository.findById(id).get();
                updated_res.setEventImage(eventPic.getBytes());
                System.out.println("event pic"+updated_res.getEventImage());
                res = this.eventRepository.save(updated_res);

                return "Successfully Added";
            }
            return "Successfully Added";
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file. Please try again!", ex);
        }
    }

    @Override
    @Transactional
    public List<EventDto> eventsCreatedByUser(@RequestHeader("Authorization") String token,
                                              String username) {
        UserDetailsDto user = userFeign.getUser(token,username);

        List<Event> events = eventRepository.getAllEventsCreatedByUser(username);
        List<EventDto> eventDtos = new ArrayList<>();

        events.forEach(event -> {
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
            eventDto.setEnrolledSeats(this.enrollmentOperationService.getParticipantsCountOfEvent(event.getEventId()));
            eventDto.setOwnerUsername(user.getEmail());
            eventDtos.add(eventDto);
        });
        List<CategoryDto> categoryDtos= events.stream().map(c -> (CategoryDto) convertObject.convert(c, CategoryDto.class)).collect(Collectors.toList());
        for(int i=0; i<eventDtos.size(); i++){
            eventDtos.get(i).setCategoryDto(categoryDtos.get(i));
        }
        return eventDtos;
    }

    @Override
    public Long updateEvent(String token, EventDto eventDto) {
        Event event = (Event) convertObject.convert(eventDto, Event.class);
        Category category = (Category) convertObject.convert(eventDto.getCategoryDto(), Category.class);
        UserDetailsDto user = userFeign.getUser(token,eventDto.getOwnerUsername());
        event.setOwner((UserDetailsEntity) convertObject.convert(user,UserDetailsEntity.class));
        event.setCategory(category);
        eventRepository.save(event);
        return event.getEventId();
    }

}
