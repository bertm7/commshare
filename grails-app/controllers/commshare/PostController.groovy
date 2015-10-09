/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import grails.plugins.springsecurity.Secured

class PostController {

    def scaffold = true

    static allowedMethods = [save: 'POST']

    def springSecurityService

    def index() {}

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'IS_AUTHENTICATED_REMEMBERED'])
    def save() {
        Post post = new Post(params)
        User user = springSecurityService.getCurrentUser() as User;
        log.debug('Post creator: ' + user);
        post.user = user
        if (post.save(flush: true)) {
            flash.message = message(code: 'post.createdsuccess.message')
        } else {
            flash.error = message(code: 'post.createdfail.message')
        }
        new Message(text: "@${post.user.username} has written \"${shortenText(post.text)}\" in community #${post.group.name}.").save()
        // post can be saved only within a group, so redirect to that group
        redirect(controller: "group", action: "show", params: [id: post?.group?.id])
    }

    private static def shortenText(text) {
        if (text.length() <= 200) {
            return text
        } else {
            text.substring(0, 200) + "..."
        }
    }
}
