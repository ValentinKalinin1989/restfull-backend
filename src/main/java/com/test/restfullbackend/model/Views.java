package com.test.restfullbackend.model;

/**
 * Marker class for JsonView
 */

public final class Views {

    public interface Name{}

    public interface NameAndLogin extends Name{}

    public interface NameAndLoginAndPassw extends NameAndLogin{}

}
