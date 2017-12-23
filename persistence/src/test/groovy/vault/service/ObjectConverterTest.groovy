package vault.service

import com.fasterxml.jackson.core.type.TypeReference
import groovy.transform.EqualsAndHashCode
import spock.lang.Shared
import spock.lang.Specification

class ObjectConverterTest extends Specification {

    @Shared
    def converter = new ObjectConverter()

    def "Object is converted to a String"() {
        given: "an object"
        def contrived = new Contrived(id: 1, description: "A contrived test class")

        when: "an object is converted to a string"
        def result = converter.asString(contrived)

        then: "a string representing the object is produced"
        result == '{"id":1,"description":"A contrived test class"}'
    }

    def "List of objects is converted to a String"() {
        given: "a list of objects"
        def objects = [new Contrived(id: 1, description: "one"), new Contrived(id: 2, description: "two")]

        when: "a list is converted to a string"
        def result = converter.asString(objects)

        then: "a string representing the list is produced"
        result == '[{"id":1,"description":"one"},{"id":2,"description":"two"}]'
    }

    def "A string of an object is converted back to an object"() {
        given: "a string of an object"
        def object = '{"id":1,"description":"A contrived test class"}'

        when: "a string is converted to an object"
        def result = converter.asObject(object, new TypeReference<Contrived>() {})

        then: "an object is returned"
        result == new Contrived(id: 1, description: "A contrived test class")
    }

    def "A string of a list of objects is converted back to a list of objects"() {
        given: "a string of a list of objects"
        def objects = '[{"id":1,"description":"one"},{"id":2,"description":"two"}]'

        when: "a string is converted to list"
        def result = converter.asObject(objects, new TypeReference<List<Contrived>>() {})

        then: "a list of objects is returned"
        def expected = [new Contrived(id:1, description: "one"), new Contrived(id: 2, description: "two")]
        result == expected
    }

    def "A value of 'null' is rejected"() {
        when: "a value of null is attempted to convert to a string"
        converter.asString(null)

        then: "it is rejected"
        thrown(NullPointerException.class)

        when: "a value of null is attempted to convert to an object"
        converter.asObject(null, new TypeReference<Contrived>() {})

        then: "it is rejected"
        thrown(NullPointerException.class)
    }

    def  "An empty string is not converted to an object"() {
        when: "an empty string is passed in"
        converter.asObject("", new TypeReference<Contrived>() {})

        then: "it is rejected"
        thrown(IllegalArgumentException.class)
    }

    @EqualsAndHashCode
    private static class Contrived {
        int id
        String description
    }
}
