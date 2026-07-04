package com.sebitas.onixflux.integration;

import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class IntegrationValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger("IntegrationValidator");

    private IntegrationValidator() {}

    public static ValidationResult validate(IntegrationMetadata metadata) {
        var errors = new ArrayList<String>();
        var warnings = new ArrayList<String>();

        if (metadata.id() == null || metadata.id().isBlank()) {
            errors.add("Integration ID is null or empty");
        }
        if (metadata.name() == null || metadata.name().isBlank()) {
            errors.add("Integration name is null or empty");
        }
        if (metadata.modId() == null || metadata.modId().isBlank()) {
            errors.add("Mod ID is null or empty");
        }

        if (!ModList.get().isLoaded(metadata.modId())) {
            warnings.add("Mod " + metadata.modId() + " is not installed, integration will be skipped");
        }

        if (metadata.hasDependencies()) {
            for (var dep : metadata.dependencies()) {
                if (!ModList.get().isLoaded(dep)) {
                    if (dep.equals(metadata.modId())) continue;
                    warnings.add("Dependency " + dep + " is not installed");
                }
            }
        }

        if (errors.isEmpty()) {
            LOGGER.debug("Integration {} validated successfully", metadata.id());
        } else {
            LOGGER.warn("Integration {} has validation errors: {}", metadata.id(), errors);
        }

        return new ValidationResult(errors, warnings);
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public record ValidationResult(List<String> errors, List<String> warnings) {
        public boolean isValid() {
            return errors.isEmpty();
        }
    }

}
