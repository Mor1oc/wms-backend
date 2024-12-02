package com.warehouse.WMS;

public interface Executable<I,O> {
    public O execute(I input);
}
