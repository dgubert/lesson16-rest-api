package models;

import lombok.Data;

@Data
public class RegisterRequestModel {
    String username, email, password;
}
