/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

class UserTagLib {

    def springSecurityService

    def followIndicator = { attrs, body ->
        def loggedUser = springSecurityService.currentUser as User
        if ((attrs.user as User)?.following?.contains(loggedUser)) {
            out << attrs.stopFollowText
        } else {
            out << attrs.followText
        }
    }

}
