<div align="center">

# IrisRenderFix (fork Solid Self)

**Fixed rendering for piglins, players and other stuff in 26.3 (Iris Vulkan/OpenGL). \*Support for SEUS PTGI HRR 2.1 REFORGED**

</div>

<br>

## Issue

From 26.x some entities and custom player heads rendered with [Iris](https://modrinth.com/mod/iris) shaders turn partly see-through from some camera angles especially against a bright sky/PTGI. Shader effects that work on everything else like world outlines skips completely.

It happens with different shaders like sVision, SEUS PTGI HRR 2.1 REFORGED, Rethinking Voxels, etc. Upstream report: [Iris #3105](https://github.com/IrisShaders/Iris/issues/3105).

> [!NOTE]
> Partly transparent pixels in skins are shown either fully visible or fully invisible. Most skins don't have any and most shaders already show them wrong ([Iris #3135](https://github.com/IrisShaders/Iris/issues/3135)).

## Installation

1. Download [Fabric Loader](https://fabricmc.net/use/) for Minecraft 26.3
2. Put [Fabric API](https://modrinth.com/mod/fabric-api) and this mod into your `mods` folder
3. Launch the game

## How it works

Vanilla draws entities with the translucent entity render type. Iris draws translucent geometry after the shader's deferred passes so effects in those passes never see the entity.

IrisRenderFix swaps that render type for cutout the one most mobs use. Custom player heads and some entities are then drawn together with every other solid entity.

| File | Purpose |
|---|---|
| [`LivingEntityRendererMixin.java`](src/main/java/com/shrikerchesttree/irisentityfix/mixin/LivingEntityRendererMixin.java) | Fix for entities |
| [`PlayerSkinRenderInfoMixin.java`](src/main/java/com/shrikerchesttree/irisentityfix/mixin/PlayerSkinRenderInfoMixin.java) | Fix for custom player heads |
| [`RenderTypesMixin.java`](src/main/java/com/shrikerchesttree/irisentityfix/mixin/RenderTypesMixin.java) | Fix for banners |
| [`IrisTextShaderProgramMixin.java`](src/main/java/com/shrikerchesttree/irisentityfix/mixin/IrisTextShaderProgramMixin.java) | Fix for signs (text on it) |

## Build

Use JDK 25

```bash
./gradlew build
```

The jar will be in `*/build/libs/*.jar` (no sources!).

<br>

## CREDITS

[support@cloaki.de](mailto:support@cloaki.de)

<br>


<div align="center">

<sub>

[MIT License](LICENSE)

Not an official Minecraft product. Not approved by or associated with Mojang or Microsoft.

</sub>

</div>
