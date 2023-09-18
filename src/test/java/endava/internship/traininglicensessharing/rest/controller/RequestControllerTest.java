package endava.internship.traininglicensessharing.rest.controller;

import static endava.internship.traininglicensessharing.utils.RequestTestUtils.EDITED_REQUEST_DTO_RESPONSE;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST_DTO_REQUEST;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST_DTO_RESPONSE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import endava.internship.traininglicensessharing.application.facade.RequestFacade;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {RequestControllerImpl.class})
class RequestControllerTest {

    private static final String URI = "/requests";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestFacade requestFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void itShouldEditRequests() throws Exception {
        String jsonRequestRequest = objectMapper.writeValueAsString(REQUEST_DTO_REQUEST);
        String jsonRequestResponse = objectMapper.writeValueAsString(List.of(EDITED_REQUEST_DTO_RESPONSE));

        when(requestFacade.editRequest(any(), any())).thenReturn(List.of(EDITED_REQUEST_DTO_RESPONSE));

        String response = mockMvc.perform(put(URI)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequestRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(jsonRequestResponse);
    }

    @Test
    void itShouldGetAllRequests() throws Exception {
        String jsonRequestResponse = objectMapper.writeValueAsString(List.of(REQUEST_DTO_RESPONSE));

        when(requestFacade.getAllRequests()).thenReturn(List.of(REQUEST_DTO_RESPONSE));

        String response = mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(jsonRequestResponse);
    }

    @Test
    void itShouldDeleteRequests() throws Exception {
        List<Integer> requestIdsToDelete = Arrays.asList(1, 2, 3);

        mockMvc.perform(delete(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestIdsToDelete)))
                .andExpect(status().isOk());

        verify(requestFacade).deleteRequest(requestIdsToDelete);
    }
}
