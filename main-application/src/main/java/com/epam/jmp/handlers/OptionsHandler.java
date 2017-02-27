package com.epam.jmp.handlers;

import lombok.Setter;
import lombok.Getter;
import org.kohsuke.args4j.Option;

/**
 * @author Eugene_Kortelyov
 */
public class OptionsHandler {

    @Getter
    @Setter
    @Option(name = "-in", aliases = "-input-folder")
    private String pathToPlugins;

}
