package com.dsamastery.common.enums;

public enum ExecutionStatus {
    QUEUED, COMPILING, RUNNING, PASSED, FAILED, ERROR, TIMEOUT;

    public boolean isTerminal() {
        return this == PASSED || this == FAILED || this == ERROR || this == TIMEOUT;
    }
}