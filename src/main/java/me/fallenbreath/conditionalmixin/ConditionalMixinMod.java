package me.fallenbreath.conditionalmixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;

@Mod(modid = ConditionalMixinMod.MOD_ID_FORGE, name = "Condition Mixin", version = Tags.VERSION)
public class ConditionalMixinMod {

    public static final String MOD_ID_FABRIC = "conditional-mixin";
    public static final String MOD_ID_FORGE = "conditional_mixin";

    public static final Logger LOGGER = LogManager.getLogger(ConditionalMixinMod.class);
}
