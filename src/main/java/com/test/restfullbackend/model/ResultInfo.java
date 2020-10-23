package com.test.restfullbackend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultInfo {
    private boolean success;
    private final List<String> errors;
    private final Map<String, String> properties;


    private ResultInfo() {
        success = true;
        errors = new ArrayList<>();
        properties = new HashMap<>();
    }

    public static ResultInfo resultTestUser(User userForTest) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.addPropertyForTest("name", userForTest.getName());
        resultInfo.addPropertyForTest("login", userForTest.getLogin());
        resultInfo.addPropertyForTest("password", userForTest.getPassword());
        resultInfo.testNotEmptyOrBlank();
        resultInfo.testContainUppercaseLetAndNumb();
        return resultInfo;
    }

    public List<String> getErrors() {
        return errors;
    }

    private void addPropertyForTest(String propertyName, String propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    private void testNotEmptyOrBlank() {
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

    private void testContainUppercaseLetAndNumb() {
        String password = properties.get("password");
        if (!password.matches("[0-9]+[A-Z]+|[A-Z]+[0-9]+")) {
            errors.add("password - must have one uppercase letter and number");
        }
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "success=" + success +
                ", errors=" + errors +
                '}';
    }
}
