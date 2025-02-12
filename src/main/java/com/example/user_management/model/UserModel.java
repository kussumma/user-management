package com.example.user_management.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class UserModel {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String role;

    @Indexed
    private String firstName;

    @Indexed
    private String lastName;

    private String address;
    private String phone;
    private String avatar;
}
