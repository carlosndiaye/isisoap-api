/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.kraken.isisoap.service;

import inc.kraken.isisoap.entity.Person;
import java.util.List;

/**
 *
 * @author carlosndiaye
 */


public interface IPersonService {
     List<Person> getAllPersons();
     Person getPersonById(long personId);
     boolean addPerson(Person person);
     void updatePerson(Person person);
     void deletePerson(Person person);
} 