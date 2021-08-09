package one.digitalinnovation.personalapi.service;

import one.digitalinnovation.personalapi.dto.MessageResponseDTO;
import one.digitalinnovation.personalapi.entity.Person;
import one.digitalinnovation.personalapi.repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
@Service
public class PersonService {
    @Autowired
    private PersonalRepository personalRepository;

    ///**CREATE**///
    public MessageResponseDTO createPerson(Person person){
        //Save o objeto que chegou de personControlller, depois retorna uma mensagem
        Person savedPerson = personalRepository.save(person);
        return MessageResponseDTO
                .builder()
                .message("Created person with ID " + savedPerson.getId())
                .build();
    }

}
