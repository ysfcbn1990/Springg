package com.tpe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "provide first name")
    private String firstName;

    @NotBlank(message = "provide last name")
    private String lastName;

    @Size(min = 5,max=10)
    @NotBlank(message = "provide user name")
    private String userName;

    @Size(min = 4,max=8)
    @NotBlank(message = "provide password")
    private String password;

}
