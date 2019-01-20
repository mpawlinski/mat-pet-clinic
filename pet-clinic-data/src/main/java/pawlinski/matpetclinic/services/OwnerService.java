package pawlinski.matpetclinic.services;

import pawlinski.matpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService {

    Owner findById(Long id);

    Owner save(Owner owner);

    Set<Owner> findAll();
}
