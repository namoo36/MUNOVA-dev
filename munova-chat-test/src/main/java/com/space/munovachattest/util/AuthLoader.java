package com.space.munovachattest.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.InputStream;
import java.util.List;

public class AuthLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Data
    public static class AuthItem {
        private String token;
        private Long memberId;
    }

    /** auth_transformed.json 읽는 메서드 */
    public static List<AuthItem> load() {
        try {
            InputStream in = AuthLoader.class.getResourceAsStream("/auth_transformed.json");
            return mapper.readValue(in, new TypeReference<List<AuthItem>>() {});
        } catch (Exception e) {
            throw new RuntimeException("auth_transformed.json 로딩 실패", e);
        }
    }
}
