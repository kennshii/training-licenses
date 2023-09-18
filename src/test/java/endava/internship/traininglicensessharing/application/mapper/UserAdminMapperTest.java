package endava.internship.traininglicensessharing.application.mapper;

import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER;
import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER_ADMIN_DTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;

class UserAdminMapperTest {

    UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = UserMapper.INSTANCE;
    }

    @Test
    void itShouldMapUserToUserAdminDto() {
        UserAdminDto user_Admin_Dto = mapper.userToUserAdminDto(USER);

        assertThat(user_Admin_Dto).isEqualTo(USER_ADMIN_DTO);
    }
}
