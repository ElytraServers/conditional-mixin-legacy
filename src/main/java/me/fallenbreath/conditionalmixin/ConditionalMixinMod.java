package me.fallenbreath.conditionalmixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;

@Mod(
    modid = ConditionalMixinMod.MOD_ID,
    name = ConditionalMixinMod.NAME,
    version = Tags.VERSION,
    dependencies = ConditionalMixinMod.DEPS)
public class ConditionalMixinMod {

    public static final String MOD_ID = "conditional_mixin";
    public static final String NAME = "Condition Mixin Legacy";
    public static final String DEPS = "required-after:unimixins;";

    public static final Logger LOGGER = LogManager.getLogger(ConditionalMixinMod.class);
}
