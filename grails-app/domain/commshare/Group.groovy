/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

class Group {

    String name
    String description
    Date dateCreated

    static hasMany = [posts: Post]

    static belongsTo = [ creator: User ]

    static constraints = {
        name(unique: true, blank: false, maxSize: 60)
        description(nullable: true, maxSize: 5000)
    }

    static mapping = {
        autoTimestamp true
        table "`GROUPS`"
        sort dateCreated: "desc"
        posts sort: "dateCreated", order: "desc"
    }

    def String toString() {
        name
    }
}
