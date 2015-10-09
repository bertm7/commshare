/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import grails.test.mixin.TestFor
import org.junit.Test
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Message)
class MessageSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Test
    def "test validation"() {
        when: "text not filled"
        mockForConstraintsTests(Message)
        def message = new Message()

        then: "validation should not pass"
        !message.validate()
        "nullable" == message.errors["text"]

        when: "text is blank"
        mockForConstraintsTests(Message)
        message = new Message(text: " ")

        then: "validation should not pass"
        !message.validate()
        "blank" == message.errors["text"]

        when: "proper constraints"
        mockForConstraintsTests(Message)
        message = new Message(text: "Hello")

        then: "validation should pass"
        message.validate()

    }
}