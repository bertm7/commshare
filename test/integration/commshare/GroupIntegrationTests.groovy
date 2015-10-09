/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

class GroupIntegrationTests {

    User user

    @Before
    void setUp() {
        user = new User(username: "User417345735", email: "a@a.com", password: 'secret', gender: Gender.MALE)
        assertNotNull user.save()
        assertNotNull User.get(user.id)
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testCreateEmptyGroup() {
        def group = new Group(name: "Group4848154864")
        assertFalse group.validate()
        assertEquals "nullable", group.errors["creator"].code
    }

    @Test
    void testUserCreatedGroup() {
        def group = new Group(name: "Group4545252863")
        user.addToGroupsOwning(group)
        assertEquals(1, User.get(user.id).groupsOwning.size())
    }

    @Test
    void testCreateGroupWithPosts() {
        def group = new Group(name: "Group1828154851")
        user.addToGroupsOwning(group)
        assertEquals(1, User.get(user.id).groupsOwning.size())
        def post1 = new Post(text: "Post1")
        def post2 = new Post(text: "Post2")
        group.addToPosts(post1)
        group.addToPosts(post2)
        user.addToPosts(post1)
        user.addToPosts(post2)
        assertNotNull user.save()
        assertEquals(2, User.get(user.id)?.groupsOwning?.min()?.posts?.size())
    }

}
