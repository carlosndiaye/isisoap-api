/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.kraken.isisoap.repository;

import inc.kraken.isisoap.entity.Person;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author carlosndiaye
 */
public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findByPersonId(long articleId);
    List<Person> findByFirstnameAndLastname(String firstname, String lastname);
}
