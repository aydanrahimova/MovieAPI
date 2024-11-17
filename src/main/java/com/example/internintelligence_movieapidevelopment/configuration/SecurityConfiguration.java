//package com.example.internintelligence_movieapidevelopment.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(request->request.anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());
////                .authorizeHttpRequests(
////                        authorize -> authorize
////                                .requestMatchers(permitAllUrls).permitAll()
////                                .requestMatchers(adminUrls).hasAnyAuthority("ADMIN")
////                                .requestMatchers(userUrls).hasAnyAuthority("USER") // Restrict /user/** to USER role
////                                .requestMatchers(adminAndUserUrls).hasAnyAuthority("USER", "ADMIN"));
//        return http.build();
//    }
//
//    static String[] permitAllUrls = {
//            "/api/v1/auth/**",
//            "/v2/api-docs",
//            "/v3/api-docs",
//            "/v3/api-docs/**",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/swagger-ui/**",
//            "/swagger-ui.html",
//            "/auth/**",
//            "/users/register",
//            "/movies/{id}",
//            "/movies/get-all",
//            "/genre/{id}",
//            "/genre/get-all",
//            "/person/{id}",
//            "/person/get-all"
//    };
//
//    static String[] adminUrls = {
//            "/users/get-all",
//            "/movies/add",
//            "/movies/update/{id}",
//            "/movies/delete/{id}",
//            "/genre/add",
//            "/genre/update/{id}",
//            "/genre/delete/{id}",
//            "/person/add",
//            "/person/update/{id}",
//            "/person/delete/{id}",
//            "/movies/{movieId}/reviews/{reviewId}",
//            "/moves/{movieId}/reviews/get-all"
//    };
//
//    static String[] userUrls = {
//            "/users/change-password",
//            "/users/update/{id}",
//            "/watchlist/**",
//            "/movies/{movieId}/reviews/add",
//            "/movies/{movieId}/reviews/update/{reviewId}"
//    };
//
//    static String[] adminAndUserUrls = {
//            "/users/{id}",
//            "/users/delete/{id}",
//            "/movies/{movieId}/reviews/delete/{reviewId}"
//
//    };
//
//}
