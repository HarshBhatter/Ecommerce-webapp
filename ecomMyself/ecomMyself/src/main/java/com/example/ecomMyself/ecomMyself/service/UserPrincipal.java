package com.example.ecomMyself.ecomMyself.service;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
//to add extra features to UserDetails
public class UserPrincipal implements UserDetails {
    private Users user;
    private int version;
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
        setVersion(user.getVersion());
    }
    public UserPrincipal(Users user)
    {
        this.user=user;
        setVersion(user.getVersion());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
