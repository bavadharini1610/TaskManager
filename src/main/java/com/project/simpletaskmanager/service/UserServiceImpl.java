package com.project.simpletaskmanager.service;

import com.project.simpletaskmanager.entity.User;
import com.project.simpletaskmanager.entity.UserPrincipal;
import com.project.simpletaskmanager.exception.UserNotFoundException;
//import com.project.simpletaskmanager.jwt.JwtUtils;
import com.project.simpletaskmanager.exception.UsernameAlreadyExistException;
import com.project.simpletaskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder(12);


    @Override
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException("Username already exist");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id){
       User user = userRepository.getById(id);
       if(user==null){
           throw new UserNotFoundException("User not found with id: "+id);
       }
       return user;}

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found with email: "+email);
        }
        return user.orElse(null);
    }

    @Override
    public String verify(User user, AuthenticationManager authenticationManager) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        }
        return "Failure";
    }


//    @Override
//    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
//        return null;
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = optionalUser.get(); // Extract the User object from Optional
        return new UserPrincipal(user);
    }

}



