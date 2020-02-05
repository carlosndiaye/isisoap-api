/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.kraken.isisoap.endpoints;

import inc.kraken.gs_ws.AddPersonRequest;
import inc.kraken.gs_ws.AddPersonResponse;
import inc.kraken.gs_ws.PersonInfo;
import inc.kraken.gs_ws.DeletePersonRequest;
import inc.kraken.gs_ws.DeletePersonResponse;
import inc.kraken.gs_ws.GetAllPersonsResponse;
import inc.kraken.gs_ws.GetPersonByIdRequest;
import inc.kraken.gs_ws.GetPersonByIdResponse;
import inc.kraken.gs_ws.ServiceStatus;
import inc.kraken.gs_ws.UpdatePersonRequest;
import inc.kraken.gs_ws.UpdatePersonResponse;
import inc.kraken.isisoap.entity.Person;
import inc.kraken.isisoap.service.IPersonService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 *
 * @author carlosndiaye
 */

@Endpoint
public class PersonEndPoint {
    private static final String NAMESPACE_URI = "gs_ws.kraken.inc";
	@Autowired
	private IPersonService personService;	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonByIdRequest")
	@ResponsePayload
	public GetPersonByIdResponse getPerson(@RequestPayload GetPersonByIdRequest request) {
		GetPersonByIdResponse response = new GetPersonByIdResponse();
		PersonInfo personInfo = new PersonInfo();
		BeanUtils.copyProperties(personService.getPersonById(request.getPersonId()), personInfo);
		response.setPersonInfo(personInfo);
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPersonsRequest")
	@ResponsePayload
	public GetAllPersonsResponse getAllPersons() {
		GetAllPersonsResponse response = new GetAllPersonsResponse();
		List<PersonInfo> personInfoList = new ArrayList<>();
		List<Person> personList = personService.getAllPersons();
		for (int i = 0; i < personList.size(); i++) {
		     PersonInfo ob = new PersonInfo();
		     BeanUtils.copyProperties(personList.get(i), ob);
		     personInfoList.add(ob);    
		}
		response.getPersonInfo().addAll(personInfoList);
		return response;
	}	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPersonRequest")
	@ResponsePayload
	public AddPersonResponse addPerson(@RequestPayload AddPersonRequest request) {
		AddPersonResponse response = new AddPersonResponse();		
    	        ServiceStatus serviceStatus = new ServiceStatus();		
		Person person = new Person();
		person.setFirstname(request.getFirstname());
		person.setLastname(request.getLastname());		
                boolean flag = personService.addPerson(person);
                if (flag == false) {
        	   serviceStatus.setStatusCode("CONFLICT");
        	   serviceStatus.setMessage("Content Already Available");
        	   response.setServiceStatus(serviceStatus);
                } else {
		   PersonInfo personInfo = new PersonInfo();
	           BeanUtils.copyProperties(person, personInfo);
		   response.setPersonInfo(personInfo);
        	   serviceStatus.setStatusCode("SUCCESS");
        	   serviceStatus.setMessage("Content Added Successfully");
        	   response.setServiceStatus(serviceStatus);
                }
                return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePersonRequest")
	@ResponsePayload
	public UpdatePersonResponse updatePerson(@RequestPayload UpdatePersonRequest request) {
		Person person = new Person();
		BeanUtils.copyProperties(request.getPersonInfo(), person);
		personService.updatePerson(person);
    	        ServiceStatus serviceStatus = new ServiceStatus();
    	        serviceStatus.setStatusCode("SUCCESS");
    	        serviceStatus.setMessage("Content Updated Successfully");
    	        UpdatePersonResponse response = new UpdatePersonResponse();
    	        response.setServiceStatus(serviceStatus);
    	        return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePersonRequest")
	@ResponsePayload
	public DeletePersonResponse deletePerson(@RequestPayload DeletePersonRequest request) {
		Person person = personService.getPersonById(request.getPersonId());
    	        ServiceStatus serviceStatus = new ServiceStatus();
		if (person == null ) {
	    	    serviceStatus.setStatusCode("FAIL");
	    	    serviceStatus.setMessage("Content Not Available");
		} else {
		    personService.deletePerson(person);
	    	    serviceStatus.setStatusCode("SUCCESS");
	    	    serviceStatus.setMessage("Content Deleted Successfully");
		}
    	        DeletePersonResponse response = new DeletePersonResponse();
    	        response.setServiceStatus(serviceStatus);
		return response;
	}	
}
