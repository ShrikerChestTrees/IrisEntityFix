package com.shrikerchesttree.irisrenderfix.mixin;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTypes.class)
public abstract class RenderTypesMixin {

    @Inject(method = "bannerPattern", at = @At("HEAD"), cancellable = true)
    private static void irisrenderfix$routeBannerPatternToCutout(
            Identifier texture,
            CallbackInfoReturnable<RenderType> cir) {
        cir.setReturnValue(RenderTypes.entityCutout(texture));
    }
}