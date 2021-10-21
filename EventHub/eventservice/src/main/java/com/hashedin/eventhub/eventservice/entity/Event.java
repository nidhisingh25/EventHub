package com.hashedin.eventhub.eventservice.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="eventId")
    private Long eventId;

    @Column(name="eventName", nullable = false)
    private String eventName;

    @Column(name="eventDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eventDate;

    @Lob
//    @Type(type = "text")
    @Column(name="eventImage")
    private byte[] eventImage;

    @Column(name="eventDesc", length = 5000)
    private String eventDesc;

    @Column(name="eventRules", length = 5000)
    private String eventRules;

    @Column(name="enrollSeats")
    private Integer enrollSeats;

    @Column(name="eventCreationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eventCreationDate;

    @Column(name="eventStartTime")
    private String eventStartTime;

    @Column(name="eventEndTime")
    private String eventEndTime;

    @Column(name = "zoomlink")
    private String zoomlink;

    @ManyToOne
    @JoinColumn(name="category")
    private Category category;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private UserDetailsEntity owner;

}
