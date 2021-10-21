package com.hashedin.eventhub.eventservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="USER_FAVOURITE_EVENT")
public class UserFavoriteEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private UserDetailsEntity userDetailsEntity;

    @OneToOne
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
