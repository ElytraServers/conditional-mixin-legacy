# Conditional Mixin Legacy

[![Build and test](https://github.com/ElytraServers/conditional-mixin-legacy/actions/workflows/build-and-test.yml/badge.svg)](https://github.com/ElytraServers/conditional-mixin-legacy/actions/workflows/build-and-test.yml)
[![](https://jitpack.io/v/ElytraServers/conditional-mixin-legacy.svg)](https://jitpack.io/#ElytraServers/conditional-mixin-legacy)

A fork of [conditional-mixin](https://github.com/Fallen-Breath/conditional-mixin) by Fallen-Breath.

A library mod for using annotation to conditionally apply your mixins to your Minecraft mods.

## Import

```gradle.kts
repositories {
  maven {
    name = "JitPack"
    url = uri("https://jitpack.io")
  }
}

dependencies {
  implementation("com.github.ElytraServers:conditional-mixin-legacy:${TAG_SeeAbove}")
}
```

## [Usage](example)

There is an example based on ExampleMod template in [example](example), read the README in it first.
