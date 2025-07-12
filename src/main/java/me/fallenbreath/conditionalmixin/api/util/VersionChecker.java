package me.fallenbreath.conditionalmixin.api.util;

import java.util.Collection;
import java.util.Optional;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.VersionRange;
import me.fallenbreath.conditionalmixin.ConditionalMixinMod;

public class VersionChecker {

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
        ArtifactVersion version = modContainer.get()
            .getProcessedVersion();

        try {
            return VersionRange.createFromVersionSpec(versionPredicate)
                .containsVersion(version);
        } catch (Exception e) {
            ConditionalMixinMod.LOGGER.error(
                "Failed to parse version or version predicate {} {}: {}",
                version.toString(),
                versionPredicate,
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
