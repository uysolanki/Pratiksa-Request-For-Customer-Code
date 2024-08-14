package com.excelr.cms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyWebSecurity 
{
	
//@Override
//protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////	auth.inMemoryAuthentication()
////	.withUser("excelr")
////	.password("123456")
////	.authorities("ADMIN")
////	.and()
////	.withUser("edtech")
////	.password("654321")
////	.authorities("USER");
//	
//	auth.authenticationProvider(myAuthenticationProvider());
//}

	@Bean
	public AuthenticationProvider myAuthenticationProvider() {
	DaoAuthenticationProvider daoProvider=new DaoAuthenticationProvider();
	daoProvider.setUserDetailsService(myUserDetailsServicee());
	daoProvider.setPasswordEncoder(myPasswordEncoder());
	return daoProvider;
}

	@Bean
	public PasswordEncoder myPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService myUserDetailsServicee() {
			return new MyUserDetailsService();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception 
//	{
////		http.authorizeRequests()
////        .anyRequest().authenticated()
////        .and()        
////        .formLogin().loginProcessingUrl("/login").successForwardUrl("/").permitAll()
////        .and()
////        .cors().and().csrf().disable();
//		
//		http.authorizeRequests()
//        .requestMatchers("/","/registerCustomer","/403").hasAnyAuthority("USER","ADMIN")
//        .requestMatchers("/deletecustomer/**","/updatecustomerform/**").hasAuthority("ADMIN")
//        .anyRequest().authenticated()
//        .and()
//        .formLogin().loginProcessingUrl("/login").successForwardUrl("/").permitAll()
//        .and()
//        .logout().logoutSuccessUrl("/login").permitAll()
//        .and()
//        .exceptionHandling().accessDeniedPage("/403")
//        .and()
//        .cors().and().csrf().disable();
//
//
//	}
	
//	@Bean
//	public PasswordEncoder getPasswordEncoder()
//	{
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
        .requestMatchers("/","/registerCustomer","/403").hasAnyAuthority("USER","ADMIN")
        .requestMatchers("/deletecustomer/**","/updatecustomerform/**","/registerUser").hasAuthority("ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin().loginProcessingUrl("/login").successForwardUrl("/").permitAll()
        .and()
        .logout().logoutSuccessUrl("/login").permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/403")
        .and()
        .cors().and().csrf().disable();	
		
		http.authenticationProvider(myAuthenticationProvider());
		return http.build();
	}
	

}
