package com.bfarming.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

//@PasswordMatches
public class UserDto implements Serializable {
    @NotNull
    @NotEmpty(message = "*Please provide a Valid FirstName")
    private String firstName;

    @NotNull
    @NotEmpty(message = "*Please provide a Valid LastName")
    private String lastName;

   // @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    //@ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;



    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }





    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }



    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=").append(firstName).append(", lastName=").append(lastName)
                .append(", password=").append(password).append(", matchingPassword=")
                .append(matchingPassword).append(", email=").append(email);
        return builder.toString();
    }

}