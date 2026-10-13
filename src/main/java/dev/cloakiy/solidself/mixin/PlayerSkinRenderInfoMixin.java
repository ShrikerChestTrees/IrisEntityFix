package dev.cloakiy.solidself.mixin;

import dev.cloakiy.solidself.SolidSelf;
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

/**
 * Player heads (placed, held, in item frames and worn) get their render type from this
 * cache entry, and vanilla uses the translucent type there as well. Swap it for the
 * cutout type that every other mob head already uses. The cached value is left as is,
 * so the toggle key takes effect immediately.
 */
@Mixin(targets = "net.minecraft.client.renderer.PlayerSkinRenderCache$RenderInfo")
public abstract class PlayerSkinRenderInfoMixin {
    @Shadow @Final private PlayerSkin playerSkin;

    @Inject(method = "renderType", at = @At("RETURN"), cancellable = true)
    private void solidself$useCutout(CallbackInfoReturnable<RenderType> cir) {
        if (!SolidSelf.isEnabled()) return;

        Identifier texture = this.playerSkin.body().texturePath();
        if (cir.getReturnValue() == RenderTypes.entityTranslucent(texture)) {
            cir.setReturnValue(RenderTypes.entityCutoutZOffset(texture));
        }
    }
}
