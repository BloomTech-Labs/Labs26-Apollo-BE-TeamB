package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.exceptions.ResourceFoundException;
import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.models.UserRoles;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findUserById() {
        assertEquals("admin", userService.findUserById(4).getUsername());
    }

    @Test
    public void b_findByNameContaining() {
        assertEquals(1, userService.findByNameContaining("adm").size());
    }

    @Test
    public void c_findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void d_delete() {
        userService.delete(8);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void e_findByName() {
        assertEquals(4, userService.findByName("admin").getUserid());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ea_findByNameException() {
        assertEquals(ResourceNotFoundException.class, userService.findByName("no").getUserid());
    }

    @Test
    public void f_findByOKTAUserName() {
        assertEquals(5, userService.findByOKTAUserName("user1").getUserid());
    }

    @Test
    public void fa_findByOKTAUserNameNewUser() {
        assertEquals("okta@okta.com", userService.findByOKTAUserName("okta@okta.com").getPrimaryemail());
    }

    @Test
    public void g_save() {
        User newUser = new User("tiger", "tiger@school.lambda");
        userService.save(newUser);

        assertEquals("tiger@school.lambda", userService.findByName("tiger").getPrimaryemail());
    }

    @Test
    public void ga_saveUpdate() {
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(), roleService.findRoleById(2)));
        datas.add(new UserRoles(new User(), roleService.findRoleById(3)));
        User user = userService.findByName("user2");
        user.setPrimaryemail("update@update.com");
        user.setRoles(datas);
        userService.save(user);

        assertEquals("update@update.com", userService.findByName("user2").getPrimaryemail());
    }

    @Transactional
    @WithUserDetails("user2")
    @Test
    public void h_update() {
        User user2 = new User("user2", "update@user.com");
        userService.update(user2, 6);

        assertEquals("update@user.com", userService.findByName("user2").getPrimaryemail());
    }

    @Transactional
    @WithUserDetails("user3")
    @Test(expected = ResourceNotFoundException.class)
    public void ha_updateNotAuthorized() {
        User user2 = new User("user2", "update@user.com");
        userService.update(user2, 6);

        assertEquals(ResourceNotFoundException.class, userService.findByName("user2").getPrimaryemail());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ia_deleteUserRoleRoleNotFound() {
        userService.deleteUserRole(7, 1);
    }

    @Test(expected = ResourceFoundException.class)
    public void ib_addUserRoleRoleFound() {
        userService.addUserRole(4, 1);
    }

}