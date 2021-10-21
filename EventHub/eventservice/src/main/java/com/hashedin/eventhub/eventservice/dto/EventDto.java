package com.hashedin.eventhub.eventservice.dto;

import com.hashedin.eventhub.eventservice.entity.UserDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventDto {

    private Long eventId;
    private Date eventCreationDate;
    private String eventName;
    private Date eventDate;
    private String eventStartTime;
    private String eventEndTime;
    private String eventImage;
    private String eventDesc;
    private String eventRules;
    private String zoomlink;
    private Integer enrollSeats;
    private CategoryDto categoryDto;
    private String ownerUsername;
    private Integer enrolledSeats;

}

//{
//        "eventId" : "400",
//        "eventName" : "nokia",
//        "eventDate" : "2021-11-01",
//        "eventImageUrl" : "url for event",
//        "eventDesc" : "Desc",
//        "eventRules" : "rules",
//        "categoryDto": {
//          "categoryName":"Art"
//         }
//}
