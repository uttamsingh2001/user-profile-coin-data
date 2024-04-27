package com.example.userprofilecoindata.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    private String password;

    private String firstName;

    private String lastName;

    private String mobile;
}
