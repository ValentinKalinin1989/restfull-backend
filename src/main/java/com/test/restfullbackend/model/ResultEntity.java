package com.test.restfullbackend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultEntity {
    private boolean success;
    List<String> errors;
    Map<String, String> properties;

    public ResultEntity() {
        success = true;
        errors = new ArrayList<>();
        properties = new HashMap<>();
    }

    public void addPropertyForTest(String propertyName, String propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    public void testNotEmptyOrBlank() {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            if (entry.getValue().isEmpty()) {
                success = false;
                errors.add(entry.getKey() + " - is EMPTY");
            } else if (entry.getValue().isBlank()) {
                success = false;
                errors.add(entry.getKey() + " - is BLANK");
            }
        }
    }

    public void testContainUppercaseLetAndNumb() {
        String password = properties.get("password");
        if (password.matches("[0-9]+[A-Z]+|[A-Z]+[0-9]+")) {
            errors.add("password - must have one uppercase letter and number");
        }
    }

    public static ResultEntity resultTestUser(User userForTest) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.addPropertyForTest("name", userForTest.getName());
        resultEntity.addPropertyForTest("login", userForTest.getLogin());
        resultEntity.addPropertyForTest("password", userForTest.getPassword());
        resultEntity.testNotEmptyOrBlank();
        resultEntity.testContainUppercaseLetAndNumb();
        return resultEntity;
    }

    public boolean isSuccess() {
        return success;
    }

}
