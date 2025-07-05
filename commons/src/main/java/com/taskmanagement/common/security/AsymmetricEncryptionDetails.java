package com.taskmanagement.common.security;

public interface AsymmetricEncryptionDetails<T> {
    T publicKey();
    T privateKey();
    String passphrase();
    String alias();
    String name();
}
