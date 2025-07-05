package com.taskmanagement.common.security;

import com.taskmanagement.common.idgen.IdGen;
import org.apache.commons.lang3.RandomStringUtils;

public class DatabaseIdGenerator implements IdGen {
    private String mnemonic;
    private IdGen sequenceGen;

    public DatabaseIdGenerator(String mnemonic, IdGen sequenceGen) {
        this.mnemonic = mnemonic.substring(0, 4);
        this.sequenceGen = sequenceGen;
    }

    @Override
    public String nextId() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(mnemonic);
        buffer.append(fourCharRandomAlphaNumericSequence());
        buffer.append(sequenceGen.nextId());
        buffer.append(fourCharRandomAlphaNumericSequence());
        return buffer.toString().toLowerCase();
    }

    private String fourCharRandomAlphaNumericSequence() {
        return RandomStringUtils.randomAlphanumeric(4);
    }

    @Override
    public String nextId(Long id) {
        return nextId();
    }
}
