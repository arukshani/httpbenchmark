package com.http.perf;

import io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2Settings;

import static io.netty.handler.logging.LogLevel.INFO;

/**
 * Test
 */
public class EchoHttp2HandlerBuilder extends
                                     AbstractHttp2ConnectionHandlerBuilder<EchoHttp2Handler, EchoHttp2HandlerBuilder> {
    private static final Http2FrameLogger logger = new Http2FrameLogger(INFO, EchoHttp2Handler.class);

    public EchoHttp2HandlerBuilder() {
//        frameLogger(logger);
    }

    @Override
    public EchoHttp2Handler build() {
        return super.build();
    }

    @Override
    protected EchoHttp2Handler build(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder,
                                     Http2Settings initialSettings) {
        EchoHttp2Handler handler = new EchoHttp2Handler(decoder, encoder, initialSettings);
        frameListener(handler);
        return handler;
    }
}

