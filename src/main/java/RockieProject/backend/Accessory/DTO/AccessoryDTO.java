package RockieProject.backend.Accessory.DTO;

import RockieProject.backend.Accessory.Domain.Type_Accessory;
import lombok.Data;

@Data
public class AccessoryDTO {
    private String name;
    private double price;
    private String url;
    private Type_Accessory type;
}
