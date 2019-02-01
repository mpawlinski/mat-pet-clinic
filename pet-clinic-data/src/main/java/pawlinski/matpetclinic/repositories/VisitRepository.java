package pawlinski.matpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import pawlinski.matpetclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
