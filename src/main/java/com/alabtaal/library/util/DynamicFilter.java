package com.alabtaal.library.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicFilter {

  private static final Logger LOG = LoggerFactory.getLogger(DynamicFilter.class);

  public static <T> List<T> filter(List<T> list, Map<String, Object> filters) {
    if (MapUtils.isEmpty(filters)) {
      return list;
    }
    return list
        .stream()
        .filter(item -> filters.entrySet()
            .stream()
            .allMatch(entry -> {
              try {
                Field field = item.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);

                Object fieldValue = field.get(item);
                Object filterValue = entry.getValue();

                final boolean isList = filterValue instanceof List;
                if (isList) {
                  final List<?> filterList = (List<?>) filterValue;
                  if (CollectionUtils.isEmpty(filterList)) {
                    return true;
                  } else {
                    if (fieldValue == null) {
                      return false;
                    } else if (filterList.get(0).getClass().getName().equals(fieldValue.getClass().getName())) {
                      return filterList.contains(fieldValue);
                    } else if (fieldValue instanceof UUID) {
                      fieldValue = fieldValue.toString();
                      return filterList.contains(fieldValue);
                    }
                  }
                }

                if (filterValue.getClass().getName().equals(fieldValue.getClass().getName())) {
                  return fieldValue.equals(filterValue);
                } else {
                  String filterValueString = String.valueOf(filterValue);
                  if (fieldValue instanceof Date) {
                    return compareDates((Date) fieldValue, filterValueString);
                  } else if (fieldValue instanceof Number) {
                    return compareNumbers((Number) fieldValue, filterValueString);
                  } else if (fieldValue instanceof String) {
                    return ((String) fieldValue).equalsIgnoreCase(filterValueString);
                  } else {
                    return fieldValue.equals(filterValue);
                  }
                }
              } catch (NoSuchFieldException | IllegalAccessException e) {
                LOG.error(e.getMessage());
                return false;
              }
            }))
        .toList();
  }

  private static boolean compareDates(Date fieldValue, String filterValue) {
    if (filterValue != null) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date filterDate = sdf.parse(filterValue);
        return fieldValue.compareTo(filterDate) == 0;
      } catch (ParseException e) {
        LOG.error("Error parsing date: {}", e.getMessage());
        return false;
      }
    }
    return false;
  }

  private static boolean compareNumbers(Number fieldValue, String filterValue) {
    try {
      return fieldValue.doubleValue() == Double.parseDouble(filterValue);
    } catch (NumberFormatException e) {
      LOG.error("Error parsing number: {}", e.getMessage());
      return false;
    }
  }
}
