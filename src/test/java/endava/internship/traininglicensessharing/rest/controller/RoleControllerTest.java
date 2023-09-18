package endava.internship.traininglicensessharing.rest.controller;

import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE_DTO_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import endava.internship.traininglicensessharing.application.facade.RoleFacade;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {RoleControllerImpl.class})
class RoleControllerTest {
    @MockBean
    private RoleFacade roleFacade;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String URL = "/roles";

    @Test
    void itShouldReturnAllRoles() throws Exception {
        when(roleFacade.getAllRoles()).thenReturn(ROLE_DTO_LIST);
        String returned = mockMvc.perform(get(URL))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expected = objectMapper.writeValueAsString(ROLE_DTO_LIST);

        assertThat(returned).isEqualTo(expected);
    }
}
