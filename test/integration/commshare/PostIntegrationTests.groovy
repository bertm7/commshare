/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

class PostIntegrationTests {

    Group group
    User user

    @Before
    void setUp() {
        user = new User(username: "User113542735", email: "a@a.com", password:  'secret', gender: Gender.MALE)
        assertNotNull user.save()
        assertNotNull User.get(user.id)
        group = new Group(name: "Group217345735")
        user.addToGroupsOwning(group)
        assertNotNull user.save()
        assertEquals 1, User.get(user.id).groupsOwning.size()

    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testCreateEmptyPost() {
        def post = new Post(text: "Post348957347589")
        assertFalse post.validate()
        assertEquals "nullable", post.errors["user"].code
        assertEquals "nullable", post.errors["group"].code
    }

    @Test
    void testCreateValidPost() {
        def post = new Post(text: "Post148951347382")
        group.addToPosts(post)
        user.addToPosts(post)
        user.save()
        assertNotNull Group.findByName("Group217345735")?.posts?.min()
        assertEquals post.text, Group.findByName("Group217345735")?.posts?.min()?.text
    }

}
