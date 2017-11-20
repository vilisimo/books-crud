package vault.validation

import spock.lang.Shared
import spock.lang.Specification

import javax.validation.ConstraintValidatorContext

class UuidValidatorTest extends Specification {

    @Shared
    def validator = new UuidValidator()

    @Shared
    def context = Mock(ConstraintValidatorContext.class)

    def "invalid uuid does not pass validation"() {
        given: "invalid uuid"
            def uuid = "invalid"

        when: "uuid is validated"
            def valid = validator.isValid(uuid, context)

        then: "uuid is judged to be invalid"
            !valid
    }

    def "valid uuid passes validation"() {
        given: "valid uuid"
            def uuid = UUID.randomUUID().toString()

        when: "uuid is validated"
            def valid = validator.isValid(uuid, context)

        then: "uuid is judged to be valid"
            valid
    }
}
