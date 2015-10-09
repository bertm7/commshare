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
@TestFor(PostController)
@Mock([Post, Group, User, Message])
class PostControllerSpec extends Specification {

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
    def "save action should persist the created post and redirect to group list"() {
        setup:
        request.method = "POST"
        def userName = "User 347534758934"
        def groupName = "Group 344544454434"
        def user = new User(username: userName).save(validate:  false)
        mockSecurity(user)
        def securityMock = mockFor(SpringSecurityService)
        securityMock.demand.getCurrentUser() { -> user }
        controller.springSecurityService = securityMock.createMock()
        def userId = user.id
        def groupId = new Group(name: groupName).save(validate:  false).id

        when: "user correctly filled post creation form"
        controller.params["text"] = "Post525788923457295734"
        controller.params["user.id"] = userId
        controller.params["group.id"] = groupId
        controller.save()

        then: "data is saved and user redirected to group show action"
        Post.getAll().size() == 1
        response.redirectedUrl == "/group/show/${groupId}"
    }

}