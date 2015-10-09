/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import com.megatome.grails.RecaptchaService
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(GroupController)
@Mock([Group, User, Message, Post])
class GroupControllerSpec extends spock.lang.Specification {

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
        response.redirectedUrl == "/group/list"
    }

    @Test
    def "list action should send list of all groups to the view"() {
        setup:
        new Group(name: "Group45734895734").save(validate:  false)
        new Group(name: "Group43377372121").save(validate:  false)

        when: "the list action has hit"
        def model = controller.list()

        then: "the groups list should be send to the view"
        model["groups"].size() == 2
        model["messages"] != null
    }

    @Test
    def "save action should persist the created group and redirect to list"() {
        setup:
        request.method = "POST"
        def user = new User(username: "User 347534758934")
        mockSecurity(user)
        def userId = user.save(validate:  false).id
        def securityMock = mockFor(SpringSecurityService)
        securityMock.demand.getCurrentUser() { -> user }
        controller.springSecurityService = securityMock.createMock()

        when: "user correctly filled group creation form and provided valid recaptcha code"
        def groupName = "Group525788923457295734"
        controller.params["name"] = groupName
        controller.params["description"] = "Description348957345739"
        def recaptchaMock = mockFor(RecaptchaService)
        recaptchaMock.demand.verifyAnswer() { session, remoteAddress, params -> return true}
        recaptchaMock.demand.cleanUp() { }
        controller.recaptchaService = recaptchaMock.createMock()
        controller.save()

        then: "data is saved and user redirected to list action"
        Group.getAll().size() == 1
        response.redirectedUrl == "/group/list"
   }

    @Test
    def "when entered invalid recaptcha, data should not be saved"() {
        setup:
        request.method = "POST"
        def user = new User(username: "User 347534758934").save(validate:  false)
        mockSecurity(user)
        def userId = user.id
        def securityMock = mockFor(SpringSecurityService)
        securityMock.demand.getCurrentUser() { -> user }
        controller.springSecurityService = securityMock.createMock()

        when: "user correctly filled group creation form BUT not proper recaptcha code"
        controller.params["name"] = "Group525735773577295734"
        controller.params["description"] = "Description735755535739"
        def recaptchaMock = mockFor(RecaptchaService)
        recaptchaMock.demand.verifyAnswer() { session, remoteAddress, params -> return false}
        controller.recaptchaService = recaptchaMock.createMock()
        controller.save()

        then: "data is not saved"
        Group.getAll().size() == 0
        response.redirectedUrl == "/group/list"

    }

    @Test
    def "show action should send group details to the view"() {
        setup:
        def group = new Group(name: "Group45734895734").save(validate:  false)

        when: "the show action has hit with valid id"
        controller.params["id"] = group.id
        def model = controller.show()

        then: "the group should be sent to the view"
        model["group"].id == group.id

        when: "invalid id provided"
        controller.params["id"] = 6786746746737
        controller.show()

        then: "should redirect to list action"
        response.redirectedUrl == "/group/list"

    }


}
