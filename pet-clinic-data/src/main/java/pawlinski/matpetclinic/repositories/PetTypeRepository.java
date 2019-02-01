package pawlinski.matpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import pawlinski.matpetclinic.model.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
