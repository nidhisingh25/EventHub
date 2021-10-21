package com.hashedin.eventhub.eventservice.dto;

import com.hashedin.eventhub.eventservice.entity.Event;
import com.hashedin.eventhub.eventservice.entity.UserDetailsEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserFavouriteEventDto {

    private Long id;

    private UserDetailsDto userDetailsEntity;

    private EventDto event;

    private String status;
}
