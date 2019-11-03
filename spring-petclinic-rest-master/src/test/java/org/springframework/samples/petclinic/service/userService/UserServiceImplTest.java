package org.springframework.samples.petclinic.service.userService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveUserFirstPrimeTest() throws Exception {

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(true);
        user.addRole("OWNER_ADMIN");
        user.addRole("ROLE_EDITOR");

        userService.saveUser(user);
        assertThat(user.getRoles().parallelStream().allMatch(role -> role.getName().startsWith("ROLE_")), is(true));
        assertThat(user.getRoles().parallelStream().allMatch(role -> role.getUser() != null), is(true));
    }

    @Test
    public void saveUserThirdPrimeTest() throws Exception {

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(true);
        Role r = new Role();
        r.setName("ROLE_A");
        r.setUser(user);
        Set<Role> set = new HashSet<>();
        set.add(r);
        user.setRoles(set);

        userService.saveUser(user);
        assertThat(user.getRoles().parallelStream().allMatch(role -> role.getName().startsWith("ROLE_")), is(true));
        assertThat(user.getRoles().parallelStream().allMatch(role -> role.getUser() != null), is(true));
    }
    @Test(expected = Exception.class)
    public void saveUserPrimeTest() throws Exception {

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(true);
        Set<Role> set = new HashSet<>();
        user.setRoles(set);

        userService.saveUser(user);
        }
    @Test(expected = Exception.class)
    public void saveUserSecondPrimeTest() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(true);
        userService.saveUser(user);
    }
}
