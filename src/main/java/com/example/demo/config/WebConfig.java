package com.example.demo.config;

import com.example.demo.security.CustomAuthenticationFailureHandler;
import com.example.demo.security.JWTAuthenticationFilter;
import com.example.demo.security.JWTAuthorizationFilter;
import com.example.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.example.demo.security.SecurityConstants.LOGIN_URL;
import static com.example.demo.security.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
    @Bean
    public DefaultWebSecurityExpressionHandler hierarchyWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                //.antMatchers("/**").permitAll().and().headers().frameOptions().disable();;
                .expressionHandler(hierarchyWebSecurityExpressionHandler())
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                //.antMatchers("/api/user/create").hasAuthority("appUser_WRITE")
                .antMatchers("/api/user/read/**").hasAuthority("appUser_READ")
                .antMatchers("/api/user/delete/**").hasAuthority("appUser_DELETE")
                .antMatchers("/api/user/update/**").hasAuthority("appUser_DELETE")
                .antMatchers("/api/roles/create").hasAuthority("appRoles_WRITE")
                .antMatchers("/api/roles/read/**").hasAuthority("appRoles_READ")
                .antMatchers("/api/roles/delete/**").hasAuthority("appRoles_DELETE")
                .antMatchers("/api/roles/update/**").hasAuthority("appRoles_DELETE")
                .antMatchers("/api/privileges/create").hasAuthority("appPrivileges_WRITE")
                .antMatchers("/api/privileges/read/**").hasAuthority("appPrivileges_READ")
                .antMatchers("/api/privileges/delete/**").hasAuthority("appPrivileges_DELETE")
                .antMatchers("/api/privileges/update/**").hasAuthority("appPrivileges_DELETE")
                //.antMatchers("/api/roles/**").hasRole("ADMIN")
                //.antMatchers("/api/privileges/**").hasRole("ADMIN")
                .antMatchers("/built/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/product").permitAll()
                .antMatchers("/admin").permitAll()
                .antMatchers("/products").permitAll()
                .antMatchers("/product/api/read/**").hasAuthority("appProd_READ")
                .antMatchers("/product/api/update/**").hasAnyAuthority("appProd_DELETE", "appProd_WRITE")
                .antMatchers("/product/api/delete/**").hasAuthority("appProd_DELETE")
                .antMatchers("/product/api/create").hasAuthority("appProd_WRITE")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl(LOGIN_URL)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userDetailsService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }


}
