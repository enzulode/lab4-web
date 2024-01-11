package com.enzulode.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private UUID id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String middleName;
    private String profileImg;
    private Boolean active;
}
