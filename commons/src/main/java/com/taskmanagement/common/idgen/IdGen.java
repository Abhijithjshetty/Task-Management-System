package com.taskmanagement.common.idgen;

public interface IdGen {
    String nextId();
    String nextId(Long id);
}
