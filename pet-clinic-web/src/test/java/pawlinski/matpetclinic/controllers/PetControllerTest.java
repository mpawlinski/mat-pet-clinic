package pawlinski.matpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pawlinski.matpetclinic.model.Owner;
import pawlinski.matpetclinic.model.Pet;
import pawlinski.matpetclinic.model.PetType;
import pawlinski.matpetclinic.services.OwnerService;
import pawlinski.matpetclinic.services.PetService;
import pawlinski.matpetclinic.services.PetTypeService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetControllerTest {

    private static final String VIEW_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

    MockMvc mockMvc;

    PetController controller;

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    Owner owner;

    Set<PetType> petTypes;

    Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new PetController(ownerService, petService, petTypeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        owner = new Owner();
        owner.setId(1L);

        petTypes = new HashSet<>();
        PetType dogPetType = new PetType();
        dogPetType.setId(1L);
        dogPetType.setName("dog");
        PetType catPetType = new PetType();
        catPetType.setId(2L);
        catPetType.setName("cat");
        petTypes.add(dogPetType);
        petTypes.add(catPetType);

        pet = new Pet();
        pet.setId(1L);
    }

    @Test
    public void createNewPetForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("types"))
                .andExpect(view().name(VIEW_PETS_CREATE_OR_UPDATE_FORM));
    }

    @Test
    public void processCreateNewPetForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService, times(1)).save(any());
    }

    @Test
    public void editPetForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(pet);
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("types"))
                .andExpect(view().name(VIEW_PETS_CREATE_OR_UPDATE_FORM));

    }

    @Test
    public void processUpdatePetForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("types"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService, times(1)).save(any(Pet.class));
    }

    //todo add rest tests for findOwner, populatePetTypes
}