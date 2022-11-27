package com.auth0.jwt.algorithms;

import android.util.Base64;

import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

class NoneAlgorithm extends Algorithm {

    NoneAlgorithm() {
        super("none", "none");
    }

    @Override
    public void verify(DecodedJWT jwt) throws SignatureVerificationException {
        try {
            byte[] signatureBytes = Base64.decode(jwt.getSignature(), Base64.URL_SAFE | Base64.NO_PADDING);

            if (signatureBytes.length > 0) {
                throw new SignatureVerificationException(this);
            }
        } catch (IllegalArgumentException e) {
            throw new SignatureVerificationException(this, e);
        }
    }

    @Override
    public byte[] sign(byte[] headerBytes, byte[] payloadBytes) throws SignatureGenerationException {
        return new byte[0];
    }

    @Override
    public byte[] sign(byte[] contentBytes) throws SignatureGenerationException {
        return new byte[0];
    }
}
