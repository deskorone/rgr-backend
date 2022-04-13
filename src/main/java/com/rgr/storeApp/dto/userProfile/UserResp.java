package com.rgr.storeApp.dto.userProfile;

import com.rgr.storeApp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserResp {

    private Long id;
    private String email;
    private List<String> roles;

    public static UserResp build(User user) {
        return new UserResp(user.getId(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(e -> e.getRole().toString())
                        .collect(Collectors.toList()));
    }
}
