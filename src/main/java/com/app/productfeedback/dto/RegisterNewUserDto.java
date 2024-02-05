package com.app.productfeedback.dto;

import com.app.productfeedback.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterNewUserDto {
    @NotBlank(message = "email can't be empty.")
    @NotNull(message = "email can't be null.")
    @Email(message = "email must be an valid e-mail type.")
    private String email;

    @NotBlank(message = "username can't be empty.")
    @NotNull(message = "username can't be null.")
    private String username;

    @NotBlank(message = "password can't be empty.")
    @NotNull(message = "password can't be null.")
    @Size(min = 6, message = "password must have at least 6 characters.")
    private String password;

    public User toEntity() {
        User user = new User();

        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setUsername(this.username);

        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
