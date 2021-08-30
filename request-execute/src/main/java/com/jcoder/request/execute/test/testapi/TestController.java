package com.jcoder.request.execute.test.testapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    public void printMap(Map<String, Object> datas) {
        for (String key : datas.keySet()) {
            System.out.println(key + " : " + datas.get(key));
        }
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<?> getPersonById(@RequestParam("name") String name,
                                           @RequestParam("country") String country,
                                           @PathVariable(name = "personId") Long personId,
                                           @RequestHeader Map<String, Object> headers) {

        Person person = new Person();
        person.setPersonId(personId);
        person.setName(name);
        person.setCountry(country);
        person.setAge(18);
        person.setSex("男");
        person.setRelations(Arrays.asList("父亲", "母亲"));

        printMap(headers);

        return ResponseEntity.ok(person);
    }


    @PostMapping("/person/{age}")
    public ResponseEntity<?> getPersonById(@RequestParam(name = "country") String country,
                                           @PathVariable(name = "age")Integer age,
                                           @RequestBody List<Person> personList,
                                           @RequestHeader Map<String, Object> headers) {

        for (int i = 0; i < personList.size(); i++) {
            personList.get(i).setPersonId(i + 1000L);
            personList.get(i).setCountry(country);
            personList.get(i).setAge(age);
        }

        //printMap(headers);

        return ResponseEntity.ok(personList);
    }

}
