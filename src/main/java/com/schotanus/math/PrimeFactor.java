package com.schotanus.math;

import java.math.BigInteger;


public record PrimeFactor(BigInteger primeNumber, int exponent) {

    @Override
    public String toString() {
        return String.format("%s^%d", primeNumber, exponent);
    }
}
