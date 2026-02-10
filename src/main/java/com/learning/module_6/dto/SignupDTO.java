package com.learning.module_6.dto;


import com.learning.module_6.entities.enums.Permission;
import com.learning.module_6.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
