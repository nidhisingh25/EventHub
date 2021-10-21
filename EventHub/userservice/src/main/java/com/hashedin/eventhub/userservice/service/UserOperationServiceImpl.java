package com.hashedin.eventhub.userservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.eventhub.userservice.dto.UserDetailsDto;
import com.hashedin.eventhub.userservice.entity.UserDetailsEntity;
import com.hashedin.eventhub.userservice.exception.ApplicationRuntimeException;
import com.hashedin.eventhub.userservice.repository.UserRepository;
import com.hashedin.eventhub.userservice.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserOperationServiceImpl implements UserOperationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(UserDetailsDto userDetailsDto) {
        if(!AppUtils.isValidEmail(userDetailsDto.getEmail())) {
            throw new ApplicationRuntimeException("RST-00105");
        }
        if(userRepository.findByEmail(userDetailsDto.getEmail()).isPresent()) {
            throw new ApplicationRuntimeException("RST-00108");
        }
        ObjectMapper mapper = new ObjectMapper();
        UserDetailsEntity userDetailsEntity = mapper.convertValue(userDetailsDto, UserDetailsEntity.class);
        userDetailsEntity.setPassword(passwordEncoder.encode(userDetailsDto.getPassword()));
        userRepository.save(userDetailsEntity);
    }

    @Override
    public UserDetailsDto getUser(String email) {
        Optional<UserDetailsEntity> optionalUserEntity = userRepository.findByEmail(email);
        if(!optionalUserEntity.isPresent()) {
            throw new ApplicationRuntimeException("RST-00106");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(optionalUserEntity.get(), UserDetailsDto.class);
    }

    @Override
    public UserDetailsDto getUser(Long userId) {
        Optional<UserDetailsEntity> optionalUserEntity = userRepository.findById(userId);
        if(!optionalUserEntity.isPresent()) {
            throw new ApplicationRuntimeException("RST-00107");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(optionalUserEntity.get(), UserDetailsDto.class);
    }

    @Override
    public List<UserDetailsDto> getUsers(List<Long> userIds) {
        List<UserDetailsEntity> userEntityList = (List<UserDetailsEntity>) userRepository.findAllById(userIds);
        ObjectMapper mapper = new ObjectMapper();
        return userEntityList.stream()
                .map(entity -> mapper.convertValue(entity, UserDetailsDto.class))
                .collect(Collectors.toList());
    }
}
