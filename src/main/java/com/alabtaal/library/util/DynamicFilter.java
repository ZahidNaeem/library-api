package com.alabtaal.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicFilter {

  private static final Logger LOG = LoggerFactory.getLogger(DynamicFilter.class);
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public static <T> List<T> filter(List<T> list, Map<String, Object> filters) {
    if (MapUtils.isEmpty(filters)) {
      return list;
    }
    List<T> filteredList = new ArrayList<>(list);
    for (T item : list) {
      for (Entry<String, Object> entry : filters.entrySet()) {
        if (!ObjectUtils.isEmpty(entry.getValue()) && !matchesFilter(item, entry.getKey(), entry.getValue())) {
          filteredList.remove(item);
        }
      }
    }
    return filteredList;
  }

  public static <T> boolean matchesFilter(T item, String fieldName, Object filterValue) {
    try {
      Field field = item.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      Object fieldValue = field.get(item);

      return matchesFilter(fieldValue, filterValue);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      LOG.error("Error accessing field '{}': {}", fieldName, e.getMessage());
      return false;
    }
  }

  public static boolean matchesFilter(Object fieldValue, Object filterValue) {
    boolean result;
    if (filterValue instanceof Collection<?> filterValues) {
      final Object firstElement = getFirstElement(filterValues);
      if (firstElement instanceof Map) {
        final Collection<Map<?, ?>> filterValuesMap = castToMapCollection(filterValues);
        result = fieldValue instanceof Collection<?> fieldValues
            ? matchesListOfMapFilterToFieldValues(fieldValues, filterValuesMap)
            : matchesListOfMapFilter(fieldValue, filterValuesMap);
      } else {
        result = fieldValue instanceof Collection<?> fieldValues
            ? matchesListFilterToFieldValues(fieldValues, filterValues)
            : matchesListFilter(fieldValue, filterValues);
      }
    } else if (filterValue instanceof Map<?, ?> filterMap) {
      result = matchesMapFilter(fieldValue, filterMap);
    } else {
      result = matchesSingleFilter(fieldValue, filterValue);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static Collection<Map<?, ?>> castToMapCollection(Collection<?> collection) {
    return (Collection<Map<?, ?>>) collection;
  }

  public static boolean matchesListFilter(Object fieldValue, Collection<?> filterList) {
    if (CollectionUtils.isEmpty(filterList)) {
      return true;
    }
    if (fieldValue == null) {
      return false;
    }

    if (isTypeSame(fieldValue, filterList)) {
      return filterList.contains(fieldValue);
    }

    if (fieldValue instanceof UUID) {
      return filterList.contains(fieldValue.toString());
    }
    for (Object filterValue : filterList) {
      if (compareAsStrings(fieldValue, filterValue)) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchesListFilterToFieldValues(Collection<?> fieldValues, Collection<?> filterList) {
    for (Object element : fieldValues) {
      if (matchesListFilter(element, filterList)) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchesMapFilter(Object fieldValue, Map<?, ?> filterMap) {
    for (Entry<?, ?> entry : filterMap.entrySet()) {
      try {
        final Field field = fieldValue.getClass().getDeclaredField(String.valueOf(entry.getKey()));
        field.setAccessible(true);
        final Object value = field.get(fieldValue);
        if (!compareAsStrings(value, entry.getValue())) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }
    return true;
  }

  public static boolean matchesListOfMapFilter(Object fieldValue, Collection<Map<?, ?>> filterListOfMap) {
    for (Map<?, ?> element : filterListOfMap) {
      if (matchesMapFilter(fieldValue, element)) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchesListOfMapFilterToFieldValues(Collection<?> fieldValues, Collection<Map<?, ?>> filterListOfMap) {
    for (Object fieldValue : fieldValues) {
      if (matchesListOfMapFilter(fieldValue, filterListOfMap)) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchesSingleFilter(Object fieldValue, Object filterValue) {
    if (fieldValue == null) {
      return false;
    }
    return compareAsStrings(fieldValue, filterValue);
  }

  private static boolean compareAsStrings(Object fieldValue, Object filterValue) {
    return String.valueOf(fieldValue).toLowerCase().contains(String.valueOf(filterValue).toLowerCase());
  }

  public static boolean compareDates(Date fieldValue, String filterValue) {
    try {
      Date filterDate = DATE_FORMAT.parse(filterValue);
      return fieldValue.compareTo(filterDate) == 0;
    } catch (ParseException e) {
      LOG.error("Error parsing date '{}': {}", filterValue, e.getMessage());
      return false;
    }
  }

  public static boolean compareNumbers(Number fieldValue, String filterValue) {
    try {
      return fieldValue.doubleValue() == Double.parseDouble(filterValue);
    } catch (NumberFormatException e) {
      LOG.error("Error parsing number '{}': {}", filterValue, e.getMessage());
      return false;
    }
  }

  public static boolean isTypeSame(Object object, Object other) {
    final String objectType = Optional.ofNullable(getGenericTypeName(object)).orElse(object.getClass().getTypeName());
    final String otherType = Optional.ofNullable(getGenericTypeName(other)).orElse(other.getClass().getTypeName());
    return objectType.equals(otherType);
  }

  public static String getGenericTypeName(Object object) {
    final Type genericSuperclass = object.getClass().getGenericSuperclass();
    if (genericSuperclass instanceof ParameterizedType pt) {
      Type[] actualTypeArguments = pt.getActualTypeArguments();
      return actualTypeArguments.length > 0 ? actualTypeArguments[0].getTypeName() : null;
    }
    return null;
  }

  public static Object getFirstElement(Collection<?> collection) {
    return collection
        .stream()
        .findFirst()
        .orElse(null);
  }
}