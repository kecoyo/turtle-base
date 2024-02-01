package com.kecoyo.turtleopen.common.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto implements Serializable {

    private Integer id;

    private String username;

    private String name;

    private String avatar;

    private String phone;

    private String gender;

    private String birthday;

    private String email;

    private List<Long> dataScopes;

    private List<AuthorityDto> authorities;

    private String token;

    public Set<String> getRoles() {
        if (authorities == null || authorities.isEmpty()) {
            return null;
        }
        return authorities.stream().map(AuthorityDto::getAuthority).collect(Collectors.toSet());
    }

}
