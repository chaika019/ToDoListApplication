package by.chaika19.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    USER,
    ADMIN;

    public SimpleGrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + name());
    }
}
