package pawlinski.matpetclinic.services.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pawlinski.matpetclinic.model.Pet;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PetMapServiceTest {

    PetMapService petMapService;
    private final Long petId = 1L;

    @BeforeEach
    void setUp() {
        petMapService = new PetMapService();
        Pet pet = new Pet();
        pet.setId(petId);

        petMapService.save(pet);
    }

    @Test
    void findAll() {
        Set<Pet> pets = petMapService.findAll();

        assertEquals(1, pets.size());
    }

    @Test
    void findById() {
        Pet petFound = petMapService.findById(petId);

        assertEquals(petId, petFound.getId());
    }

    @Test
    void findByNotExistingId() throws Exception {
        Pet petNotFound = petMapService.findById(5L);

        assertNull(petNotFound);
    }

    @Test
    void save() {
        Pet newPet = new Pet();
        newPet.setId(2L);
        petMapService.save(newPet);

        assertEquals(2, petMapService.findAll().size());

    }

    @Test
    void delete() {
        petMapService.delete(petMapService.findById(petId));

        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void deleteById() {
        petMapService.deleteById(petId);

        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void deleteByNonExistingId() {
        Pet pet = new Pet();
        pet.setId(2L);
        petMapService.deleteById(pet.getId());

        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteWithNullId() {
        Pet pet = new Pet();
        petMapService.delete(pet);

        assertEquals(1, petMapService.findAll().size());
    }
}