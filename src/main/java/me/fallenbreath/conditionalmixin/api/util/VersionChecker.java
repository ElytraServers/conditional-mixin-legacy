package me.fallenbreath.conditionalmixin.api.util;

import java.util.Collection;
import java.util.Optional;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionRange;
import me.fallenbreath.conditionalmixin.ConditionalMixinMod;

public class VersionChecker {

    public static final String METADATA_VERSION_PREFIX = "metadata:";

    private static Optional<ModContainer> getModContainer(String modId) {
        return Loader.instance()
            .getModList()
            .stream()
            .filter(
                it -> it.getModId()
                    .equals(modId))
            .findFirst();
    }

    /**
     * Return if the given mod presents in the current environment
     *
     * @param modId The id of the mod to check
     */
    public static boolean isModPresent(String modId) {
        return Loader.isModLoaded(modId);
    }

    /**
     * Return a string version of the given mod, or {@link Optional#empty} if the mod doesn't exist
     *
     * @param modId The id of the mod to get version from
     */
    public static Optional<String> getModVersionString(String modId) {
        return getModContainer(modId).map(ModContainer::getVersion);
    }

    private static Optional<ArtifactVersion> getModVersion(ModContainer modContainer, boolean useMetadataVersion) {
        if (useMetadataVersion) {
            String modId = modContainer.getModId();
            return Optional.ofNullable(modContainer.getMetadata())
                .map(metadata -> metadata.version)
                .map(version -> new DefaultArtifactVersion(modId, version));
        } else {
            return Optional.of(modContainer.getProcessedVersion());
        }
    }

    /**
     * Use the loader's util to check if a given version satisfy a version predicate
     *
     * @param modId            The id of the mod to test the given version predicate
     * @param versionPredicate A string indicates a version predicate, in the syntax of the current platform. For
     *                         example:
     *                         <ul>
     *                         <li>">=1.2.0" or "2.0.x" for fabric-like platforms</li>
     *                         <li>"[1.2.0,)" or "[2.0, 2.1)" for forge-like platforms</li>
     *                         </ul>
     */
    public static boolean doesModVersionSatisfyPredicate(String modId, String versionPredicate) {
        Optional<ModContainer> modContainer = getModContainer(modId);
        if (!modContainer.isPresent()) {
            return false;
        }

        boolean useMetadataVersion = versionPredicate.startsWith(METADATA_VERSION_PREFIX);
        String versionPredicateRange = useMetadataVersion ? versionPredicate.substring(METADATA_VERSION_PREFIX.length())
            : versionPredicate;
        Optional<ArtifactVersion> version = getModVersion(modContainer.get(), useMetadataVersion);
        if (!version.isPresent()) {
            ConditionalMixinMod.LOGGER
                .error("Failed to get version (useMetadataVersion = {}) {}", useMetadataVersion, modContainer);
            return false;
        }

        try {
            return VersionRange.createFromVersionSpec(versionPredicateRange)
                .containsVersion(version.get());
        } catch (Exception e) {
            ConditionalMixinMod.LOGGER.error(
                "Failed to parse version or version predicate {} {}: {}",
                version.toString(),
                versionPredicateRange,
                e);
        }
        return false;
    }

    /**
     * If the given mod version satisfies any of the predicate, or given versionPredicates is empty
     * <p>
     * If the given mod does not exist, false will be returned
     *
     * @param modId             The id of the mod to test the given version predicates
     * @param versionPredicates A collection of the version predicates to test
     */
    public static boolean doesModVersionSatisfyPredicate(String modId, Collection<String> versionPredicates) {
        if (!isModPresent(modId)) {
            return false;
        }
        return versionPredicates.isEmpty() || versionPredicates.stream()
            .anyMatch(vp -> doesModVersionSatisfyPredicate(modId, vp));
    }

}
