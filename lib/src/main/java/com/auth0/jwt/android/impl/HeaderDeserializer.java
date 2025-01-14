package com.auth0.jwt.android.impl;

import com.auth0.jwt.android.HeaderParams;
import com.auth0.jwt.android.exceptions.JWTDecodeException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;

/**
 * Jackson deserializer implementation for converting from JWT Header parts.
 * <p>
 * This class is thread-safe.
 *
 * @see JWTParser
 */
class HeaderDeserializer extends StdDeserializer<BasicHeader> {

    private final ObjectReader objectReader;

    HeaderDeserializer(ObjectReader objectReader) {
        this(null, objectReader);
    }

    private HeaderDeserializer(Class<?> vc, ObjectReader objectReader) {
        super(vc);

        this.objectReader = objectReader;
    }

    @Override
    public BasicHeader deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, JsonNode> tree = p.getCodec().readValue(p, new TypeReference<Map<String, JsonNode>>() {
        });
        if (tree == null) {
            throw new JWTDecodeException("Parsing the Header's JSON resulted on a Null map");
        }

        String algorithm = getString(tree, HeaderParams.ALGORITHM);
        String type = getString(tree, HeaderParams.TYPE);
        String contentType = getString(tree, HeaderParams.CONTENT_TYPE);
        String keyId = getString(tree, HeaderParams.KEY_ID);
        return new BasicHeader(algorithm, type, contentType, keyId, tree, objectReader);
    }

    String getString(Map<String, JsonNode> tree, String claimName) {
        JsonNode node = tree.get(claimName);
        if (node == null || node.isNull()) {
            return null;
        }
        return node.asText(null);
    }
}
