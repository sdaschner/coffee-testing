package com.sebastian_daschner.coffee_shop;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class ProcessorSystem {

    public ProcessorSystem() {
        WireMock.configureFor("coffee-processor.test.kubernetes.local", 80);
        reset();

        WireMock.stubFor(WireMock.post("/coffee-processor/resources/processes")
                .willReturn(responseJson("PREPARING")));
    }

    private ResponseDefinitionBuilder responseJson(String status) {
        return ResponseDefinitionBuilder.okForJson(Collections.singletonMap("status", status));
    }

    public void answerForOrder(URI orderUri, String status) {
        String orderId = extractId(orderUri);
        WireMock.stubFor(WireMock.post("/coffee-processor/resources/processes")
                .withRequestBody(requestJson(orderId))
                .willReturn(responseJson(status)));
    }

    private String extractId(URI orderUri) {
        String string = orderUri.toString();
        return string.substring(string.lastIndexOf('/') + 1);
    }

    private ContentPattern<?> requestJson(String orderId) {
        return WireMock.equalToJson("{\"order\":\"" + orderId + "\"}", true, true);
    }

    private ContentPattern<?> requestJson(String orderId, String status) {
        return WireMock.equalToJson("{\"order\":\"" + orderId + "\",\"status\":\"" + status + "\"}", true, true);
    }

    public void waitForInvocation(URI orderUri, String status) {
        long timeout = System.currentTimeMillis() + 60_000L;

        String orderId = extractId(orderUri);
        while (!requestMatched(status, orderId)) {
            LockSupport.parkNanos(2_000_000_000L);
            if (System.currentTimeMillis() > timeout)
                throw new AssertionError("Invocation hasn't happened within timeout");
        }
    }

    private boolean requestMatched(String status, String orderId) {
        List<LoggedRequest> requests = WireMock.findAll(WireMock.postRequestedFor(WireMock.urlEqualTo("/coffee-processor/resources/processes"))
                .withRequestBody(requestJson(orderId, status)));
        return !requests.isEmpty();
    }

    public void reset() {
        WireMock.resetAllRequests();
    }
}
