package pawlinski.matpetclinic.services.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pawlinski.matpetclinic.model.Owner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;

    final Long ownerId = 1L;
    final String lastName = "Coben";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        Owner owner1 = new Owner();
        owner1.setId(ownerId);
        owner1.setLastName(lastName);
        ownerMapService.save(owner1); //putting 1 owner into the map
    }

    @Test
    void findByLastName() {
        Owner coben = ownerMapService.findByLastName(lastName);

        assertNotNull(coben);
        assertEquals(ownerId, coben.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner foo = ownerMapService.findByLastName("foo");

        assertNull(foo);
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();

        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);

        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long specifiedId = 2L;
        Owner owner2 = new Owner();
        owner2.setId(specifiedId);
        Owner savedOwner = ownerMapService.save(owner2);

        assertEquals(specifiedId, owner2.getId());
    }

    @Test
    void saveGeneratedId() {
        Owner owner3 = new Owner();
        Owner savedOwner = ownerMapService.save(owner3);

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);

        assertEquals(0, ownerMapService.findAll().size());
    }
}