package pawlinski.matpetclinic.formatters;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import pawlinski.matpetclinic.model.PetType;
import pawlinski.matpetclinic.services.PetTypeService;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Collection<PetType> collection = petTypeService.findAll();

        for(PetType petType : collection) {
            if(petType.getName().equals(text)) {
                return petType;
            }
        }

        throw new ParseException("pet type was not found", 0);
    }
}
