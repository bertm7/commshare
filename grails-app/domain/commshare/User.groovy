/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import org.codehaus.groovy.grails.orm.hibernate.cfg.IdentityEnumType

class User extends SecUser {

    String email
    String country
    String town
    Gender gender
    String info
    Integer yearBorn
    Date dateCreated
    byte[] avatar
    String avatarType

    static hasMany = [posts: Post, groupsOwning: Group, following: User]

    static constraints = {
        email(blank: false, maxSize: 50, email: true)
        country(nullable: true, maxSize: 60)
        town(nullable: true, maxSize: 60)
        gender(nullable: true)
        info(nullable: true, maxSize: 4000)
        yearBorn(nullable: true)
        avatar(nullable: true, maxSize: 16000)
        avatarType(nullable: true)
    }

    static mapping = {
        autoTimestamp true
        gender(type: IdentityEnumType)
        table "`USERS`"
    }

    def String toString() {
        username
    }

}
