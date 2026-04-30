package com.ghulam.move.utils;

import org.hibernate.id.IdentifierGenerator;

import java.security.SecureRandom;

public final class ShortIdGenerator implements IdentifierGenerator {

    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ID_LENGTH = 16;

    @Override
    public String generate(org.hibernate.engine.spi.SharedSessionContractImplementor session, Object object) {
        StringBuilder id = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length());
            id.append(ALPHANUMERIC_CHARACTERS.charAt(index));
        }
        return id.toString();
    }
}
