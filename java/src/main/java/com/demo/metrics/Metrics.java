package com.demo.metrics;

import io.prometheus.client.Gauge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import io.prometheus.client.Counter;

public class Metrics {

    public static final Counter numCars = Counter.build()
            .name("num_cars").help("Number of Cars.").register();

    public static final Gauge carSpeed = Gauge.build()
            .name("speed_cars").help("Speed of Cars.").register();

    private Server server;

    private static final Logger logger = LogManager.getLogger(Metrics.class);

    public void start() throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8082);
        server.setConnectors(new Connector[] {connector});
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new MetricsServlet()), "/metrics");
        context.addServlet(new ServletHolder(new CarsServlet()), "/cars");

        server.start();
        server.join();
    }

    public static void main(String... args) throws Exception {
        Metrics metrics = new Metrics();
        metrics.start();
    }
}
