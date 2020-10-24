package com.test.restfullbackend.model;

/**
 * Marker class for JsonView
 */
public final class Views {

    /**
     * marker for name-field
     */
    public interface Name {
    }

    /**
     * marker for name-field, login-field
     */
    public interface NameAndLogin extends Name {
    }

    /**
     * marker for name-field, login-field, password-field
     */
    public interface NameAndLoginAndPassw extends NameAndLogin {
    }

}
