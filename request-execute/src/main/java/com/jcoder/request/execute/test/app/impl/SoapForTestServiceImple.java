package com.jcoder.request.execute.test.app.impl;

import com.jcoder.request.execute.test.app.SoapForTestService;
import com.jcoder.request.execute.test.testapi.Person;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebService(serviceName = "webserviceForTest",//对外发布的服务名
        targetNamespace = "http://app.test.portal.request.jcoder.com",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface = "com.jcoder.request.execute.test.app.SoapForTestService")
@Service
public class SoapForTestServiceImple implements SoapForTestService {

    @Override
    public Person getPersonById(String personName, Integer age) {

        Person person = new Person();
        person.setPersonId(10001L);
        person.setName(personName);
        person.setCountry("中国");
        person.setAge(age);
        person.setSex("男");
        person.setRelations(Arrays.asList("父亲", "母亲"));
        return person;
    }

    @Override
    public List<Person> getPersons(String country, Integer age) {

        List<Person> personList = new ArrayList<>();

        personList.add(new Person(1001L,
                "盖世猪猪",
                age,
                "男",
                country,
                Arrays.asList("父亲", "母亲")));

        personList.add(new Person(1002L,
                "压力山大",
                age,
                "男",
                country,
                Arrays.asList("父亲", "母亲", "哥哥")));

        return personList;
    }

    @Override
    public List<Person> addPerson(List<Person> persons) {

        for (int i = 0; i < persons.size(); i++) {
            persons.get(i).setPersonId(i + 1000L);
        }
        return persons;
    }
}
