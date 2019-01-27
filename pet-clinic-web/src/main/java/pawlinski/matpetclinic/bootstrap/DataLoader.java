package pawlinski.matpetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pawlinski.matpetclinic.model.Owner;
import pawlinski.matpetclinic.model.Pet;
import pawlinski.matpetclinic.model.PetType;
import pawlinski.matpetclinic.model.Vet;
import pawlinski.matpetclinic.services.OwnerService;
import pawlinski.matpetclinic.services.PetTypeService;
import pawlinski.matpetclinic.services.VetService;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner { // Spring Boot specific way to initialize data

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("Hank");
        owner1.setLastName("Moody");
        owner1.setAddress("2243 Venice Beach");
        owner1.setCity("Los Angeles");
        owner1.setTelephone("999777333");

        Pet hanksPet = new Pet();
        hanksPet.setPetType(savedDogPetType);
        hanksPet.setOwner(owner1);
        hanksPet.setBirthDate(LocalDate.now());
        hanksPet.setName("Yusuf");
        owner1.getPets().add(hanksPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Charlie");
        owner2.setLastName("Harper");
        owner2.setAddress("24 Long Beach");
        owner2.setCity("Los Angeles");
        owner2.setTelephone("123456789");

        Pet charliesPet = new Pet();
        charliesPet.setPetType(savedCatPetType);
        charliesPet.setOwner(owner2);
        charliesPet.setBirthDate(LocalDate.now());
        charliesPet.setName("Teshimitsu");
        owner2.getPets().add(charliesPet);

        ownerService.save(owner2);

        System.out.println("Loaded Owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Anthony");
        vet1.setLastName("Kiedis");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("John");
        vet2.setLastName("Frusciante");

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
