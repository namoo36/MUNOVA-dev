package com.space.munovachattest.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class RSocketMetadataUtil {

    private static ByteBuf createCompositeEntry(String mime, byte[] content) {
        byte[] mimeBytes = mime.getBytes(StandardCharsets.UTF_8);

        ByteBuf buf = Unpooled.buffer(1 + mimeBytes.length + 4 + content.length);

        buf.writeByte(mimeBytes.length);          // MIME length
        buf.writeBytes(mimeBytes);                // MIME String
        buf.writeInt(content.length);             // Content length
        buf.writeBytes(content);                  // Content bytes

        return buf;
    }

    /** SETUP metadata — authentication */
    public static ByteBuf auth(String token) {
        return createCompositeEntry(
                "message/x.rsocket.authentication.v0",
                token.getBytes(StandardCharsets.UTF_8)
        );
    }

    /** ROUTING metadata — requestResponse / requestStream 등 */
    public static ByteBuf route(String route) {
        return createCompositeEntry(
                "message/x.rsocket.routing.v0",
                route.getBytes(StandardCharsets.UTF_8)
        );
    }

    /** ★ Composite Metadata 전체 생성 */
    public static ByteBuf composite(ByteBuf... entries) {
        CompositeByteBuf composite = Unpooled.compositeBuffer();
        for (ByteBuf entry : entries) {
            composite.addComponent(true, entry);
        }
        return composite;
    }
}
