package vip.testops.gateway.utils;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class FilterUtil {
    public static Mono<Void> failedReturn(String jsonStr, ServerHttpResponse response) {
        byte[] bytes = jsonStr.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().set("Content-Type", "application/json; charset=utf-8");
        return response.writeWith(Flux.just(buffer));
    }
}
