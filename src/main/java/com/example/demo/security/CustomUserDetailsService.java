package com.example.demo.security;

import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GuestRepository guestRepository;

    public CustomUserDetailsService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Guest guest = guestRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(email));

        return new CustomUserDetails(
                guest.getId(),
                guest.getEmail(),
                guest.getPassword(),
                guest.getRole(),
                List.of(new SimpleGrantedAuthority(guest.getRole()))
        );
    }
}
