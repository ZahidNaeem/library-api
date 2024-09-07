package com.alabtaal.library.util;

import java.lang.reflect.Field;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelationshipHandler {

  private static final Logger LOG = LoggerFactory.getLogger(RelationshipHandler.class);

  public static void setParentForChildren(Object parent) {
    Class<?> parentClass = parent.getClass();
    Field[] fields = parentClass.getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);

      if (Collection.class.isAssignableFrom(field.getType())) {
        try {
          Collection<?> collection = (Collection<?>) field.get(parent);

          for (Object element : collection) {
            Field parentField = getParentField(element.getClass(), parentClass);

            if (parentField != null) {
              parentField.setAccessible(true);
              parentField.set(element, parent);

              // Recursively call the method for multi-level relationships
              setParentForChildren(element);
            }
          }
        } catch (IllegalAccessException e) {
          Miscellaneous.logException(LOG, e);
        }
      }
    }
  }

  private static Field getParentField(Class<?> childClass, Class<?> parentClass) {
    Field[] fields = childClass.getDeclaredFields();

    for (Field field : fields) {
      if (field.getType().equals(parentClass)) {
        return field;
      }
    }

    return null;
  }
}
