/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import uk.co.desirableobjects.ajaxuploader.AjaxUploaderService
import uk.co.desirableobjects.ajaxuploader.exception.FileUploadException

import javax.activation.MimetypesFileTypeMap
import javax.servlet.http.HttpServletRequest

class UserController {

    def springSecurityService

    def servletContext  // Injected by Spring

    final SESSION_AVATAR = "uploaded_avatar"

    static allowedMethods = [update: "POST"]
    AjaxUploaderService ajaxUploaderService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        redirect(action: "list")
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def list() {
        [users: User.list(), messages: Message.list(max: 5)]
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def show() {
        User user = User.get(params.id)
        if (!user) {
            flash.error = message(code: 'user.notfound.message')
            redirect(action: "list")
        } else {
            params.offset = params.offset ?: 0
            params.max = params.max ?: 10
            [user: user, loggedUserId: springSecurityService.currentUser?.id, posts: Post.findAll(params, { post -> post.user.id == user.id}), messages: Message.list(max: 5)]
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'IS_AUTHENTICATED_REMEMBERED'])
    def followToggle() {
        User user = User.get(params.id)
        User loggedUser = springSecurityService.currentUser as User
        if (!user) {
            render "User not found!"
        }
        if (user?.following?.contains(loggedUser)) {
            user.following.remove(loggedUser)
            render "You are no longer following ${user.username}"
        } else {
            user.addToFollowing(loggedUser)
            new Message(text: "@${loggedUser.username} now follows @${user.username}.").save()
            render "You are now following ${user.username}"
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'IS_AUTHENTICATED_REMEMBERED'])
    def update() {
        def user = User.get(params.id)
        if (!user) {
            flash.message = message(code: 'user.notfound.message')
            redirect(action: "list")
            return
        }

        if (params.version) { // object locked?
            def version = params.version.toLong()
            if (user.version > version) {
                flash.message = message(code: 'user.save.error')
                redirect(action: "show", id: user.id)
                return
            }
        }

        user.properties = params

        if (session[SESSION_AVATAR]) {       // was avatar uploaded via the uploadAvatar ajax action?
            File avatar = new File((String) session[SESSION_AVATAR])
            user.avatar = avatar.getBytes()
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap()
            user.avatarType = mimeTypesMap.getContentType((String) session[SESSION_AVATAR]);
            session[SESSION_AVATAR] = null
        }

        if (!user.save(flush: true)) {
            flash.error = message(code: 'user.save.error')
        } else {
            flash.message = message(code: 'user.savesuccess.message')
        }

        new Message(text: "@${user.username} has updated " + (user.gender == Gender.MALE ? "his" : "her") + " profile.").save()

        redirect(action: "show", id: user.id)
    }

    /** upload user avatar to temporary directory only */
    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'IS_AUTHENTICATED_REMEMBERED'])
    def uploadAvatar() {
        try {
            File dest = File.createTempFile("${params.userId}_" + System.currentTimeMillis(),".jpg")
            session[SESSION_AVATAR] = dest.path
            InputStream inputStream = selectInputStream(request)
            ajaxUploaderService.upload(inputStream, dest)
            return render(text: [success:true] as JSON, contentType:'text/json')
        } catch (FileUploadException e) {
            log.error("Failed to upload file.", e)
            return render(text: [success:false] as JSON, contentType:'text/json')
        }
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def viewAvatar() {
        def avatarFile
        avatarFile = session[SESSION_AVATAR] != null ? (new File((String) session[SESSION_AVATAR]))?.bytes : null
        def user = User.get(params.id)
        def avatarContentType = 'image/jpeg'
        if (!avatarFile || !params.temporary) {
            avatarFile = user?.avatar
            avatarContentType = user?.avatarType
        }
        if (!avatarFile) {
            avatarFile = ((!user.gender || user.gender == Gender.MALE) ? servletContext.getResource("/images/users/default_man.jpg")?.bytes : servletContext.getResource("/images/users/default_woman.jpg")?.bytes)
        }
        response.setHeader("Content-disposition", "attachment; filename=avatar.jpg")
        response.contentType = avatarContentType ?:  'image/jpeg'
        response.outputStream << avatarFile
        response.outputStream.flush()
    }

    private static InputStream selectInputStream(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
            return uploadedFile.inputStream
        }
        return request.inputStream
    }

}
