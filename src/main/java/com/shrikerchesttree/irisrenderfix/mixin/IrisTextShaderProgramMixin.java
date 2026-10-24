package com.shrikerchesttree.irisrenderfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.irisshaders.iris.pipeline.programs.ShaderKey", remap = false)
public abstract class IrisTextShaderProgramMixin {
    @Inject(method = "getProgram", at = @At("HEAD"), cancellable = true, remap = false)
    private void irisrenderfix$routeWorldTextToOpaqueShader(CallbackInfoReturnable<Object> cir) {
        String shaderKey = String.valueOf(this);
        final String programName;
        if ("TEXT".equals(shaderKey)) {
            programName = "Entities";
        } else if ("TEXT_BE".equals(shaderKey)) {
            programName = "Block";
        } else {
            return;
        }

        try {
            Class<?> programIdClass = Class.forName(
                    "net.irisshaders.iris.shaderpack.loading.ProgramId",
                    false,
                    this.getClass().getClassLoader());
            Object[] constants = programIdClass.getEnumConstants();
            if (constants != null) {
                for (Object constant : constants) {
                    if (constant instanceof Enum<?> enumValue && programName.equals(enumValue.name())) {
                        cir.setReturnValue(constant);
                        return;
                    }
                }
            }
        } catch (Throwable ignored) {
        }
    }
}