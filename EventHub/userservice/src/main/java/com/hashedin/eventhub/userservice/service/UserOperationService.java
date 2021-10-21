package com.hashedin.eventhub.userservice.service;

import com.hashedin.eventhub.userservice.dto.UserDetailsDto;
import com.hashedin.eventhub.userservice.entity.UserDetailsEntity;

import java.util.List;

public interface UserOperationService {

    /**
     * This api will persist an UserDetailsDto in the database.
     *
     * @param userDetailsDto
     */
    public void addUser(UserDetailsDto userDetailsDto);

    /**
     *This api will return an UserEntity for the given email
     *
     * @param email
     * @return UserDetailsDto
     */
    public UserDetailsDto getUser(String email);

    /**
     * This api will return an UserEntity for the given userId
     *
     * @param userId
     * @return UserDetailsDto
     */
    public UserDetailsDto getUser(Long userId);

    /**
     * This api will return a List of UserEntity for the given list of userId
     *
     * @param userIds
     * @return UserDetailsDto
     */
    public List<UserDetailsDto> getUsers(List<Long> userIds);

}
