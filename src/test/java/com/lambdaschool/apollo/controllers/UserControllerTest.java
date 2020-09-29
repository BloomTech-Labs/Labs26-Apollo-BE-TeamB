package com.lambdaschool.apollo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.apollo.models.Role;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.models.UserRoles;
import com.lambdaschool.apollo.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @Before
    public void setUp() throws Exception {
        userList = new ArrayList<>();

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1.setRoleid(1); // id 1
        r2.setRoleid(2); // id 2
        r3.setRoleid(3); // id 3

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
        admins.add(new UserRoles(new User(), r3));
        User u1 = new User("admin", "admin@lambdaschool.local", admins);
        userList.add(u1); // id 4

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(), r3));
        datas.add(new UserRoles(new User(), r2));
        User u2 = new User("user1", "user1@user.com", datas);
        userList.add(u2); // id 5

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("user2", "user2@user.com", users);
        userList.add(u3); // id 6

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("user3", "user3@user.com", users);
        userList.add(u4); // id 7

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("user4", "user4@user.com", users);
        userList.add(u5); // id 8

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllUsers() throws Exception {
        String apiUrl = "/users/users";

        Mockito.when(userService.findAll())
                .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult result = mockMvc.perform(rb)
                .andReturn(); // this could throw an exception
        String actual = result.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + expected);
        System.out.println("Actual: " + actual);

        assertEquals("Rest API Returns List", expected, actual);
    }

    @Test
    public void getUserById() throws Exception {

    }

    @Test
    public void getUserByName() throws Exception {
    }

    @Test
    public void getUserLikeName() throws Exception {
    }

    @Test
    public void addNewUser() throws Exception {
    }

    @Test
    public void updateFullUser() throws Exception {
    }

    @Test
    public void updateUser() throws Exception {
    }

    @Test
    public void deleteUserById() throws Exception {
    }

    @Test
    public void deleteUserRoleByIds() throws Exception {
    }

    @Test
    public void postUserRoleByIds() throws Exception {
    }

    @Test
    public void getCurrentUserInfo() throws Exception {
    }

    @Test
    public void helloWorld() throws Exception {
    }
}