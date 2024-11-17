package models;

import lombok.Data;

@Data
public class LoginRequestModel {
    String username, email, password;
}
