package com.myname.mymodid.mixins;

import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import codechicken.nei.ItemPanel;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Restriction(require = @Condition(value = "NotEnoughItems", versionPredicates = "2.7.*"))
@Mixin(value = ItemPanel.class, remap = false)
public class NotEnoughItemsMixin {

    @Inject(method = "init", at = @At("HEAD"))
    private void mymod$onInit(CallbackInfo ci) {
        Display.setTitle("Minecraft Code Chicken Bones Infused!");
    }

}
