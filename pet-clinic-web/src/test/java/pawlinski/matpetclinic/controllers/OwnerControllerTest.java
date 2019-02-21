package pawlinski.matpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pawlinski.matpetclinic.model.Owner;
import pawlinski.matpetclinic.services.OwnerService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        owners = new HashSet<>();
        Owner owner1 = new Owner();
        owner1.setId(1L);
        Owner owner2 = new Owner();
        owner2.setId(2L);
        owners.add(owner1);
        owners.add(owner2);

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void displayOwnerDetails() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);

        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }

    @Test
    public void createNewOwnerForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateForm"));

        verifyZeroInteractions(ownerService);
    }

    @Test
    public void updateOwnerForm() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);

        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateForm"));
    }

    @Test
    public void processCreateNewOwnerForm() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);

        when(ownerService.save(any(Owner.class))).thenReturn(owner);

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService, times(1)).save(any());
    }

    @Test
    public void processUpdateOwnerForm() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);

        when(ownerService.save(any())).thenReturn(owner);

        mockMvc.perform(post("/owners/1/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService, times(1)).save(any());
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(view().name("owners/findOwners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void processFindFormAndReturnMany() throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        Owner owner2 = new Owner();
        owner2.setId(2L);

        when(ownerService.findByLastNameLike(anyString())).thenReturn(Arrays.asList(owner1, owner2));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void processFindFormAndReturnOne() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);

        when(ownerService.findByLastNameLike(anyString())).thenReturn(Arrays.asList(owner));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

}