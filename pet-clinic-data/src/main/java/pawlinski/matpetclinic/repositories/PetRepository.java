package pawlinski.matpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import pawlinski.matpetclinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
