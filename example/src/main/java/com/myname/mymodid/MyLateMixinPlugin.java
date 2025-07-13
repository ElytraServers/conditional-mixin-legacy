package com.myname.mymodid;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class MyLateMixinPlugin implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.mymodid.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        // this example still uses the old UniMixins, which it will ignore all the mixin classes in the configuration.
        // but luckily, we are only working with "client" section, which it won't overwrite lol.
        return Lists.newArrayList();
    }
}
