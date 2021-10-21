package com.hashedin.eventhub.eventservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DownloadEventDetailsDto {

    private String name;
    private String email;
}
