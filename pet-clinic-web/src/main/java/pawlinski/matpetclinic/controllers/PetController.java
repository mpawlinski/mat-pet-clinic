package pawlinski.matpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pawlinski.matpetclinic.model.Owner;
import pawlinski.matpetclinic.model.Pet;
import pawlinski.matpetclinic.model.PetType;
import pawlinski.matpetclinic.services.OwnerService;
import pawlinski.matpetclinic.services.PetService;
import pawlinski.matpetclinic.services.PetTypeService;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    public static final String VIEW_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String createNewPetForm(Model model, Owner owner) {
        Pet newPet = new Pet();
        owner.getPets().add(newPet);
        newPet.setOwner(owner);

        model.addAttribute("pet", newPet);

        return VIEW_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreateNewPetForm (Owner owner, Model model, BindingResult result, Pet pet) {
        owner.getPets().add(pet);
        pet.setOwner(owner);

        if(result.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEW_PETS_CREATE_OR_UPDATE_FORM;

        } else {
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String editPetForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));

        return VIEW_PETS_CREATE_OR_UPDATE_FORM;
    }


    @PostMapping("/pets/{petId}/edit")
    public String processEditPetForm(Pet pet, Owner owner, Model model, BindingResult result) {
        owner.getPets().add(pet);
        pet.setOwner(owner);

        if(result.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEW_PETS_CREATE_OR_UPDATE_FORM;

        } else {
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
