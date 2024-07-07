package RockieProject.backend.Rockie.Application;

import RockieProject.backend.Rockie.DTO.RockieDTO;
import RockieProject.backend.Rockie.DTO.RockieInfoPatch;
import RockieProject.backend.Rockie.Domain.Rockie;
import RockieProject.backend.Rockie.Domain.RockieService;
import RockieProject.backend.Rockie.Infraestructure.RockieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rockie/")
public class RockieController {

    @Autowired
    RockieService rockieService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/info")
    public ResponseEntity<RockieDTO> getRockie() {
        return ResponseEntity.ok(rockieService.getRockieByEmail());
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/update")
    public ResponseEntity<RockieDTO> patchRockie(@RequestBody RockieInfoPatch rockieInfoPatch) {
        rockieService.updateRockieInfoByStudent(rockieInfoPatch);
        return ResponseEntity.ok(rockieService.getRockieByEmail());
    }
}
