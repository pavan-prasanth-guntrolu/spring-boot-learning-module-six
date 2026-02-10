package com.learning.module_6.services;


import com.learning.module_6.dto.SignupDTO;
import com.learning.module_6.dto.UserDTO;
import com.learning.module_6.entities.UserEntity;
import com.learning.module_6.exceptions.ResourceNotFoundException;
import com.learning.module_6.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new BadCredentialsException("User with email"+username+"Not found"));
    }

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with email"+ userId +"Not found"));
    }

    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDTO signup(SignupDTO signupDTO) {
        Optional<UserEntity> userEntity=userRepository.findByEmail(signupDTO.getEmail());
        if(userEntity.isPresent()){
            throw new BadCredentialsException("User with email already exists"+signupDTO.getEmail());
        }

        UserEntity toBeCreatedUser= modelMapper.map(signupDTO,UserEntity.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        UserEntity savedUser=userRepository.save(toBeCreatedUser);

        return modelMapper.map(savedUser,UserDTO.class);
    }


    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}

