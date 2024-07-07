package RockieProject.backend.Authentication.Domain;

import RockieProject.backend.Authentication.Config.JwtService;
import RockieProject.backend.Authentication.Dtos.JwtAuthenticationResponse;
import RockieProject.backend.Authentication.Dtos.LogInDTO;
import RockieProject.backend.Authentication.Dtos.SignUpDTO;
import RockieProject.backend.Authentication.Events.SendLogInMail;
import RockieProject.backend.Authentication.Events.SendSignUpEmail;
import RockieProject.backend.Excepciones.ExcepcionUsuarioYaExiste;
import RockieProject.backend.Rockie.Domain.Rockie;
import RockieProject.backend.Rockie.Infraestructure.RockieRepository;
import RockieProject.backend.Student.Domain.Role;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthenticationService {
    @Value("${EMAILUSERNAME}")
    private String email;

    private final StudentRepository studentRepository;

    private final RockieRepository rockieRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper mapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    public JwtAuthenticationResponse signup(SignUpDTO signUpDTO) {
        if (studentRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new ExcepcionUsuarioYaExiste("El correo ya está registrado.");
        }

        signUpDTO.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Rockie rockie = new Rockie();
        rockieRepository.save(rockie);

        Student student =  mapper.map(signUpDTO, Student.class);
        student.setRockie(rockie);
        student.setRole(Role.STUDENT);
        student.setExpired(false);
        student.setLocked(false);
        student.setCredentialsExpired(false);
        student.setEnable(true);

        studentRepository.save(student);

        applicationEventPublisher.publishEvent(new SendSignUpEmail(student, email));

        String jwt = jwtService.generateToken(student);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);

        return response;
    }

    public JwtAuthenticationResponse login(LogInDTO request) throws IllegalArgumentException {
        Student student = studentRepository.findStudentByEmail(request.getEmail());
        if (student == null) {
            throw new UsernameNotFoundException("El correo no está registrado.");
        }

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            throw new IllegalArgumentException("Credenciales incorrectas.");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String jwt = jwtService.generateToken(student);

        applicationEventPublisher.publishEvent(new SendLogInMail(student, email));

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);

        return response;
    }
}