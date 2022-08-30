package nl.robbertij.matchnmusic.config;

import nl.robbertij.matchnmusic.filter.JwtRequestFilter;
import nl.robbertij.matchnmusic.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.http.HttpMethod.PATCH;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/authenticate").permitAll()
                    .antMatchers(PATCH,"/users/{^[\\w]$}/password").authenticated()
                    .antMatchers(HttpMethod.POST,"/students/**").hasAnyRole("STUDENT", "ADMIN")
                    .antMatchers(HttpMethod.GET,"/students/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/students/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/teachers/**").hasAnyRole("TEACHER", "ADMIN")
                    .antMatchers(HttpMethod.GET,"/teachers/**").hasAnyRole("TEACHER", "STUDENT", "ADMIN")
                    .antMatchers(HttpMethod.DELETE, "teachers/**").permitAll()
                    .antMatchers("/authenticated").authenticated()
                .anyRequest().permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
