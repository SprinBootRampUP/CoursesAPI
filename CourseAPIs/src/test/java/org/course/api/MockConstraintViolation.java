package org.course.api;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

public class MockConstraintViolation implements ConstraintViolation<Object> {
    private final String propertyPath;
    private final String message;

    public MockConstraintViolation(String propertyPath, String message) {
        this.propertyPath = propertyPath;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getMessageTemplate() {
        return "";
    }


    // Other methods can return default or null values as needed
    @Override public Object getRootBean() { return null; }
    @Override public Class<Object> getRootBeanClass() { return null; }
    @Override public Object getLeafBean() { return null; }

    @Override
    public Object[] getExecutableParameters() {
        return new Object[0];
    }

    @Override
    public Object getExecutableReturnValue() {
        return null;
    }

    @Override
    public Path getPropertyPath() {
        return null;
    }

    @Override public Object getInvalidValue() { return null; }
    @Override public ConstraintDescriptor<?> getConstraintDescriptor() { return null; }
    @Override public <U> U unwrap(Class<U> type) { throw new UnsupportedOperationException(); }
}
