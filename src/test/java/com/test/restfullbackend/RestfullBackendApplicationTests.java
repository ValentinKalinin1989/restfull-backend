package com.test.restfullbackend;

import com.test.restfullbackend.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-and-role-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-and-role-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RestfullBackendApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    public void whenGetAllUsers() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"name\":\"name1\",\"login\":\"login1\"," +
                        "\"password\":\"pass1\"},{\"name\":\"name2\",\"login\":\"login2\",\"password\":\"pass2\"}," +
                        "{\"name\":\"name3\",\"login\":\"login3\",\"password\":\"pass3\"}")));
    }

    @Test
    public void whenGetUserWithLogin3() throws Exception {
        this.mockMvc.perform(get("/users/login3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"name\":\"name3\",\"login\":\"login3\"," +
                        "\"password\":\"pass3\"," +
                        "\"roles\":[{\"id\":3,\"name\":\"analyst\"},{\"id\":4,\"name\":\"operator\"}]}")));
    }

    @Test
    public void whenDelUserWithLogin3() throws Exception {
        this.mockMvc.perform(delete("/users/login3"))
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/users/login3"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenCreateUserWithLogin4WithTrueParam() throws Exception {
        this.mockMvc.perform(post("/users")
                .header("Content-Type", "application/json")
                .content("{\"name\":\"name4\",\"login\":\"login4\"" +
                        ",\"password\":\"pass4\"," +
                        "\"roles\":[{\"id\":3,\"name\":\"analyst\"},{\"id\":4,\"name\":\"operator\"}]}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void whenCreateUserWithLogin4WithErrorParam() throws Exception {
        this.mockMvc.perform(post("/users")
                .header("Content-Type", "application/json")
                .content("{\"name\":\"\",\"login\":\"\"" +
                        ",\"password\":\"p4\"," +
                        "\"roles\":[{\"id\":3,\"name\":\"analyst\"},{\"id\":4,\"name\":\"operator\"}]}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("{\"success\":false," +
                        "\"errors\":[\"name - is EMPTY\"," +
                        "\"login - is EMPTY\",\"password - must have one uppercase letter and number\"]}")));
    }

    @Test
    public void whenTryUpdateUserWithLogin1WithTrueParam() throws Exception {
        this.mockMvc.perform(put("/users/login1")
                .header("Content-Type", "application/json")
                .content("{\"name\":\"updated_name1\",\"login\":\"login1\"" +
                        ",\"password\":\"updated_pass1\"," +
                        "\"roles\":[{\"id\":3,\"name\":\"analyst\"}]}"))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/users/login1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"name\":\"updated_name1\",\"login\":\"login1\"," +
                        "\"password\":\"updated_pass1\",\"roles\":[{\"id\":3,\"name\":\"analyst\"}]}")));
    }

    @Test
    public void whenTryUpdateUserWithLogin2WithErrorParam() throws Exception {
        this.mockMvc.perform(put("/users/login2")
                .header("Content-Type", "application/json")
                .content("{\"name\":\"\",\"login\":\"  \"" +
                        ",\"password\":\"1\"," +
                        "\"roles\":[{\"id\":3,\"name\":\"analyst\"}]}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("{\"success\":false," +
                        "\"errors\":[\"name - is EMPTY\"," +
                        "\"login - is BLANK\",\"password - must have one uppercase letter and number\"]}")));

        this.mockMvc.perform(get("/users/login2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"name\":\"name2\"," +
                        "\"login\":\"login2\",\"password\":\"pass2\",\"roles\":[{\"id\":2,\"name\":\"user\"}]}")));
    }
}
