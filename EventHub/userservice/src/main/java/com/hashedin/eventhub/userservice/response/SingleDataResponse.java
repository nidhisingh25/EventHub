package com.hashedin.eventhub.userservice.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SingleDataResponse extends BaseResponse {
    private Object data;

    public SingleDataResponse(Object data, LocalDateTime timestamp, int status,
                              String message, String path) {
        super(timestamp,status,message,path);
        this.data = data;
    }
}
