package RockieProject.backend.Rockie.DTO;

import lombok.Data;

@Data
public class RockieInfoPatch {
    private String name;
    private String face;
    private String accessory;
    private String upper_accessory;
    private String lower_accessory;
}
