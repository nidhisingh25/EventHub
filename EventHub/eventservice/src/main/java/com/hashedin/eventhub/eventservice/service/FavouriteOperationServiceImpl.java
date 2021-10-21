package com.hashedin.eventhub.eventservice.service;

import com.hashedin.eventhub.eventservice.dto.CategoryDto;
import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.dto.UserEventQueryParamsDto;
import com.hashedin.eventhub.eventservice.dto.UserFavouriteEventDto;
import com.hashedin.eventhub.eventservice.entity.Event;
import com.hashedin.eventhub.eventservice.entity.UserDetailsEntity;
import com.hashedin.eventhub.eventservice.entity.UserFavoriteEventEntity;
import com.hashedin.eventhub.eventservice.exception.AlreadyExistsException;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import com.hashedin.eventhub.eventservice.feign.UserFeign;
import com.hashedin.eventhub.eventservice.repository.EventRepository;
import com.hashedin.eventhub.eventservice.repository.FavouriteOperationRepository;
import com.hashedin.eventhub.eventservice.utils.ObjectConversion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteOperationServiceImpl implements FavouriteOperationService {

    @Autowired
    private FavouriteOperationRepository favouriteOperationRepository;

    @Autowired
    private ObjectConversion convertObject;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private EnrollmentOperationService enrollmentOperationService;

    @Override
    public void addToFavourite(@RequestHeader("Authorization") String token,
                               UserEventQueryParamsDto userEventQueryParamsDto) {
        persist(token,userEventQueryParamsDto, "FAVOURITE");
    }

    @Override
    public void removeFromFavourite(@RequestHeader("Authorization") String token,
                                    UserEventQueryParamsDto userEventQueryParamsDto) {
        persist(token,userEventQueryParamsDto, "CANCEL");
    }

    @Override
    @Transactional
    public List<EventDto> getFavouriteEvents(@RequestHeader("Authorization") String token,
                                             String username) {
        List<EventDto> eventDtos = new ArrayList<>();
         favouriteOperationRepository.findByUserDetailsEntityAndStatus((UserDetailsEntity) convertObject
                .convert(userFeign.getUser(token,username), UserDetailsEntity.class),"FAVOURITE")
                .forEach(fav -> {
                    Event event = fav.getEvent();
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
                    eventDto.setCategoryDto((CategoryDto) convertObject.convert(event.getCategory(), CategoryDto.class));
                    eventDto.setEventImage(file);
                    eventDto.setEnrolledSeats(this.enrollmentOperationService.getParticipantsCountOfEvent(event.getEventId()));
                    eventDto.setOwnerUsername(fav.getUserDetailsEntity().getEmail());
                    eventDtos.add(eventDto);
                });
//        List<CategoryDto> categoryDtos= events.stream().map(c -> (CategoryDto) convertObject.convert(c, CategoryDto.class)).collect(Collectors.toList());
//        for(int i=0; i<eventDtos.size(); i++){
//            eventDtos.get(i).setCategoryDto(categoryDtos.get(i));
//        }
         return eventDtos;
    }

    private void persist(String token, UserEventQueryParamsDto userEventQueryParamsDto,
                         String status) throws RecordNotFoundException {
        Optional<Event> optEvent = eventRepository.findById(userEventQueryParamsDto.getEventId());
        if(optEvent.isPresent()) {
            UserFavoriteEventEntity userFavoriteEventEntity = new UserFavoriteEventEntity();
            Event event = optEvent.get();
            UserDetailsEntity userEntity = (UserDetailsEntity) convertObject
                    .convert(userFeign.getUser(token,userEventQueryParamsDto.getUsername() ), UserDetailsEntity.class);

            Optional<UserFavoriteEventEntity> optUserFavoriteEventEntity =
                    favouriteOperationRepository.findByUserDetailsEntityAndEvent(userEntity,event);
            if(optUserFavoriteEventEntity.isPresent()) {
                userFavoriteEventEntity = optUserFavoriteEventEntity.get();
                if(StringUtils.equals(status,"FAVOURITE") && StringUtils.equals(userFavoriteEventEntity.getStatus(),"FAVOURITE")) {
                    throw new AlreadyExistsException("RST-00109");
                }
            }
            userFavoriteEventEntity.setEvent(event);
            userFavoriteEventEntity.setUserDetailsEntity(userEntity);
            userFavoriteEventEntity.setStatus(status);
            favouriteOperationRepository.save(userFavoriteEventEntity);
        } else {
            throw new RecordNotFoundException("Event Id "+ String.valueOf(userEventQueryParamsDto.getEventId())+" not found");
        }
    }
}
