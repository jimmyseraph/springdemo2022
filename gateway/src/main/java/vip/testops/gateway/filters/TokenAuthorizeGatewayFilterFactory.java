package vip.testops.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vip.testops.gateway.entities.resp.AuthorizeResp;
import vip.testops.gateway.entities.resp.Response;
import vip.testops.gateway.utils.FilterUtil;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class TokenAuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenAuthorizeGatewayFilterFactory.Config> {

    private static final String AUTHORIZE_TOKEN = "Access-Token";
    private final String USER_ID_IN_HEADER = "user-id";
    private final String USER_NAME_IN_HEADER = "user-name";
    private final String USER_EMAIL_IN_HEADER = "user-email";
    private Gson gson = new Gson();

    private WebClient.Builder webClientBuilder;

    @Autowired
    public void setWebClientBuilder(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public TokenAuthorizeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("---- start checking access token -----");
            // 1. get access token from request header
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(AUTHORIZE_TOKEN);

            // 2. call account service, authorize method to authorize the token

            if (config.getNeedAuth()) {
                Response<?> response = new Response<>();
                if(StringUtil.isNullOrEmpty(token)) {
                    serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.invalidToken();

                    return FilterUtil.failedReturn(gson.toJson(response), serverHttpResponse);
                }

                // call authorize
                return webClientBuilder.build().get()
                        .uri(uriBuilder -> uriBuilder.scheme("http")
                                .host("account")
                                .path("/user/authorize")
                                .queryParam("token", token)
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve().bodyToMono(String.class)
                        .onErrorResume(error -> {
                            log.info("call account service error.", error);
                            response.invalidToken();

                            return Mono.just(gson.toJson(response));
                        })
                        .flatMap(respStr -> {
                            Response<AuthorizeResp> resp = gson.fromJson(respStr, new TypeToken<Response<AuthorizeResp>>(){}.getType());
                            if(resp == null || resp.getCode() != 1000) {
                                serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                                response.invalidToken();
                                return FilterUtil.failedReturn(gson.toJson(response), serverHttpResponse);
                            }
                            // 3. if token is valid, put user info into header, go next; else return access denied.
                            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
                            builder.header(USER_NAME_IN_HEADER, resp.getData().getName())
                                    .header(USER_ID_IN_HEADER, String.valueOf(resp.getData().getId()))
                                    .header(USER_EMAIL_IN_HEADER, resp.getData().getEmail());
                            return chain.filter(exchange.mutate().request(builder.build()).build());
                        });
            }

            return chain.filter(exchange);

        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("needAuth");
    }

    public static class Config {
        private Boolean needAuth;

        public Config(Boolean needAuth) {
            this.needAuth = needAuth;
        }

        public Config() {}

        public Boolean getNeedAuth() {
            return needAuth;
        }

        public void setNeedAuth(Boolean needAuth) {
            this.needAuth = needAuth;
        }
    }
}
