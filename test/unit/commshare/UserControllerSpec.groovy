/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.junit.Test
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([User, Message, Post])
class UserControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def private mockSecurity(user) {
        def securityMock = mockFor(SpringSecurityService)
        securityMock.demand.encodePassword() { -> }
        securityMock.demand.beforeInsert() { -> }
        user.springSecurityService = securityMock.createMock()
        return user
    }

    @Test
    def "index action should redirect to list page"() {
        when: "the index action has hit"
        controller.index()

        then: "the user should be redirected to list action"
        response.redirectedUrl == "/user/list"
    }

    @Test
    def "list action should send list of all users to the view"() {
        setup:
        mockSecurity(new User(username: "User45734895734", password: "pass").save(validate:  false))
        mockSecurity(new User(username: "User43377372121", password: "pass").save(validate:  false))

        when: "the list action has hit"
        def model = controller.list()

        then: "the user list should be send to the view"
        model["users"].size() == 2
        model["messages"] != null
    }

    @Test
    def "show action should send user details to the view"() {
        setup:
        def user = mockSecurity(new User(username: "User45734895734", password: "pass")).save(validate:  false)
        def securityMock = mockFor(SpringSecurityService)
        securityMock.demand.getCurrentUser() { -> user }
        controller.springSecurityService = securityMock.createMock()

        when: "the show action has hit with valid id"
        controller.params["id"] = user.id
        def model = controller.show()

        then: "the group should be sent to the view"
        model["user"].id == user.id

        when: "invalid id provided"
        controller.params["id"] = 4444745556737
        controller.show()

        then: "should redirect to list action"
        response.redirectedUrl == "/user/list"
    }

    @Test
    def "user can follow other users"() {
        setup:
        def whoFollows = mockSecurity(new User(username: "WhoFollows", id: 1, password: "pass")).save(validate: false)
        def whoIsFollowed = mockSecurity(new User(username: "WhoIsFollowed", password: "pass")).save(validate: false)
        def securityMock = mockFor(SpringSecurityService)
        securityMock.demand.getCurrentUser() { -> whoFollows }
        controller.springSecurityService = securityMock.createMock()

        when: "follow action has hit"
        controller.params["id"] = whoIsFollowed.id
        controller.followToggle()

        then: "the followed user has added one user in the following list"
        User.findByUsername("WhoIsFollowed").following?.size() == 1
        User.findByUsername("WhoIsFollowed").following?.min()?.id == whoFollows.id
    }

    @Test
    def "update action updates user information"() {
        setup:
        request.method = "POST"
        def userName = "User5234523555555552"
        def userId = mockSecurity(new User(id: 1, username: userName, password: "pass", gender: Gender.MALE, town: "Prague", info: "myinfo")).save(validate: false).id

        when: "user fills the data and update action has hit"
        controller.params["id"] = userId
        controller.params["info"] = "new info"
        controller.params["town"] = null
        controller.update()

        then: "data should be updated and flow redirected to user profile"
        User.get(userId).info == "new info"
        User.get(userId).town == null
        response.redirectedUrl == "/user/show/1"
    }

}