package pawlinski.matpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import pawlinski.matpetclinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

}
