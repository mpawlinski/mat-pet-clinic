package pawlinski.matpetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pawlinski.matpetclinic.model.*;
import pawlinski.matpetclinic.services.*;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner { // Spring Boot specific way to initialize data

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

            loadData();

    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);

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

        // adding visit
        Visit charliesPetVisit = new Visit();
        charliesPetVisit.setPet(charliesPet);
        charliesPetVisit.setDate(LocalDate.now());
        charliesPetVisit.setDescription("vaccination");

        visitService.save(charliesPetVisit);

        System.out.println("Loaded Owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Anthony");
        vet1.setLastName("Kiedis");
        vet1.getSpecialties().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("John");
        vet2.setLastName("Frusciante");
        vet2.getSpecialties().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
