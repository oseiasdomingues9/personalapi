package one.digitalinnovation.personalapi.service;

import one.digitalinnovation.personalapi.dto.MessageResponseDTO;
import one.digitalinnovation.personalapi.dto.request.PersonDTO;
import one.digitalinnovation.personalapi.entity.Person;
import one.digitalinnovation.personalapi.exception.PersonNotFoundException;
import one.digitalinnovation.personalapi.mapper.PersonMapper;
import one.digitalinnovation.personalapi.repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonalRepository personalRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    ///**CREATE**///
    public MessageResponseDTO createPerson(PersonDTO personDTO) {

        Person personToSave = personMapper.toModel(personDTO);
        //Save o objeto que chegou de personControlller, depois retorna uma mensagem
        Person savedPerson = personalRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(),"Created person with ID ");
    }

    ///**READ**///
    public List<PersonDTO> listAll() {
        List<Person> allPeople = personalRepository.findAll();

        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }

    ///**UPDATE**///
    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException{
        verifyIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);

        //Save o objeto que chegou de personControlller, depois retorna uma mensagem
        Person updatePerson = personalRepository.save(personToUpdate);
        return createMessageResponse(updatePerson.getId(), "Update person with ID ");
    }

    ///**DELETE**///
    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personalRepository.deleteById(id);
    }

    ///**METODOS PRIVADOS**///
    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personalRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id , String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
