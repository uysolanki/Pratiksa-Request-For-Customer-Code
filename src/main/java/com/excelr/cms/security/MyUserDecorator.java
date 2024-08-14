package com.excelr.cms.security;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.excelr.cms.entity.Role;
import com.excelr.cms.entity.User;


public class MyUserDecorator implements UserDetails {

	User user;
	
	public MyUserDecorator(User user)
	{
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;

	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		LocalDate accExpiryDate=user.getAccountExpiryDate();
		LocalDate todaysDate=LocalDate.now();
		if(accExpiryDate.isAfter(todaysDate))
		return true;
		else
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		int accountLockedStatus=user.getAccountLockedStatus();
		if(accountLockedStatus==1)
		return true;
		else
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		LocalDate credExpiryDate=user.getCredentialtExpiryDate();
		LocalDate todaysDate=LocalDate.now();
		if(credExpiryDate.isAfter(todaysDate))
		return true;
		else
		return false;
	}

	@Override
	public boolean isEnabled() {
		int accountEnabledStatus=user.getAccountEnabledStatus();
		if(accountEnabledStatus==1)
		return true;
		else
		return false;
	}

}
