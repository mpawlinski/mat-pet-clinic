package pawlinski.matpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pawlinski.matpetclinic.model.Owner;
import pawlinski.matpetclinic.services.OwnerService;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", new Owner());

        return "owners/findOwners";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView displayOwnerDetails(@PathVariable Long ownerId) {
//        model.addAttribute("owner", ownerService.findById(Long.valueOf(id)));
//        return "owners/ownerDetails";

        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));

        return mav;
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        //parameterless GET request will return the list of all owners
        if(owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> ownersList = ownerService.findByLastNameLike("%" + owner.getLastName() + "%");

        if(ownersList.isEmpty()) {
            result.rejectValue("lastName", "notFound", "not found"); //error
            return "/owners/findOwners";

        } else if(ownersList.size() == 1) { //found 1 owner
            owner = ownersList.iterator().next();
            return "redirect:/owners/" + owner.getId();

        } else { //found multiple owners
            model.addAttribute("owners", ownersList);
            return "owners/ownersList";
        }
    }

    //    @GetMapping({"", "/", "/index", "/index.html"})
//    public String listOwners(Model model) {
//
//        model.addAttribute("owners", ownerService.findAll());
//
//        return "owners/index";
//    }
}
