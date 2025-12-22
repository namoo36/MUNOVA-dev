package com.space.munovachattest.config;

import io.rsocket.metadata.WellKnownMimeType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.JacksonCborDecoder;
import org.springframework.http.codec.cbor.JacksonCborEncoder;
import org.springframework.http.codec.json.JacksonJsonDecoder;
import org.springframework.http.codec.json.JacksonJsonEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

@Configuration
public class RSocketConfig {
    @Bean
    public RSocketStrategies rSocketStrategies() {
        return RSocketStrategies.builder()
                .encoders(encoders -> {
                    encoders.add(new JacksonJsonEncoder());
                    encoders.add(new JacksonCborEncoder());
                })
                .decoders(decoders -> {
                    decoders.add(new JacksonJsonDecoder());
                    decoders.add(new JacksonCborDecoder());
                })
                .routeMatcher(new PathPatternRouteMatcher())
                .metadataExtractorRegistry(reg -> {
                    reg.metadataToExtract(
                            MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_ROUTING.getString()),
                            String.class,
                            "route"
                    );
                })
                .build();
    }

    @Bean
    public RSocketMessageHandler rSocketMessageHandler(RSocketStrategies strategies) {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRSocketStrategies(strategies);

        return handler;
    }

}
