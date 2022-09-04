package com.tcc.simpledocapi.config.security;

import com.tcc.simpledocapi.filter.CustomAuthenticationFilter;
import com.tcc.simpledocapi.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http
            .csrf()
                .disable()
            .sessionManagement().sessionCreationPolicy(STATELESS)
            .and()
                    .cors().and()
                        .authorizeRequests()
                            .antMatchers( "/api/login/**", "/api/v1/user/register/**").permitAll()
                            .and().authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER")
                .and().logout()
                .logoutUrl("/me/logout")
                .invalidateHttpSession(true).permitAll();
               /* .logout(logout -> logout.logoutUrl("/me/logout").addLogoutHandler(((request, response, authentication) -> {
                    try {
                        request.logout();
                    } catch (ServletException e) {

                    }
                })));*/
                /*.and().logout()
                        .logoutUrl("/me/logout")
                        .invalidateHttpSession(true).permitAll();*/

        http.authorizeRequests().antMatchers(POST, "/api/v1/team/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/v1/team/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/***").hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
       web.ignoring().antMatchers("/ws/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
                "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
                "x-auth-token", "access-control-allow-origin"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
