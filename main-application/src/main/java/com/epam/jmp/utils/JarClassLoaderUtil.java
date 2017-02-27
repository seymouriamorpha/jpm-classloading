package com.epam.jmp.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.Logger;

/**
 * @author Eugene_Kortelyov
 */
public class JarClassLoaderUtil {
	private static final Logger logger = Logger.getLogger(JarClassLoaderUtil.class);

	public static final File[] getPlugins(String pathToPlugins) {
		FileFilter fileFilter = new WildcardFileFilter("*plugin*.jar");
		File[] plugins = new File(pathToPlugins).listFiles(fileFilter);
		if (plugins==null) {
            logger.warn("Incorrect path to plugins: '" + pathToPlugins + "'");
            plugins = new File[0];
        }
		if (plugins.length == 0) {
			logger.warn("No plugins found");
		} else {
			logger.info("Found " + plugins.length + " plugin(s)");
		}
		return plugins;
	}

	public static final List<String> getClassNamesFromJar(String jarName) {
		ArrayList<String> classes = new ArrayList<>();
		try {
			@SuppressWarnings("resource")
			JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
			JarEntry jarEntry;

			while (true) {
				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null) {
					break;
				}
				if (jarEntry.getName().endsWith(".class")) {
					classes.add(jarEntry.getName().replaceAll("/", "\\."));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return classes;
	}

}