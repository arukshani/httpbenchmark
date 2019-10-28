package com.http.perf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.DefaultHttp2Headers;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.CharsetUtil;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.buffer.Unpooled.unreleasableBuffer;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * ff
 */
public class EchoHttp2Handler extends Http2ConnectionHandler implements Http2FrameListener {

    static final ByteBuf RESPONSE_BYTES = unreleasableBuffer(copiedBuffer("Hello World", CharsetUtil.UTF_8));
    private Http2ConnectionEncoder encoder;
    private Http2ConnectionDecoder decoder;

    EchoHttp2Handler(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder,
                     Http2Settings initialSettings) {
        super(decoder, encoder, initialSettings);
        this.encoder = encoder;
        this.decoder = decoder;
    }

  /*  private static Http2Headers http1HeadersToHttp2Headers(FullHttpRequest request) {
        CharSequence host = request.headers().get(HttpHeaderNames.HOST);
        Http2Headers http2Headers = new DefaultHttp2Headers()
                .method(HttpMethod.GET.asciiName())
                .path(request.uri())
                .scheme(HttpScheme.HTTP.name());
        if (host != null) {
            http2Headers.authority(host);
        }
        return http2Headers;
    }

    *//**
     * Handles the cleartext HTTP upgrade event. If an upgrade occurred, sends a simple response via HTTP/2
     * on stream 1 (the stream specifically reserved for cleartext HTTP upgrade).
     *//*
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof HttpServerUpgradeHandler.UpgradeEvent) {
            HttpServerUpgradeHandler.UpgradeEvent upgradeEvent =
                    (HttpServerUpgradeHandler.UpgradeEvent) evt;
            onHeadersRead(ctx, 1, http1HeadersToHttp2Headers(upgradeEvent.upgradeRequest()), 0 , true);
        }
        super.userEventTriggered(ctx, evt);
    }*/

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    /**
     * Sends a "Hello World" DATA frame to the client.
     */
    private void sendResponse(ChannelHandlerContext ctx, int streamId, ByteBuf payload) {
//        // Send a frame for the response status
//        Http2Headers headers = new DefaultHttp2Headers().status(OK.codeAsText());
//        encoder().writeHeaders(ctx, streamId, headers, 0, false, ctx.newPromise());
        encoder().writeData(ctx, streamId, payload, 0, true, ctx.newPromise());

        // no need to call flush as channelReadComplete(...) will take care of it.
    }

    @Override
    public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) {
        int processed = data.readableBytes() + padding;
//        if (endOfStream) {
//            sendResponse(ctx, streamId, data.retain());
//        }
        sendResponse(ctx, streamId, data.retain());
        return processed;
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext ctx, int streamId,
                              Http2Headers headers, int padding, boolean endOfStream)  {
//        if (endOfStream) {
//            ByteBuf content = ctx.alloc().buffer();
//            content.writeBytes(RESPONSE_BYTES.duplicate());
//            ByteBufUtil.writeAscii(content, " - via HTTP/2");
//            sendResponse(ctx, streamId, content);
//        }

//        Http2FrameStream stream = headersFrame.stream();
//        Http2Headers sendHeaders = new DefaultHttp2Headers().status(OK.codeAsText());
//        ctx.write(new DefaultHttp2HeadersFrame(sendHeaders).stream(stream));
//        if (endOfStream) {
//            ctx.write(new DefaultHttp2DataFrame(new EmptyByteBuf(ctx.alloc()), true).stream(stream));
//        }

        Http2Headers sentHeaders = new DefaultHttp2Headers().status(OK.codeAsText());
        encoder().writeHeaders(ctx, streamId, sentHeaders, 0, false, ctx.newPromise());
        try {
            encoder.flowController().writePendingBytes();
        } catch (Http2Exception e) {

        }
//        ctx.flush();
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency,
                              short weight, boolean exclusive, int padding, boolean endOfStream) {
        onHeadersRead(ctx, streamId, headers, padding, endOfStream);
    }

    @Override
    public void onPriorityRead(ChannelHandlerContext ctx, int streamId, int streamDependency,
                               short weight, boolean exclusive) {
    }

    @Override
    public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) {
    }

    @Override
    public void onSettingsAckRead(ChannelHandlerContext ctx) {
    }

    @Override
    public void onSettingsRead(ChannelHandlerContext ctx, Http2Settings settings) {
    }

    @Override
    public void onPingRead(ChannelHandlerContext ctx, long data) {
    }

    @Override
    public void onPingAckRead(ChannelHandlerContext ctx, long data) {
    }

    @Override
    public void onPushPromiseRead(ChannelHandlerContext ctx, int streamId, int promisedStreamId,
                                  Http2Headers headers, int padding) {
    }

    @Override
    public void onGoAwayRead(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) {
    }

    @Override
    public void onWindowUpdateRead(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) {
    }

    @Override
    public void onUnknownFrame(ChannelHandlerContext ctx, byte frameType, int streamId,
                               Http2Flags flags, ByteBuf payload) {
    }
}
