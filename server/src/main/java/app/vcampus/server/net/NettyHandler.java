package app.vcampus.server.net;

import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.Session;
import app.vcampus.server.utility.router.Router;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public class NettyHandler extends ChannelInboundHandlerAdapter {
    private final Gson gson = new Gson();
    private final Router router;
    private final SessionFactory database;
    private Session session;

    public NettyHandler(Router router, SessionFactory database) {
        this.router = router;
        this.database = database;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("[{}] Client connected from {}", ctx.channel().id(), ctx.channel().remoteAddress());
        session = new Session();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("[{}] Client disconnected", ctx.channel().id());
    }

    @Override
    public void channelRead(@NonNull ChannelHandlerContext ctx, @NonNull Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            log.info("[{}] Received: {}", ctx.channel().id(), in.toString(CharsetUtil.UTF_8));
            Request request = gson.fromJson(in.toString(CharsetUtil.UTF_8), Request.class);
            request.setSession(session);

            Response response = null;

            if (!router.hasRoute(request.getUri())) {
                log.info("[{}] Route not found: {}", ctx.channel().id(), request.getUri());
                response = Response.Common.notFound();
            } else if (!session.permission(router.getRole(request.getUri()))) {
                log.info("[{}] Permission denied: {}", ctx.channel().id(), request.getUri());
                response = Response.Common.permissionDenied();
            } else {
                response = router.invoke(request, database.openSession());
            }

            if (response.getSession() != null) {
                log.info("[{}] Session updated: {}", ctx.channel().id(), response.getSession());
                session = response.getSession();
            }

            response.setId(request.getId());
            sendResponse(ctx, response);
        } catch (Exception e) {
            log.error("[{}] Exception: {}", ctx.channel().id(), e.getMessage());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void sendResponse(ChannelHandlerContext ctx, Response response) {
        ctx.writeAndFlush(Unpooled.copiedBuffer(gson.toJson(response), CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
//        cause.printStackTrace();
        log.error("[{}] Exception: {}", ctx.channel().id(), cause.getMessage());
        ctx.close();
    }
}
