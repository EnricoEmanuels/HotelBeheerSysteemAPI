package hotel.beheer.systeem.api;

import hotel.beheer.systeem.api.config.JPAConfig;
import hotel.beheer.systeem.api.controllers.KlantController;
import hotel.beheer.systeem.api.dao.KlantDao;
import hotel.beheer.systeem.api.mappers.KlantMapper;
import hotel.beheer.systeem.api.services.KlantService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Hello world!
 *
 */
public class HotelBeheerSysteemConfig
{
    public static void main( String[] args ) throws Exception {
        // Disable JAXB optimization
        System.setProperty("org.glassfish.jaxb.runtime.v2.runtime.JAXBContextImpl.fastBoot", "true");
        // Create a Jetty server
        Server server = new Server(8080);

        // Create a ServletContextHandler  ( dit is voor je default patch)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Configure Jersey
        ResourceConfig config = new ResourceConfig();
        config.register(JacksonFeature.class); // Register Jackson for JSON support
        config.packages("hotel.beheer.systeem.api.controllers"); // Replace with your package name (hij zal alleen kijken in de packages die jij hebt aangegeven waarin die resources zijn als zijn er andere resources/controllers die je niet hebt aangegeven hij gaat niet erin kijken )

        // Add the Jersey ServletContainer to the context
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));
        context.addServlet(jerseyServlet, "/api/*"); // Map to /api/*
        context.addServlet(jerseyServlet, "/*");

        // Register CORSFilter
        FilterHolder corsFilter = new FilterHolder(new CORSFilter());
        context.addFilter(corsFilter, "/*", null);

        // Eerst deze regel: dit initialiseert de EntityManagerFactory
        EntityManagerFactory entityManagerFactory = JPAConfig.getEntityMangerFactory();

        // Dan pas deze regel: nu is emf niet meer null
        EntityManager entityManager = JPAConfig.getEntityManger();

        KlantDao klantDao = new KlantDao(entityManager);
        KlantMapper klantMapper = new KlantMapper();
        KlantService klantService = new KlantService(klantDao);
        KlantController klantController = new KlantController(klantService, klantMapper);
        config.register(klantController);



        // Set the handler and start the server
        server.setHandler(context);
        server.start();
        server.join();


    }
}
