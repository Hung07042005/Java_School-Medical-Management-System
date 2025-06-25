// src/main/java/com/example/demo/config/SecurityConfig.java
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Kích hoạt Spring Security cho ứng dụng web
public class SecurityConfig {

    // Định nghĩa PasswordEncoder (quan trọng để mã hóa mật khẩu)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cấu hình UserDetailsService (ví dụ In-memory users)
    // Trong thực tế, bạn sẽ lấy user từ database thông qua UserRepository và một custom UserDetailsService
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                                .password(passwordEncoder.encode("adminpass")) // Mã hóa mật khẩu
                                .roles("ADMIN", "MANAGER", "NURSE", "PARENT", "STUDENT") // Gán các vai trò
                                .build();

        UserDetails nurse = User.withUsername("nurse")
                                .password(passwordEncoder.encode("nursepass"))
                                .roles("NURSE")
                                .build();

        UserDetails parent = User.withUsername("parent")
                                .password(passwordEncoder.encode("parentpass"))
                                .roles("PARENT")
                                .build();

        UserDetails student = User.withUsername("student")
                                .password(passwordEncoder.encode("studentpass"))
                                .roles("STUDENT")
                                .build();

        return new InMemoryUserDetailsManager(admin, nurse, parent, student);
    }

    // Cấu hình quy tắc bảo mật HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll() // Cho phép truy cập tài nguyên tĩnh và trang chủ
                .requestMatchers("/students/**").hasAnyRole("ADMIN", "MANAGER", "NURSE") // Chỉ admin, manager, nurse được quản lý học sinh
                .requestMatchers("/medical-records/**").hasAnyRole("ADMIN", "MANAGER", "NURSE", "PARENT") // Cha mẹ cũng có thể quản lý hồ sơ
                .anyRequest().authenticated() // Tất cả các request khác yêu cầu xác thực
            )
            .formLogin(form -> form
                .loginPage("/login") // Đường dẫn đến trang đăng nhập tùy chỉnh
                .permitAll() // Cho phép tất cả mọi người truy cập trang login
            )
            .logout(logout -> logout
                .permitAll() // Cho phép tất cả mọi người logout
                .logoutSuccessUrl("/") // Sau khi logout, chuyển về trang chủ
            )
            .csrf(csrf -> csrf.disable()); // Tạm thời tắt CSRF để dễ phát triển, trong sản phẩm thì KHÔNG NÊN làm vậy

        return http.build();
    }
}