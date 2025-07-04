package com.sushikhacapitals.common.idgen;

import java.util.UUID;

public class UuidDrivenIdGen implements IdGen {

    public UuidDrivenIdGen() {

    }

    @Override
    public String nextId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String nextId(Long id) {
        return nextId();
    }


}
