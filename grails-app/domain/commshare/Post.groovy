/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

class Post {

    String text
    Date dateCreated

    static belongsTo = [user: User, group: Group]

    static constraints = {
        text(blank: false, maxSize: 4000)
    }

    static mapping = {
        autoTimestamp true
        sort dateCreated: "desc"
        table "`POSTS`"
    }

    def String toString() {
        text
    }

}
