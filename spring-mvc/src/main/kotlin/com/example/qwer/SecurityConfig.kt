package com.example.qwer

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(private val dataSource: DataSource) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication().dataSource(dataSource)
            .authoritiesByUsernameQuery("select login, password from Users where login=?")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/api/**").hasAnyRole("ADMIN", "API")
            .antMatchers("/login").anonymous()
            .and().authorizeRequests().anyRequest().authenticated()
            .and().formLogin().loginPage("/login")
            .usernameParameter("login").passwordParameter("password")
            .defaultSuccessUrl("/app/list")
            .and()
            .csrf().ignoringAntMatchers("/api/**")
    }
}