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
@TestFor(User)
class UserSpec extends Specification {

    @Test
    def "test validation"() {
        when: "too long username or country or town"
        mockForConstraintsTests(User)
        def user = new User(username: "g7sd8g678sdg67d8s6gd8sgtjjjjjjjjjjjjjjjjjjjjjtruuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu6sd8g6sd8g", gender: Gender.MALE, country: "g7sd8g678sdg67d8s6gd8sgtjjjjjjjjjjjjjjjjjjjjjtruuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu6sd8g6sd8g", town: "g7sd8g678sdg67d8s6gd8sgtjjjjjjjjjjjjjjjjjjjjjtruuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu6sd8g6sd8g")

        then: "validation should fail"
        !user.validate()
        user.errors["username"] == "maxSize"
        user.errors["country"] == "maxSize"
        user.errors["town"] == "maxSize"

        when: "mandatory fields blank or null"
        mockForConstraintsTests(User)
        def user2 = new User(username: " ")

        then: "validation should fail"
        !user2.validate()
        user2.errors["username"] == "blank"

        when: "properties correctly filled"
        mockForConstraintsTests(User)
        def user3 = new User(username: "Rene", email: "a@a.com", password: "rene", gender: Gender.MALE, country: "Czech republic")

        then: "validation should pass"
        user3.validate()


    }
}
