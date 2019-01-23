package pawlinski.matpetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pawlinski.matpetclinic.model.Owner;
import pawlinski.matpetclinic.model.Vet;
import pawlinski.matpetclinic.services.OwnerService;
import pawlinski.matpetclinic.services.VetService;
import pawlinski.matpetclinic.services.map.OwnerServiceMap;
import pawlinski.matpetclinic.services.map.VetServiceMap;

@Component
public class DataLoader implements CommandLineRunner { // Spring Boot specific way to initialize data

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        this.ownerService = new OwnerServiceMap();
        this.vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Hank");
        owner1.setLastName("Moody");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Charlie");
        owner2.setLastName("Harper");

        ownerService.save(owner2);

        System.out.println("Loaded Owners...");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Anthony");
        vet1.setLastName("Kiedis");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("John");
        vet2.setLastName("Frusciante");

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
