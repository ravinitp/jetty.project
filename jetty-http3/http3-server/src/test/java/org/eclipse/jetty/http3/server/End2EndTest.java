package org.eclipse.jetty.http3.server;

import org.eclipse.jetty.http.HttpCompliance;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.jupiter.api.Test;

public class End2EndTest
{
    @Test
    void name() throws Exception
    {
        Server server = new Server();
        QuicConnector quicConnector = new QuicConnector(server);
        quicConnector.setPort(8443);
        server.setConnectors(new Connector[]{quicConnector});

        HttpConfiguration config = new HttpConfiguration();
        config.setHttpCompliance(HttpCompliance.LEGACY); // enable HTTP/0.9
        HttpConnectionFactory factory = new HttpConnectionFactory(config);
        quicConnector.addConnectionFactory(factory);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{new DefaultHandler()});
        server.setHandler(handlers);

        server.start();

        System.out.println("Started.");
        System.in.read();

        server.stop();
    }
}
