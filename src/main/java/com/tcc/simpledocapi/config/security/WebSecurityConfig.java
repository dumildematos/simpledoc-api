package com.tcc.simpledocapi.config.security;

import com.tcc.simpledocapi.config.security.oauth.CustomOAuth2UserService;
import com.tcc.simpledocapi.config.security.oauth.Oauth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2UserService oAuthUserService;
    @Autowired
    private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().antMatchers("/api/v1/**").permitAll();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/oauth2/**", "/registration/**").permitAll()
                .antMatchers("/api/v1/**").authenticated()
                .anyRequest().permitAll()
                .and().formLogin().permitAll()
                .and().oauth2Login()
                    .userInfoEndpoint().userService(oAuthUserService)
                    .and()
                    .successHandler(oauth2LoginSuccessHandler)
                .and().logout().permitAll();

        //http.authorizeRequests().anyRequest().authenticated().and().oauth2Login();
        //http.authorizeRequests().anyRequest().permitAll();
        //http.authorizeRequests().antMatchers("/api/v1/oauth").authenticated().and().oauth2Login();
        //http.authorizeRequests().antMatchers("/api/v1/oauth").authenticated().and().oauth2Login();
        /*http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/api/v1/user/").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .permitAll()
                .and()
                .oauth2Login()
                    .userInfoEndpoint().userService(oAuthUserService)
                    .and()
                .and()
                .logout().permitAll();*/

    }


}
