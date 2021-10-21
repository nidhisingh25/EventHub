package com.hashedin.eventhub.eventservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="USER_ENROLLMENT_ENTITY")
public class UserEnrollmentEntity {

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

    @Column(name = "REGISTRATION_DATE", nullable = false)
    private Date registrationDate;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
