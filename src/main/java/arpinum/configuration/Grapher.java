package arpinum.configuration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.grapher.graphviz.GraphvizGrapher;
import com.google.inject.grapher.graphviz.GraphvizModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

public class Grapher {

    public static void graph(Injector injecton, String name)  {
        PrintWriter out = printerFor(name);

        Injector injector = Guice.createInjector(new GraphvizModule());
        GraphvizGrapher grapher = injector.getInstance(GraphvizGrapher.class);
        grapher.setOut(out);
        grapher.setRankdir("TB");
        doGrap(injecton, grapher);
    }

    private static void doGrap(Injector injecton, GraphvizGrapher grapher)  {
        try {
            grapher.graph(injecton);
        } catch (IOException e) {
            LOGGER.error("Error generating dot file", e);
        }
    }

    private static PrintWriter printerFor(String name)  {
        try {
            return new PrintWriter(Paths.get(System.getProperty("user.dir"), name + ".dot").toFile(), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("Error generating dot file", e);
            return null;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Grapher.class);
}
