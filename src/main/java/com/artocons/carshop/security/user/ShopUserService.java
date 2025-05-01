package com.artocons.carshop.security.user;

import com.artocons.carshop.persistence.model.User;
import com.artocons.carshop.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public ShopUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return ShopUser.buildUserDetails(user);
    }

}