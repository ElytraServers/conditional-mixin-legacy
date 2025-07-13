package com.myname.mymodid;

import java.util.List;
import java.util.Set;

import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;

public class MyMixinConfigPlugin extends RestrictiveMixinConfigPlugin {

    @Override
    public String getRefMapperConfig() {
        // null to keep it default
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        // do nothing
    }

    @Override
    public List<String> getMixins() {
        // null to keep it default
        return null;
    }

    @Override
    protected void onRestrictionCheckFailed(String mixinClassName, String reason) {
        // output the mixins ignored by Conditional Mixins.
        System.out.println("[EXAMPLE_MOD] A mixin " + mixinClassName + " was disabled because of " + reason);
    }
}
