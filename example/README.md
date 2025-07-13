# Example of Conditional Mixin Legacy

![GitHub License](https://img.shields.io/badge/LICENSE-CC0-orange?style=for-the-badge)

This example is based on [ExampleMod](https://github.com/GTNewHorizons/ExampleMod1.7.10) template with GTNHGradle.

If you are building up the project from blank or using another template, you'll have to figure out how to configure
Mixins yourself.

## How to use Condition Mixin Legacy?

### 1. Add Condition Mixin Legacy to your dependencies

TODO.

I need to add jitpack link here.

### 2. Setup your Mixins

You can simply enable Mixins by editing `gradle.properties`:

- `usesMixins = true`
- `mixinsPackage = <partial_package_name>`: you need to make sure that the folder
  `src/main/java/${mod_group}/${partial_package_name}` exists. The `java` can be replaced by `kotlin` and `scala`, but
  Mixins is designed to be work with Java.

Then add your mixin configuration JSONs:

- `src/main/resources/mixins.${your_mod_id}.json` for early mixins. This one will be loaded automatically like modern
  Minecraft.
- `src/main/resources/mixins.${your_mod_id}.late.json` for late mixins. This one will NOT be loaded automatically, and
  we also don't want it to be loaded automatically.\
  _Note that UniMixins < 0.1.21 will ignore all "mixins" section, see below._

### 3. Make a `LateMixinLoader` to load the late configuration

- Create a class at anywhere you like, implementing `ILateMixinLoader`. The example file is located at
  `src/main/java/com/myname/mymodid/MyLateMixinPlugin`.
- Add an annotation `@LateMixin` on the top of the class.
- Return the file name of the late json in `getMixinConfig()`, which is `mixins.${your_mod_id}.late.json`.
- (UniMixins > 0.1.21) Return additional mixin classes in `getMixins()`, or an empty list.\
  (UniMixins < 0.1.21) Return all mixin classes in `getMixins()`, because the ones in configuration is all ignored.

#### 3.1 What is Early and Late Mixins?

Simply, when you are targeting to Minecraft vanilla and Forge classes, you need **Early Mixins**.
If you are targeting to Mod classes, you need **Late Mixins**.

The main difference is that the Late Mixins are loaded when the Forge infra is ready where we can get the versions of
mods.

If you are using mod existence and version check of Conditional Mixins, you'll possibly need it to be loaded as Late
Mixins.

### 4. Make a `MixinConfigPlugin` to let Conditional Mixin handle this

_Yes, they are different things._

Just simple create a class, extending `me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin`. The
example file is located at `src/main/java/com/myname/mymodid/MyMixinConfigPlugin`.

And add the canonical name of it to the "plugin" sections of your mixin configuration JSONs that need the functionality
of Condition Mixins.
