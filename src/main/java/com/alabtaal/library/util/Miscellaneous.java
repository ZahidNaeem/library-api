package com.alabtaal.library.util;

import com.alabtaal.library.exception.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
//@RequiredArgsConstructor
public class Miscellaneous {

  private static final Logger LOG = LoggerFactory.getLogger(Miscellaneous.class);
  private static Validator validator;

  public Miscellaneous(final Validator validator) {
    Miscellaneous.validator = validator;
  }

//  public static Exception getNestedException(final Exception rootException) {
//    if (rootException.getCause() == null) {
//      LOG.info("Last Exception: {}", rootException.getMessage());
//      return rootException;
//    } else {
//      final Exception cause = (Exception) rootException.getCause();
//      LOG.info("{} Exception Cause: {}", rootException.getClass().getName(), cause.getMessage());
//      return getNestedException(cause);
//    }
//  }

  public static Exception getRootCause(final Exception rootException) {
    return (Exception) ExceptionUtils.getRootCause(rootException);
  }

  public static String getSqlExceptionMessage(final DataIntegrityViolationException exception) {
    if (exception.getCause() instanceof ConstraintViolationException) {
      final ConstraintViolationException cause = (ConstraintViolationException) exception.getCause();
      LOG.debug(cause.getConstraintName());
      return cause.getSQLException().getMessage();
    } else if (exception.getCause() instanceof DataException) {
      final DataException cause = (DataException) exception.getCause();
      LOG.debug(cause.getSQLState());
      return cause.getSQLException().getMessage();
    } else {
      return exception.getCause().getMessage();
    }
  }

  public static ResourceBundle getResourceBundle(final String bundle) {
    return ResourceBundle.getBundle(bundle);
  }

  public static String getResourceMessage(final ResourceBundle resourceBundle, final String key) {
    return resourceBundle.getString(key);
  }

  public static String getResourceMessage(final String resourceBundle, final String key) {
    return getResourceMessage(ResourceBundle.getBundle(resourceBundle), key);
  }

  public static <T> void constraintViolation(final T clazz) throws BadRequestException {
    final Set<ConstraintViolation<T>> constraintViolations = validator.validate(clazz);
    if (!CollectionUtils.isEmpty(constraintViolations)) {
      final String violations = constraintViolations.stream().map(ConstraintViolation::getMessage)
          .collect(Collectors.joining("\n"));
      throw new BadRequestException(violations);
    }
  }

  public static UUID generateUUID(final String uuid) throws BadRequestException {
    try {
      return UUID.fromString(uuid);
    } catch (final Exception e) {
      logException(LOG, e);
      throw new BadRequestException("UUID Error: " + e.getMessage());
    }
  }

  public static <T> boolean fieldExists(final Class<T> clazz, final String fieldName) {
    return FieldUtils.getField(clazz, fieldName, true) != null;
  }

  public static <T> Pageable handlePaginationValues(
      Integer pageNumber,
      Integer pageSize,
      String sortBy,
      final String sortDirection,
      final Class<T> clazz) throws BadRequestException {
    pageNumber = pageNumber == null || pageNumber <= 0 ? 0 : pageNumber;
    pageSize = pageSize == null ? 10 : pageSize;
    sortBy = sortBy == null ? "creationDate" : sortBy;
    final boolean fieldExists = fieldExists(clazz, sortBy);
    if (!fieldExists) {
      throw new BadRequestException("Field '" + sortBy + "' (used in sort by) does not exist.");
    }
    final Direction direction =
        sortDirection == null ? Direction.DESC : Direction.fromString(sortDirection);
    final Sort sort = Sort.by(direction, sortBy);
    return PageRequest.of(pageNumber, pageSize, sort);
  }

  public static void logException(final Logger logger, final Exception e) {
    logger.error("Exception:\n{}", getRootCause(e));
  }

  public static List<String> splitStringByNumber(final String text, final int number) {
    final List<String> strings = new ArrayList<>();
    if (StringUtils.isNotBlank(text)) {
      int start, end = text.length();
      while (end > 0) {
        start = Math.max(0, end - number);
        System.out.println("Text: " + text.substring(start, end));
        strings.add(0, text.substring(start, end));
        end = start;
      }
    }
    return strings;
  }

  public static String thousandSeparator(final Double number) {
    NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
    formatter.setMaximumFractionDigits(2);
    return formatter.format(number);
  }

  public static String thousandSeparator(final String number) throws BadRequestException {
    String result = "";
    if (StringUtils.isNotBlank(number)) {
      final String[] numbers = number.split("\\.");
      StringBuilder fraction = new StringBuilder();
      for (int i = 0; i < numbers.length; i++) {
        numbers[i] = numbers[i].replaceAll("\\D", "");
        if (i > 0) {
          fraction.append(numbers[i]);
        }
      }
      if (StringUtils.isNotBlank(numbers[0])) {
        result = thousandSeparator(Double.parseDouble(numbers[0]));
      }
      if (StringUtils.isNotBlank(fraction) || number.contains(".")) {
        result += "." + fraction;
      }
    }
    return result;
  }

  public static Double nvl(String valueToCheck, final String defaultValue) {
    if (StringUtils.isNotBlank(valueToCheck)) {
      valueToCheck = valueToCheck.replaceAll("[^\\d.]", "");
      return Double.parseDouble(valueToCheck);
    }
    return Double.parseDouble(defaultValue);
  }

}
