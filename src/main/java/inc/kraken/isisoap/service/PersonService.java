/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.kraken.isisoap.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import inc.kraken.isisoap.entity.Person;
import inc.kraken.isisoap.repository.PersonRepository;

/**
 *
 * @author carlosndiaye
 */

@Service
public class PersonService implements IPersonService {
	@Autowired
	private PersonRepository personRepository;

	@Override
	public Person getPersonById(long articleId) {
		Person obj = personRepository.findByPersonId(articleId);
		return obj;
	}	
	@Override
	public List<Person> getAllPersons(){
		List<Person> list = new ArrayList<>();
		personRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addPerson(Person person){
	        List<Person> list = personRepository.findByFirstnameAndLastname(person.getFirstname(), person.getLastname()); 	
                if (list.size() > 0) {
    	           return false;
                } else {
    	           person = personRepository.save(person);
    	           return true;
                }
	}
        
	@Override
	public void updatePerson(Person person) {
		personRepository.save(person);
	}
	@Override
	public void deletePerson(Person person) {
		personRepository.delete(person);
	}
} 