package com.test.restfullbackend.model;

/**
 * класс маркер для JsonView
 */

public final class Views {

    public interface Name{}

    public interface NameAndLogin extends Name{}

    public interface NameAndLoginAndPassw extends NameAndLogin{}

}
