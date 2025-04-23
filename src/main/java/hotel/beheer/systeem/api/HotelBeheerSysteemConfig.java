package hotel.beheer.systeem.api;

import hotel.beheer.systeem.api.config.JPAConfig;
import hotel.beheer.systeem.api.controllers.*;
import hotel.beheer.systeem.api.dao.*;
import hotel.beheer.systeem.api.mappers.*;
import hotel.beheer.systeem.api.services.*;

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

        // Betaalmethode

        BetaalmethodeDao betaalmethodeDao = new BetaalmethodeDao(entityManager);
        BetaalmethodeService betaalmethodeService = new BetaalmethodeService(betaalmethodeDao);
        BetaalmethodeMapper betaalmethodeMapper = new BetaalmethodeMapper();

        BeschikbareKamerDao beschikbareKamerDao = new BeschikbareKamerDao(entityManager);
        BeschikbareKamersService beschikbareKamersService = new BeschikbareKamersService(beschikbareKamerDao);
        BeschikbareKamerMapper beschikbareKamerMapper = new BeschikbareKamerMapper();


        // kamersboeken
        KamersBoekenDao kamersBoekenDao = new KamersBoekenDao(entityManager);

        KamersBoekenMapper kamersBoekenMapper = new KamersBoekenMapper();

        KamersBoekenService kamersBoekenService = new KamersBoekenService(kamersBoekenDao);



        KlantDao klantDao = new KlantDao(entityManager);
        KlantMapper klantMapper = new KlantMapper();
        KlantService klantService = new KlantService(klantDao);

        // voor betaalmethode controller
        BetaalmethodeController betaalmethodeController = new BetaalmethodeController(betaalmethodeService, betaalmethodeMapper, klantService);
        // betaalmethode dwingen om constructor injectie toe te passen
        config.register(betaalmethodeController);

        // kamersboeken controller
        KamersBoekenController kamersBoekenController = new KamersBoekenController(kamersBoekenService, kamersBoekenMapper, klantService, beschikbareKamersService, betaalmethodeService);
        config.register(kamersBoekenController); // object dwingen om constructor injectie toe te passenn


        KlantController klantController = new KlantController(klantService, klantMapper, betaalmethodeService);
        // dwingen om contructor injectie toe te passen
        config.register(klantController);

        // kamer dao
        KamerDao kamerDao = new KamerDao(entityManager);
        // kamer service
        KamerService kamerService = new KamerService(kamerDao);

        BeschikbareKamersController beschikbareKamersController = new BeschikbareKamersController(beschikbareKamersService, beschikbareKamerMapper, kamerService);
        config.register(beschikbareKamersController);

        // kamer Mapper
        KamerMapper kamerMapper = new KamerMapper();
        // kamerController
        KamerController kamerController = new KamerController(kamerService, kamerMapper);
        // dwingen om constructor injectie toe te passen
        config.register(kamerController);

        // betaalmethode




//        // Betaalmethode
//
//        BetaalmethodeService betaalmethodeService = new BetaalmethodeService(betaalmethodeDao);




        // Set the handler and start the server
        server.setHandler(context);
        server.start();
        server.join();


    }
}
