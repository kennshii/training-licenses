package endava.internship.traininglicensessharing.integration.cucumber.glue.mapper;

import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.application.dto.CredentialDto;

public class CucumberCredentialMapper {

    public static Credential credentialDtoToCredential(CredentialDto credentialDto) {
        return Credential.builder()
                .password(credentialDto.getPassword())
                .username(credentialDto.getUsername())
                .build();
    }

}
