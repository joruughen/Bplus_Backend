package RockieProject.backend.Accessory.Domain;

import RockieProject.backend.Accessory.DTO.AccessoryDTO;
import RockieProject.backend.Accessory.Infraestructure.AccessoryRepository;
import RockieProject.backend.Rockie.Domain.Rockie;
import RockieProject.backend.Rockie.Infraestructure.RockieRepository;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccessoryService {

    @Autowired
    AccessoryRepository accessoryRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    ModelMapper mapper;

    public List<AccessoryDTO> mapAccessoryToDto(List<Accessory> accessories) {
        return accessories.stream()
                .map(Accessory -> mapper.map(Accessory, AccessoryDTO.class))
                .collect(Collectors.toList());
    }

    public void createAccessory(AccessoryDTO accessoryDTO) {
       Accessory accessory = mapper.map(accessoryDTO, Accessory.class);
       accessoryRepository.save(accessory);
    }


    public List<AccessoryDTO> getAllAccessories_student() {
        List<Accessory> accessoryList = accessoryRepository.findAll();
        return mapAccessoryToDto(accessoryList);
    }
    public List<Accessory> getAllAccessories_admin() {
        return accessoryRepository.findAll();
    }

    public boolean canBuyAccessory(String accessory) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);

        Accessory accessory_to_buy =  accessoryRepository.findByName(accessory);

        return accessory_to_buy.getPrice() <= student.getRockie_coins();
    }

    public ResponseEntity<String> buyAccessory(String accessory) {

        if (canBuyAccessory(accessory)){
            if (dontHaveAccessory(accessory)) {

                String email = SecurityContextHolder.getContext().getAuthentication().getName();
                Student student = studentRepository.findStudentByEmail(email);
                Rockie rockie = student.getRockie();

                Accessory accessory_to_buy =  accessoryRepository.findByName(accessory);

                List<Accessory> accessories =  rockie.getAccessories();
                accessories.add(accessory_to_buy);
                rockie.setAccessories(accessories);
                student.setRockie(rockie);
                student.setRockie_coins(student.getRockie_coins() - accessory_to_buy.getPrice());

                studentRepository.save(student);


                return ResponseEntity.ok("Compra exitosa :D");
            } else {return ResponseEntity.ok("Ya tienes este objeto");}
        }
        return ResponseEntity.ok("No tienes suficientes Rockie coins D:");

    }

    public boolean dontHaveAccessory(String accessory) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Rockie rockie = student.getRockie();
        Accessory accessory_to_buy =  accessoryRepository.findByName(accessory);

        for (Accessory accessory_iterator : rockie.getAccessories()) {
            if (Objects.equals(accessory_iterator, accessory_to_buy)){return false;}
        }
        return true;

    }
}
