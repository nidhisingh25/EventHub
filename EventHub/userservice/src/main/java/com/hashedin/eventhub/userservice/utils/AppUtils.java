package com.hashedin.eventhub.userservice.utils;

import com.hashedin.eventhub.userservice.response.SingleDataResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static com.hashedin.eventhub.userservice.constants.GlobalConstants.*;

/**
 * Keep all utility methods in this class
 */
public class AppUtils {

    //Constructor made private to prevent developer from instantiate utility class
    private AppUtils() {

    }

    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_VERIFICATION_STRING).matcher(email).matches();
    }

    public static SingleDataResponse prepareSingleDataResponse(Object data, HttpStatus httpStatus, String message, String requestURI) {
        SingleDataResponse response = new SingleDataResponse(data, LocalDateTime.now(), httpStatus.value(),
                message, requestURI);
        return  response;
    }
}
