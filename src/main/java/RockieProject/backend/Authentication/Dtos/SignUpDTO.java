package RockieProject.backend.Authentication.Dtos;

import lombok.Data;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String user_name;
}
