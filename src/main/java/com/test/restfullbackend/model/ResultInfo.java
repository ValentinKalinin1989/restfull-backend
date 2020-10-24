package com.test.restfullbackend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class for testing param for create or update User
 * and generate info about errors
 */
public class ResultInfo {
    /**
     * result test all param
     * if one param is invalid return false
     */
    private boolean success;
    /**
     * description errors
     */
    private final List<String> errors;
    /**
     * params for test
     */
    private final Map<String, String> properties;


    /**
     * private constructor
     */
    private ResultInfo() {
        success = true;
        errors = new ArrayList<>();
        properties = new HashMap<>();
    }

    /**
     * create ResultInfo with testing
     * and test result and error descriptions
     *
     * @param userForTest - User for test
     * @return - ResultInfo
     */
    public static ResultInfo resultTestUser(User userForTest) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.addPropertyForTest("name", userForTest.getName());
        resultInfo.addPropertyForTest("login", userForTest.getLogin());
        resultInfo.addPropertyForTest("password", userForTest.getPassword());
        resultInfo.testNotEmptyOrBlank();
        resultInfo.testContainUppercaseLetAndNumb();
        return resultInfo;
    }

    /**
     * @return - error descriptions
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Add param for test
     *
     * @param propertyName  - param-name
     * @param propertyValue - param-value
     */
    private void addPropertyForTest(String propertyName, String propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    /**
     * Testing string on empty and blank
     */
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

    /**
     * Testing string on having one number and one uppercase letter
     */
    private void testContainUppercaseLetAndNumb() {
        String password = properties.get("password");
        if (!password.matches("[0-9]+[A-Z]+|[A-Z]+[0-9]+")) {
            errors.add("password - must have one uppercase letter and number");
        }
    }

    /**
     * @return - result of testing
     */
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
