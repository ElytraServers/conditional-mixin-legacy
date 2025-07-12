package com.myname.mymodid;

import com.google.common.collect.Lists;
import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import java.util.List;
import java.util.Set;

@LateMixin
public class MyLateMixinPlugin implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.mymodid.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        return Lists.newArrayList("NotEnoughItemsMixin");
    }
}
