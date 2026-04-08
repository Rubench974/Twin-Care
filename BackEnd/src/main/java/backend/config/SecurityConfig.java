package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import backend.security.JwtAuthenticationFilter;
import backend.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter,
                          CustomUserDetailsService userService) {
        this.jwtFilter = jwtFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(java.util.List.of(
                        "http://localhost:3000",
                        "http://localhost:3001",
                        "http://192.168.56.1:3000",
                        "http://192.168.1.17:3000",
                        "http://localhost:5173",
                        "https://twincare-t2xu.onrender.com"
                    ));
                    config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(java.util.List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/test", "/h2-console/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/dossiers/**").hasAnyAuthority("PATIENT", "ROLE_PATIENT", "ASSISTANT_MEDICAL", "ROLE_ASSISTANT_MEDICAL", "MEDECIN", "ROLE_MEDECIN")
                        .requestMatchers("/api/documents/**").hasAnyAuthority("PATIENT", "ROLE_PATIENT", "ASSISTANT_MEDICAL", "ROLE_ASSISTANT_MEDICAL", "MEDECIN", "ROLE_MEDECIN")
                        .requestMatchers("/api/validations/**").hasAnyAuthority("ASSISTANT_MEDICAL", "ROLE_ASSISTANT_MEDICAL", "MEDECIN", "ROLE_MEDECIN")
                        .requestMatchers("/api/chatbot/**").hasAnyAuthority("PATIENT", "ROLE_PATIENT")
                        .requestMatchers("/api/parcours/**").hasAnyAuthority("PATIENT", "ROLE_PATIENT", "ASSISTANT_MEDICAL", "ROLE_ASSISTANT_MEDICAL", "MEDECIN", "ROLE_MEDECIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userService);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
