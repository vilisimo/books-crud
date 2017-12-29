package vault.exception

import spock.lang.Specification

import javax.ws.rs.core.Response

class ResourceExceptionMapperTest extends Specification {

    def "exception is converted to response"() {
        given: "a mapper"
            def mapper = new ResourceExceptionMapper()

        when: "an exception is passed in"
            def exception = new BookNotFoundException(Response.Status.NOT_FOUND, "resource")
            def response = mapper.toResponse(exception)

        then: "exception is converted to response"
            response.getStatusInfo() == Response.Status.NOT_FOUND

        and: "response has exception's data"
            def properties = response.entity.properties
            properties.get("code") == response.status
            properties.get("resourceId") == "resource"
    }
}
