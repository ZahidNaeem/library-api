package com.alabtaal.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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

  public static void setManyToManyRelation(Object entity) {
    setManyToManyRelation(entity, new HashSet<>());
  }

  private static void setManyToManyRelation(Object entity, Set<Object> visited) {
    if (visited.contains(entity)) {
      return;
    }
    visited.add(entity);

    Class<?> entityClass = entity.getClass();
    Field[] fields = entityClass.getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);

      if (Collection.class.isAssignableFrom(field.getType())) {
        try {
          Collection<?> collection = (Collection<?>) field.get(entity);

          for (Object relatedEntity : collection) {
            Field inverseField = getInverseField(relatedEntity.getClass(), entityClass);

            if (inverseField != null) {
              inverseField.setAccessible(true);
              Collection<?> inverseCollection = (Collection<?>) inverseField.get(relatedEntity);

              if (!inverseCollection.contains(entity)) {
                addEntityToCollectionUnchecked(inverseCollection, entity);
              }

              // Recursively call the method for multi-level relationships
              setManyToManyRelation(relatedEntity, visited);
            }
          }
        } catch (IllegalAccessException e) {
          LOG.error("Error accessing field", e);
        }
      }
    }
  }

  private static Field getInverseField(Class<?> relatedClass, Class<?> entityClass) {
    Field[] fields = relatedClass.getDeclaredFields();

    for (Field field : fields) {
      if (Collection.class.isAssignableFrom(field.getType())) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType pt) {
          Type[] actualTypeArguments = pt.getActualTypeArguments();
          if (actualTypeArguments.length > 0 && actualTypeArguments[0].getTypeName().equals(entityClass.getTypeName())) {
            return field;
          }
        }
      }
    }

    return null;
  }

  private static <T> void addEntityToCollection(Collection<T> collection, T entity) {
    if (!collection.contains(entity)) {
      collection.add(entity);
    }
  }

  @SuppressWarnings("unchecked")
  private static <T> void addEntityToCollectionUnchecked(Collection<?> collection, Object entity) {
    addEntityToCollection((Collection<T>) collection, (T) entity);
  }
}

