package com.epam.jmp;

import com.epam.jmp.handlers.OptionsHandler;
import com.epam.jmp.handlers.RequestHandler;
import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.Console;
import java.net.MalformedURLException;

/**
 * @author Eugene_Kortelyov
 */
public class Runner {

    private static final Logger logger = Logger.getLogger(Runner.class);

    public static void printInfo() {
        System.out.println();
        logger.info("Avaliable commands: ");
        logger.info("test - for testing plugins");
        logger.info("update - for updating plugins");
        logger.info("uninstall - for clearing all uploaded plugins");
        logger.info("exit - for exit from application");
        System.out.println();

    }

    public static void main(String[] args) throws CmdLineException, MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        OptionsHandler options = new OptionsHandler();
        CmdLineParser parser = new CmdLineParser(options);
        parser.parseArgument(args);
        String pathToPlugins = options.getPathToPlugins();

        Console console = System.console();

        RequestHandler requestHandler = new RequestHandler();

        logger.info("Starting console application for dynamic loading of new development functionality");
        if(pathToPlugins == null) {
            logger.warn("You didn't specify initial path to plugins, please specify it now:");
            pathToPlugins = console.readLine();
        }
        logger.info("Initial plugins loading from: '" + pathToPlugins + "'");
        requestHandler.update(pathToPlugins);
        printInfo();

        while (true) {
            System.out.println("Please, enter command: ");
            String input = console.readLine();
            switch (input) {
                case "test":
                    requestHandler.test();
                    break;
                case "update": {
                        logger.info("Please, write path to plugins or nothing to use default");
                        String updatedPathToPlugins = console.readLine();
                        if(updatedPathToPlugins.isEmpty()){
                            requestHandler.update(pathToPlugins);
                        } else {
                            requestHandler.update(updatedPathToPlugins);
                        }
                    }
                    break;
                case "uninstall":
                    requestHandler.uninstall();
                    break;
                case "exit":
                    requestHandler.exit();
                    break;
                default:
                    System.out.println("There is no such command: '" + input + "', please use one of the following:");
                    printInfo();
            }
        }
    }

}
