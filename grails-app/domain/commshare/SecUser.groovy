/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

class SecUser {

    def springSecurityService

    static transients = ["springSecurityService"]

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {
        username blank: false, unique: true, maxSize: 20
        password blank: false, maxSize: 200
    }

    static mapping = {
        password column: '`password`'
    }

    Set<SecRole> getAuthorities() {
        SecUserSecRole.findAllBySecUser(this).collect { it.secRole } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password);
    }

    def String toString() {
        username
    }


}
