package RockieProject.backend.Student.Domain;

import RockieProject.backend.Excepciones.ExcepcionRecursoNoEncontrado;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByEmail(username).orElseThrow(()->(new ExcepcionRecursoNoEncontrado("User not found")));
    }

    public UserDetailsService userDetailsService() {
        return this;
    }

}
