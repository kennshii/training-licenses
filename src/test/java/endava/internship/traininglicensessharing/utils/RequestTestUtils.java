package endava.internship.traininglicensessharing.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;

import endava.internship.traininglicensessharing.application.dto.RequestDtoRequest;
import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;

public class RequestTestUtils {

    public static final Integer REQUEST_ID = 1;

    private static final DateTimeFormatter requestedOnFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter startOfUseFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
    public static final String REQUESTED_ON = LocalDateTime.now().format(requestedOnFormatter);
    public static final String START_OF_USE = LocalDate.now().format(startOfUseFormatter);

    public static final Request REQUEST = Request.builder()
            .id(REQUEST_ID)
            .requestedOn(LocalDateTime.parse(REQUESTED_ON, requestedOnFormatter))
            .user(UserTestUtils.USER)
            .license(LicenseTestUtils.LICENSE)
            .startOfUse(LocalDate.parse(START_OF_USE, startOfUseFormatter))
            .status(RequestStatus.PENDING)
            .build();

    public static final Request EDITED_REQUEST = Request.builder()
            .id(REQUEST_ID)
            .requestedOn(LocalDateTime.parse(REQUESTED_ON, requestedOnFormatter))
            .user(UserTestUtils.USER)
            .license(LicenseTestUtils.LICENSE)
            .startOfUse(LocalDate.parse(START_OF_USE, startOfUseFormatter))
            .status(RequestStatus.APPROVED)
            .build();

    public static final RequestDtoRequest REQUEST_DTO_REQUEST = RequestDtoRequest.builder()
            .requestIds(Collections.singletonList(REQUEST.getId()))
            .status(RequestStatus.APPROVED.toString())
            .build();

    public static final RequestDtoResponse EDITED_REQUEST_DTO_RESPONSE = RequestDtoResponse.builder()
            .app(REQUEST.getLicense().getName())
            .requestedOn(REQUESTED_ON)
            .requestId(REQUEST_ID)
            .user(REQUEST.getUser().getFirstName() + " " + REQUEST.getUser().getLastName())
            .discipline(REQUEST.getUser().getWorkplace().getDiscipline().getName())
            .status(RequestStatus.APPROVED.toString())
            .startOfUse(START_OF_USE)
            .build();

    public static final RequestDtoResponse REQUEST_DTO_RESPONSE = RequestDtoResponse.builder()
            .app(REQUEST.getLicense().getName())
            .requestedOn(REQUESTED_ON)
            .requestId(REQUEST_ID)
            .user(REQUEST.getUser().getFirstName() + " " + REQUEST.getUser().getLastName())
            .discipline(REQUEST.getUser().getWorkplace().getDiscipline().getName())
            .status(REQUEST.getStatus().toString())
            .startOfUse(START_OF_USE)
            .build();
}
