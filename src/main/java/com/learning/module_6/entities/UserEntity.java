package com.learning.module_6.entities;


import com.learning.module_6.entities.enums.Role;
import com.learning.module_6.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        roles.forEach(
                role ->{
                   Set<SimpleGrantedAuthority> permissions= PermissionMapping.getAuthoritiesForRole(role);
                   authorities.addAll(permissions);
                   authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
