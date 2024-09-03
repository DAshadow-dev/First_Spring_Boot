package com.example.spring2.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.spring2.validator.DobConstraint;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotEmpty
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min = 2,message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
}
