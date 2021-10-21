package com.hashedin.eventhub.eventservice.service;

import com.hashedin.eventhub.eventservice.dto.EventDto;
import com.hashedin.eventhub.eventservice.dto.UserEventQueryParamsDto;
import com.hashedin.eventhub.eventservice.dto.UserFavouriteEventDto;
import com.hashedin.eventhub.eventservice.exception.RecordNotFoundException;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface FavouriteOperationService {

    void addToFavourite(@RequestHeader("Authorization") String token,
                        UserEventQueryParamsDto userEventQueryParamsDto);

    void removeFromFavourite(@RequestHeader("Authorization") String token,
                             UserEventQueryParamsDto userEventQueryParamsDto);

    List<EventDto> getFavouriteEvents(@RequestHeader("Authorization") String token,
                                      String username);
}
