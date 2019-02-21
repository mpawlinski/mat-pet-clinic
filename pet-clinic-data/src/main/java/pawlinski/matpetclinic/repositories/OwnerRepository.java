package pawlinski.matpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import pawlinski.matpetclinic.model.Owner;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findByLastNameLike(String lastName);
}
