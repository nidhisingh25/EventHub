package com.hashedin.eventhub.userservice.service;


import com.hashedin.eventhub.userservice.entity.UserDetailsEntity;
import com.hashedin.eventhub.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserCredentialService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserDetailsEntity> optUserEntity = userRepo.findByEmail(username);
        if (optUserEntity.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(username, optUserEntity.get().getPassword(), new ArrayList<>());
    }

}
