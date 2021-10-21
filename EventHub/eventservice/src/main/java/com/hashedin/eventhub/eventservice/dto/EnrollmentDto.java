package com.hashedin.eventhub.eventservice.dto;

import com.hashedin.eventhub.eventservice.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnrollmentDto {

    private Long id;

    private UserDetailsDto userDetailsEntity;

    private EventDto event;

    private Date registrationDate;

    private String status;

}
