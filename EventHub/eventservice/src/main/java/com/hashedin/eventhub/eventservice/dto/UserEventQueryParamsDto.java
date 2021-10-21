package com.hashedin.eventhub.eventservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserEventQueryParamsDto {

    private String username;

    private Long eventId;
}
