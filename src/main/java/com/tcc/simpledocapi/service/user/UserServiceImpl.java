package com.tcc.simpledocapi.service.user;


import com.tcc.simpledocapi.entity.ConfirmationToken;
import com.tcc.simpledocapi.entity.Role;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.repository.ConfirmationTokenRepository;
import com.tcc.simpledocapi.repository.RoleRepository;
import com.tcc.simpledocapi.repository.UserRepository;
import com.tcc.simpledocapi.service.email.EmailDetails;
import com.tcc.simpledocapi.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private  final ConfirmationTokenRepository confirmationTokenRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Override
    public User saveUser(User user, String roleName) {


        /*user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("\uD83D\uDE0C",encodedPassword);
        log.info(" \uD83D\uDE42",passwordEncoder.encode(user.getPassword()));*/
        user.setPassword(passwordEncoder.encode((user.getPassword())));

        Role role = roleRepository.findByName(roleName);
        user.getRoles().clear();
        user.getRoles().add(role);

        Optional<User> gUser = Optional.of(userRepository.save(user));



            ConfirmationToken confirmationToken = new ConfirmationToken(
                    null,
                    UUID.randomUUID().toString(),
                    new Date(),
                    gUser.get()
            );

            EmailDetails mailMessage = new EmailDetails(
                    gUser.get().getUsername(),
                    "To confirm your account, please click here : "
                            +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken(),
                    "Complete Registration!",
                    null);
            confirmationTokenRepository.save(confirmationToken);


            emailService.sendSimpleMail(mailMessage);



        return gUser.get();
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> getUsers(int offset, int size) {
        return userRepository.findAll(PageRequest.of(offset, size));
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = userRepository.findByUsername(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        log.info(String.valueOf(user.getRoles()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User {} found in the database", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
