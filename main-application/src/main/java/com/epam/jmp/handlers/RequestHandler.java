package com.epam.jmp.handlers;

import com.epam.jmp.impl.CustomUrlClassLoader;
import com.epam.jmp.plugin.test.Testable;
import com.epam.jmp.utils.JarClassLoaderUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eugene_Kortelyov
 */
public class RequestHandler {
    private static final Logger logger = Logger.getLogger(RequestHandler.class);

    private Map<String, List<Class<?>>> pluginsCache;

    {
        pluginsCache = new HashMap<>();
    }

    public void test() throws IllegalAccessException, InstantiationException {
        logger.info("Testing loaded plugins...");
        if(pluginsCache.isEmpty()) {
            logger.info("There are no loaded plugins");
        } else {
            for(Map.Entry<String, List<Class<?>>> plugin: pluginsCache.entrySet()) {
                for(Class<?> clazz: plugin.getValue()){
                    logger.info("Testing: " + clazz.getName());
                    Testable object = (Testable) clazz.newInstance();
                    object.test();
                }
            }
        }
    }

    public void update(String pathToPlugins) throws MalformedURLException, ClassNotFoundException {
        logger.info("Updating plugins from: '" + pathToPlugins + "'");
        CustomUrlClassLoader classLoader = null;
        File[] plugins = JarClassLoaderUtil.getPlugins(pathToPlugins);
        for (File plugin: plugins) {
            String jarAbsolutePath = plugin.getAbsolutePath();
            try{
                List<String> classNamesForLoad = JarClassLoaderUtil.getClassNamesFromJar(jarAbsolutePath);
                List<Class<?>> classesForLoad = new ArrayList<>();
                URL jarUrl = new URL("jar", "", "file:" + jarAbsolutePath + "!/");
                classLoader = new CustomUrlClassLoader(new URL[] {jarUrl}, getClass().getClassLoader());
                for (String classNameForLoad: classNamesForLoad) {
                    classesForLoad.add(classLoader.loadClass(FilenameUtils.getBaseName(classNameForLoad)));
                }
                pluginsCache.put(FilenameUtils.getName(jarAbsolutePath), classesForLoad);
                logger.info("Plugin: " + FilenameUtils.getName(jarAbsolutePath) + " was successfully uploaded");
            } finally {
                if(classLoader!=null) {
                    classLoader.close();
                }
            }
        }
    }

    public void uninstall() {
        logger.info("Uninstalling plugins...");
        pluginsCache.clear();
        logger.info("Uninstalling plugins...done");
    }

    public void exit() {
        logger.info("Exit application...");
        System.exit(0);
    }

}
