package org.example.settings;

public class InvalidSettingsException extends Exception {
    public InvalidSettingsException(String errorMessage) {
        super(errorMessage);
    }
}
