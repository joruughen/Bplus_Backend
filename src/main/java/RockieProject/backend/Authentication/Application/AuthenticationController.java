package RockieProject.backend.Authentication.Application;

import RockieProject.backend.Authentication.Domain.AuthenticationService;
import RockieProject.backend.Authentication.Dtos.JwtAuthenticationResponse;
import RockieProject.backend.Authentication.Dtos.LogInDTO;
import RockieProject.backend.Authentication.Dtos.SignUpDTO;
import RockieProject.backend.Student.Domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpDTO dto) {
        return ResponseEntity.ok(authenticationService.signup(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> logIn(@RequestBody LogInDTO dto) {
        return ResponseEntity.ok(authenticationService.login(dto));

    }

}
