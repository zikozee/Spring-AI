package com.zee.aidocs.config;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 22 Jul, 2024
 */

@Configuration
public class HintsRegistrar implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerPattern("*.pdf");
        hints.resources().registerPattern("*.st");
//        hints.reflection().registerType(UUID.class);
    }
}
