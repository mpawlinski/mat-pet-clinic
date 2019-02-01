package pawlinski.matpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import pawlinski.matpetclinic.model.Vet;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
