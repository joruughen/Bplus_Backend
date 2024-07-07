package RockieProject.backend.Student.Domain;

import RockieProject.backend.ExerciseSession.Domain.ExerciseSession;
import RockieProject.backend.Habit.Domain.Habit;
import RockieProject.backend.Meditation.Domain.Meditation;
import RockieProject.backend.Rockie.Domain.Rockie;
import RockieProject.backend.StudySession.Domain.StudySession;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Student implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "password")
    private String password;

    @Column(name = "user_description")
    private String user_description;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "rockie_coins")
    private double rockie_coins;

    //Roles
    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean expired;

    private Boolean locked;

    private Boolean credentialsExpired;

    private Boolean enable;

//    relations
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rockie_id", referencedColumnName = "id")
    private Rockie rockie;

    @JsonManagedReference
    @OneToMany(mappedBy = "student")
    private List<Habit> habits;

    @JsonManagedReference//Avoid loop
    @OneToMany(mappedBy = "student")
    private List<ExerciseSession> exerciseSessions;

    @JsonManagedReference
    @OneToMany(mappedBy = "student")
    private List<Meditation> meditations;

    @JsonManagedReference
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudySession> studySessions;



    //Para que funcione el UserDetails y por consiguiente el OAuth

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

}
