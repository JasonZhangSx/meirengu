package com.meirengu.admin.util.check;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/7 15:08.
 */
public class ValidateDigitHandler implements Handler {
    private static final Logger LOGGER = Logger.getLogger(ValidateDigitHandler.class.getName());

    public void validate(AnnotationValidable validatedObj, Field field)
            throws ValidateException {
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.entering(this.getClass().getName(), "validate()");
        }
        if (field.isAnnotationPresent(ValidateDigit.class)) {
            checkDigit(validatedObj, field);
        }
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.exiting(this.getClass().getName(), "validate()");
        }
    }

    /**
     * validate the String is digit only
     *
     * @param filter
     *            filter validated object
     * @param field
     *            validated field or property
     * @throws ValidateException
     */
    private void checkDigit(AnnotationValidable filter, Field field)
            throws ValidateException {
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.entering(this.getClass().getName(), "checkDigit()");
        }
        ValidateDigit annotation = field.getAnnotation(ValidateDigit.class);
        String message = field.getName()+":"+annotation.message();
        String value = null;
        try {
            value = String.valueOf(GetFiledValue.getField(filter, field.getName()));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "Get field value or cast value error. Error message: "
                            + ex.getMessage(), ex);
            throw new ValidateException(ex.getMessage(), ex);
        }
        String patternStr = "\\d*";
        Pattern pattern = Pattern.compile(patternStr);
        if (value != null && !"".equals(value) && !"null".equals(value)) {
            if (!pattern.matcher(value).matches()) {
                LOGGER.log(Level.SEVERE,
                        "Validate fail. Error message: validate value is:"
                                + value);

            }
        }
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.exiting(this.getClass().getName(), "checkDigit()");
        }
    }
}