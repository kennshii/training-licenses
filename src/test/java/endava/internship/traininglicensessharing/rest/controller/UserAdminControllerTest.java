package endava.internship.traininglicensessharing.rest.controller;

import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER_DISABLE_DTO;
import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER_LIST;
import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER_LIST_AND_ROLE_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;
import endava.internship.traininglicensessharing.application.facade.UserFacade;
import endava.internship.traininglicensessharing.application.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserAdminControllerTest {
    @MockBean
    private UserFacade userFacade;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void itShouldReturnAllUsers() throws Exception {
        List<UserAdminDto> expectedUserList = USER_LIST.stream()
                .map(userMapper::userToUserAdminDto)
                .toList();

        String expectedJson = objectMapper.writeValueAsString(expectedUserList);

        when(userFacade.getAllUsersAdmin()).thenReturn(expectedUserList);

        String returnedJson = mockMvc.perform(get("/users"))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(returnedJson).isEqualTo(expectedJson);
    }

    @Test
    void itShouldReturnOkStatusWhenDisableExistentUser() throws Exception {
        when(userFacade.disableUser(any())).thenReturn(true);

        assertThat(mockMvc.perform(put("/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_DISABLE_DTO)))
                .andReturn()
                .getResponse()
                .getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void itShouldReturnNotFoundStatusWhenDisableNonexistentUser() throws Exception {
        when(userFacade.disableUser(USER_DISABLE_DTO.getUserIdList())).thenReturn(false);

        assertThat(mockMvc.perform(put("/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_DISABLE_DTO)))
                .andReturn()
                .getResponse()
                .getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void isShouldReturnOkStatusWhenExistentUserAndRole() throws Exception {

        when(userFacade.modifyUserRole(USER_LIST_AND_ROLE_DTO.getUserIdList(), USER_LIST_AND_ROLE_DTO.getRoleId())).thenReturn(true);

        assertThat(mockMvc.perform(put("/users/roles")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_LIST_AND_ROLE_DTO)))
                .andReturn()
                .getResponse()
                .getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void isShouldReturnNotFoundStatusWhenNonexistentUserOrRole() throws Exception {

        when(userFacade.modifyUserRole(USER_LIST_AND_ROLE_DTO.getUserIdList(), USER_LIST_AND_ROLE_DTO.getRoleId())).thenReturn(false);

        assertThat(mockMvc.perform(put("/users/roles")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_LIST_AND_ROLE_DTO)))
                .andReturn()
                .getResponse()
                .getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

}
