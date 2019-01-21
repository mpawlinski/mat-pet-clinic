package pawlinski.matpetclinic.services;

import pawlinski.matpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
