package com.alabtaal.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private static final Map<FieldCacheKey, Field> fieldCache = new HashMap<>();

  public static <T> List<T> filter(List<T> list, Map<String, Object> filters) {
    if (MapUtils.isEmpty(filters)) {
      return list;
    }
    return list
        .stream()
        .filter(item -> filters.entrySet()
            .stream()
            .filter(entry -> !ObjectUtils.isEmpty(entry.getValue()))
            .allMatch(entry -> matchesFilter(item, entry.getKey(), entry.getValue())))
        .toList();
  }

  public static <T> boolean matchesFilter(T item, String fieldName, Object filterValue) {
    try {
      Field field = getField(item.getClass(), fieldName);
      field.setAccessible(true);
      Object fieldValue = field.get(item);

      return Optional.ofNullable(filterValue)
          .map(value -> matchesFilter(fieldValue, value))
          .orElse(true);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      LOG.error("Error accessing field '{}': {}", fieldName, e.getMessage());
      return false;
    }
  }

  public static boolean matchesFilter(Object fieldValue, Object filterValue) {
    if (filterValue instanceof Collection<?> filterValues) {
      final Object firstElement = getFirstElement(filterValues);
      if (firstElement instanceof Map) {
        final Collection<Map<?, ?>> filterValuesMap = castToMapCollection(filterValues);
        return fieldValue instanceof Collection<?> fieldValues
            ? matchesListOfMapFilterToFieldValues(fieldValues, filterValuesMap)
            : matchesListOfMapFilter(fieldValue, filterValuesMap);
      } else {
        return fieldValue instanceof Collection<?> fieldValues
            ? matchesListFilterToFieldValues(fieldValues, filterValues)
            : matchesListFilter(fieldValue, filterValues);
      }
    } else if (filterValue instanceof Map<?, ?> filterMap) {
      return matchesMapFilter(fieldValue, filterMap);
    } else {
      return matchesSingleFilter(fieldValue, filterValue);
    }
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
    return filterList.stream()
        .anyMatch(filterValue -> compareAsStrings(fieldValue, filterValue));
  }

  public static boolean matchesListFilterToFieldValues(Collection<?> fieldValues, Collection<?> filterList) {
    return fieldValues
        .stream()
        .anyMatch(element -> matchesListFilter(element, filterList));
  }

  public static boolean matchesMapFilter(Object fieldValue, Map<?, ?> filterMap) {
    return filterMap
        .entrySet()
        .stream()
        .allMatch((entry) -> {
          try {
            final Field field = getField(fieldValue.getClass(), String.valueOf(entry.getKey()));
            field.setAccessible(true);
            final Object value = field.get(fieldValue);
            return compareAsStrings(value, entry.getValue());
          } catch (Exception e) {
            return false;
          }
        });
  }

  public static boolean matchesListOfMapFilter(Object fieldValue, Collection<Map<?, ?>> filterListOfMap) {
    return filterListOfMap
        .stream()
        .anyMatch((element) -> matchesMapFilter(fieldValue, element));
  }

  public static boolean matchesListOfMapFilterToFieldValues(Collection<?> fieldValues, Collection<Map<?, ?>> filterListOfMap) {
    return fieldValues
        .stream()
        .anyMatch(fieldValue ->
            matchesListOfMapFilter(fieldValue, filterListOfMap));
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

  public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
    FieldCacheKey key = new FieldCacheKey(clazz, fieldName);
    if (!fieldCache.containsKey(key)) {
      Field field = clazz.getDeclaredField(fieldName);
      fieldCache.put(key, field);
    }
    return fieldCache.get(key);
  }

  private record FieldCacheKey(Class<?> clazz, String fieldName) {

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      FieldCacheKey that = (FieldCacheKey) o;
      return clazz.equals(that.clazz) && fieldName.equals(that.fieldName);
    }
  }
}
