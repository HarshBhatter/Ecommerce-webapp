package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//to return userDetail wrapped in UserPrincipal as
@Service
public class MyUserDetailService implements UserDetailsService{
    @Autowired
    private User_Repo user_repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(user_repo.existsByUsername(username)) {
            return new UserPrincipal(user_repo.findByUsername(username));
        }
        throw new UsernameNotFoundException("User 404");
    }
}
