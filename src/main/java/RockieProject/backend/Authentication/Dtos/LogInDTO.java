package RockieProject.backend.Authentication.Dtos;

import lombok.Data;

@Data
public class LogInDTO {
    private String email;
    private String password;
}
