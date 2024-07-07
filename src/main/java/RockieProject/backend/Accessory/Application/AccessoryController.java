package RockieProject.backend.Accessory.Application;

import RockieProject.backend.Accessory.DTO.AccessoryDTO;
import RockieProject.backend.Accessory.Domain.Accessory;
import RockieProject.backend.Accessory.Domain.AccessoryService;
import RockieProject.backend.Rockie.DTO.RockieDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accessory/")
public class AccessoryController {

    @Autowired
    AccessoryService accessoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<String> createAccessory(@RequestBody AccessoryDTO accessoryDTO) {
        accessoryService.createAccessory(accessoryDTO);
        return ResponseEntity.ok("Accessory created");
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/all")
    public ResponseEntity<List<AccessoryDTO>> getAllAccessories() {
        List<AccessoryDTO> accessories= accessoryService.getAllAccessories_student();
        return ResponseEntity.ok(accessories);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all_admin")
    public ResponseEntity<List<Accessory>> getAllAccessories_admin() {
        List<Accessory> accessories= accessoryService.getAllAccessories_admin();
        return ResponseEntity.ok(accessories);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/buy/accessory/{name}")
    public ResponseEntity<String> buyAccessory(@PathVariable String name) {
        return accessoryService.buyAccessory(name);
    }









}
