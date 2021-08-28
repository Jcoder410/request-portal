package com.jcoder.request.execute.test.app;

import com.jcoder.request.execute.test.testapi.Person;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * @author Qiu
 */
@WebService
public interface SoapForTestService {

    @WebMethod(operationName = "singlePerson")
    Person getPersonById(@WebParam(name = "personName") String personName,
                         @WebParam(name = "age") Integer age);


    @WebMethod(operationName = "multiPerson")
    List<Person> getPersons(@WebParam(name = "country") String country,
                            @WebParam(name = "age") Integer age);

    @WebMethod(operationName = "addPerson")
    List<Person> addPerson(@WebParam(name = "personList") List<Person> persons);

}
