package com.shrikerchesttree.irisentityfix.mixin;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerSkin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.renderer.PlayerSkinRenderCache$RenderInfo")
public abstract class PlayerSkinRenderInfoMixin {
    @Shadow @Final private PlayerSkin playerSkin;

    @Inject(method = "renderType", at = @At("RETURN"), cancellable = true)
    private void irisentityfix$useCutout(CallbackInfoReturnable<RenderType> cir) {
        Identifier texture = this.playerSkin.body().texturePath();
        if (cir.getReturnValue() == RenderTypes.entityTranslucent(texture)) {
            cir.setReturnValue(RenderTypes.entityCutoutZOffset(texture));
        }
    }
}
