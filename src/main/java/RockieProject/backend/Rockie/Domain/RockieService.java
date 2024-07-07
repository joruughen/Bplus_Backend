package RockieProject.backend.Rockie.Domain;

import RockieProject.backend.Accessory.Domain.Accessory;
import RockieProject.backend.Rockie.DTO.RockieDTO;
import RockieProject.backend.Rockie.DTO.RockieInfoPatch;
import RockieProject.backend.Rockie.Infraestructure.RockieRepository;
import RockieProject.backend.Student.DTO.StudentInfoDTO;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RockieService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;
    private final RockieRepository rockieRepository;

    public RockieDTO getRockieByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Student student = studentRepository.findStudentByEmail(email);

        return mapper.map(student.getRockie(), RockieDTO.class);
    }

    public void updateRockieInfoByStudent(RockieInfoPatch rockieInfoPatch) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);

        Rockie rockie = student.getRockie();

        if (rockieInfoPatch.getName() != null){rockie.setName(rockieInfoPatch.getName());}
        if (rockieInfoPatch.getFace() != null){
            for (Accessory accessory : rockie.getAccessories()) {
                if (Objects.equals(accessory.getName(), rockieInfoPatch.getFace())){rockie.setFace(rockieInfoPatch.getFace());}
            }
        }
        if (rockieInfoPatch.getAccessory() != null){
            for (Accessory accessory : rockie.getAccessories()) {
                if (Objects.equals(accessory.getName(), rockieInfoPatch.getAccessory())){rockie.setAccessory(rockieInfoPatch.getAccessory());}
            }
        }
        if (rockieInfoPatch.getUpper_accessory() != null){
            for (Accessory accessory : rockie.getAccessories()) {
                if (Objects.equals(accessory.getName(), rockieInfoPatch.getUpper_accessory())){rockie.setUpper_accessory(rockieInfoPatch.getUpper_accessory());}
            }
        }
        if (rockieInfoPatch.getLower_accessory() != null){
            for (Accessory accessory : rockie.getAccessories()) {
                if (Objects.equals(accessory.getName(), rockieInfoPatch.getLower_accessory())){rockie.setLower_accessory(rockieInfoPatch.getLower_accessory());}
            }
        }

        rockieRepository.save(rockie);
    }
}
