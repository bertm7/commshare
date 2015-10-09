/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import com.megatome.grails.RecaptchaService
import grails.plugins.springsecurity.Secured

class GroupController {

    def scaffold = true

    static allowedMethods = [save: 'POST']

    def springSecurityService
    RecaptchaService recaptchaService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        redirect(action: "list")
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def list() {
        [groups: Group.list(params), groupCount: Group.count(), messages: Message.list(max: 5)]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'IS_AUTHENTICATED_REMEMBERED'])
    def save() {
        Group group = new Group(params)
        User creator = springSecurityService.getCurrentUser() as User;
        log.debug('Group creator: ' + creator);
        group.setCreator(creator)
        def recaptchaOK = true
        if (!recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)) {
            recaptchaOK = false
        }
        if (recaptchaOK && group.save(flush: true)) {
            recaptchaService.cleanUp(session)
            flash.message = message(code: 'group.createdsuccess.message')
        } else {
            flash.error = message(code: 'group.createdfail.message')
        }
        new Message(text: "@${group.creator.username} has created new community #${group.name}.").save()
        redirect(action: "list")
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def show() {
        Group group = Group.get(params.id)
        if (!group) {
            flash.error = message(code: 'group.notfound.message')
            redirect(action: "list")
        } else {
            params.offset = params.offset ?: 0
            params.max = params.max ?: 10
            [group: group, posts: Post.findAll(params, { post -> post.group.id == group.id}), messages: Message.list(max: 5)]
        }
    }

}
