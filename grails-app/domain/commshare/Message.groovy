/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

/** Entity where activities are stored and they are displayed on their panel */
class Message {

    String text
    Date dateCreated

    static constraints = {
        text(blank: false, maxSize:  4000)
    }

    static mapping = {
        sort dateCreated: "desc"
        table "`MESSAGES`"
    }
}
