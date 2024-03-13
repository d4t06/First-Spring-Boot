package com.example.demo.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.user.converter.UserDtoToUser;
import com.example.demo.user.dto.MyUserPrincipal;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;

@Service
// implement UserDetailService is default server of spring boot security
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDtoToUser userDtoToUser;
    private final PasswordEncoder passwordEncoder;

    @Override
    // override method loadUserByUsername and return myUserPrincipal (implement UserDetails)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(">>> run loadUserByUsername and return UserPrincipal");

        UserDetails userDetails = this.userRepository.findByUsername(username)
                .map(user -> new MyUserPrincipal(user))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        System.out.println(">>> check user detail: " + userDetails);

        return userDetails;
    }

    public UserService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            UserDtoToUser userDtoToUser) {

        this.userRepository = userRepository;
        this.userDtoToUser = userDtoToUser;
        this.passwordEncoder = passwordEncoder;
    }

    public User findOne(String username) {
        Optional<User> users = this.userRepository.findByUsername(username);
        if (users.isPresent())
            return users.get();

        return null;
    }

    public User create(UserDto userDto) {
        User user = this.userDtoToUser.convert(userDto);
        user.setPassword(this.passwordEncoder.encode(userDto.password()));

        return this.userRepository.save(user);
    }

}
