import commshare.Gender
import commshare.SecRole
import commshare.SecUserSecRole
import commshare.User

class BootStrap {

    def init = { servletContext ->
        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)

        def admin
        if (!(admin = User.findByUsername('Admin'))) {
            admin = new User(username: "Admin", email: "reptst@gmail.com", password: "c0mmsh@re", enabled: true, country: "Czech republic", town: "Prague", yearBorn: 1983, gender: Gender.MALE).save(failOnError: true)
        }
        if (!admin?.authorities?.contains(adminRole)) {
                SecUserSecRole.create admin, adminRole
        }

    }

    def destroy = {
    }
}
