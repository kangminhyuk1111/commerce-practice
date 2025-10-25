package com.commerce.utils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class BaseReflectionUtils {

  /**
   * 엔티티의 private 필드에 값을 설정합니다.
   * 부모 클래스의 필드도 접근 가능합니다.
   *
   * @param entity 대상 엔티티
   * @param fieldName 필드명
   * @param value 설정할 값
   */
  public static void setField(Object entity, String fieldName, Object value) {
    try {
      Field field = getField(entity.getClass(), fieldName);
      field.setAccessible(true);
      field.set(entity, value);
    } catch (Exception e) {
      throw new RuntimeException(
          String.format("Failed to set field '%s' on %s", fieldName, entity.getClass().getName()), e);
    }
  }

  /**
   * 클래스 또는 부모 클래스에서 필드를 찾습니다.
   */
  private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
    try {
      return clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      if (clazz.getSuperclass() == null) {
        throw e;
      }
      return getField(clazz.getSuperclass(), fieldName);
    }
  }

  /**
   * 엔티티의 id를 설정합니다.
   */
  public static void setId(Object entity, Long id) {
    setField(entity, "id", id);
  }

  /**
   * 엔티티의 createdAt을 설정합니다.
   */
  public static void setCreatedAt(Object entity, LocalDateTime createdAt) {
    setField(entity, "createdAt", createdAt);
  }

  /**
   * 엔티티의 updatedAt을 설정합니다.
   */
  public static void setUpdatedAt(Object entity, LocalDateTime updatedAt) {
    setField(entity, "updatedAt", updatedAt);
  }
}