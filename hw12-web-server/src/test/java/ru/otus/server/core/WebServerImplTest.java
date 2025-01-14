package ru.otus.server.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Веб сервер должен")
class WebServerImplTest {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private static final int PORT = 40000;
    private final ServletContextFactory servletContextFactory = Mockito.mock(ServletContextFactory.class);

    @BeforeEach
    void setUp() {
        when(servletContextFactory.getServletContextHandler()).thenReturn(Mockito.mock(ServletContextHandler.class));
    }

    @Test
    @DisplayName("запускаться и останавливаться без ошибок")
    void serverIsFunctional() {
        WebServer server = new WebServerImpl(PORT, servletContextFactory);
        assertThatNoException().isThrownBy(server::start);
        assertThatNoException().isThrownBy(server::stop);
    }

    @Test
    @DisplayName("возвращать титульную страницу")
    void welcomePageTest() throws Exception {
        WebServer server = new WebServerImpl(PORT, servletContextFactory);
        server.start();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:" + PORT))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        server.stop();
    }
}
