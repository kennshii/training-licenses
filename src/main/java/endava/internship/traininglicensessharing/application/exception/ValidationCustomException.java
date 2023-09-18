package endava.internship.traininglicensessharing.application.exception;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.validation.Errors;

public class ValidationCustomException extends RuntimeException {

    private final Map<String, String> errors = new TreeMap<>();

    public ValidationCustomException(Map<String, String> errors) {
        this.errors.putAll(errors);
    }

    public ValidationCustomException(Errors errors) {
        this.errors.putAll(errorsToMap(errors));
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private Map<String, String> errorsToMap(Errors errors) {
        if (errors.hasErrors()) {
            Map<String, String> errorsMap = new TreeMap<>();
            errors.getFieldErrors().forEach(error -> {
                if (!errorsMap.containsKey(error.getField())) {
                    errorsMap.put(error.getField(), error.getDefaultMessage());
                }
            });
            return errorsMap;
        }

        return new TreeMap<>();
    }
}
