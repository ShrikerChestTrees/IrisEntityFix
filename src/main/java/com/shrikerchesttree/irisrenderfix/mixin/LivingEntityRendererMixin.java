package com.shrikerchesttree.irisrenderfix.mixin;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.ZombifiedPiglinRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "getRenderType", at = @At("RETURN"), cancellable = true)
    private void irisrenderfix$useCutoutForAffectedEntities(
            LivingEntityRenderState state,
            boolean bodyVisible,
            boolean translucent,
            boolean glowing,
            CallbackInfoReturnable<RenderType> cir) {

        if (!bodyVisible || translucent) {
            return;
        }

        Object renderer = (Object) this;
        boolean target = renderer instanceof AvatarRenderer<?>
                || renderer instanceof PiglinRenderer
                || renderer instanceof ZombifiedPiglinRenderer;
        if (!target) {
            return;
        }

        @SuppressWarnings("unchecked")
        LivingEntityRenderer<?, LivingEntityRenderState, ?> livingRenderer =
                (LivingEntityRenderer<?, LivingEntityRenderState, ?>) renderer;
        Identifier texture = livingRenderer.getTextureLocation(state);

        if (cir.getReturnValue() == RenderTypes.entityTranslucent(texture)) {
            cir.setReturnValue(RenderTypes.entityCutout(texture));
        }
    }
}
