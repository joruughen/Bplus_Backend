package RockieProject.backend.Rockie.Application;

import RockieProject.backend.Rockie.DTO.RockieDTO;
import RockieProject.backend.Rockie.DTO.RockieInfoPatch;
import RockieProject.backend.Rockie.Domain.RockieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RockieControllerTest {

    @InjectMocks
    private RockieController rockieController;

    @Mock
    private RockieService rockieService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(rockieController).build();
            objectMapper = new ObjectMapper();
        }
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testGetRockie() throws Exception {
        RockieDTO rockieDTO = new RockieDTO();
        rockieDTO.setName("Rockie");
        rockieDTO.setLevel(1);
        rockieDTO.setFace("Cara");
        rockieDTO.setAccessory("Sombrero");
        rockieDTO.setUpper_accessory("Capa");
        rockieDTO.setLower_accessory("Botas XD");

        when(rockieService.getRockieByEmail()).thenReturn(rockieDTO);

        mockMvc.perform(get("/rockie/info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(rockieDTO)));
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testPatchRockie() throws Exception {
        // Crear un Rockie con accesorios
        RockieDTO rockieDTO = new RockieDTO();
        rockieDTO.setName("Roca");
        rockieDTO.setLevel(1);
        rockieDTO.setFace("Mascara");
        rockieDTO.setAccessory("Gorro");
        rockieDTO.setUpper_accessory("UTEC");
        rockieDTO.setLower_accessory("Gato");

        RockieInfoPatch rockieInfoPatch = new RockieInfoPatch();

        // Mockear el service, solo estamos probando los endpoints
        doNothing().when(rockieService).updateRockieInfoByStudent(rockieInfoPatch);
        when(rockieService.getRockieByEmail()).thenReturn(rockieDTO);

        mockMvc.perform(patch("/rockie/update")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rockieInfoPatch)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(rockieDTO)));
    }
}
