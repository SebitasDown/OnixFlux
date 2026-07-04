package com.sebitas.onixflux.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.transmutation.FluxTableBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FluxTableBER implements BlockEntityRenderer<FluxTableBlockEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            OnixFlux.MOD_ID, "textures/entity/flux_table_fx.png");

    public FluxTableBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(FluxTableBlockEntity entity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (entity == null || entity.getBlockPos() == null) return;

        float time = (entity.getLevel() != null ? entity.getLevel().getGameTime() + partialTick : 0);
        float alpha = 0.3f + 0.2f * (float) Math.sin(time * 0.05);

        poseStack.pushPose();
        poseStack.translate(0.5, 1.02, 0.5);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));

        float halfSize = 0.4f;
        var m = poseStack.last().pose();

        consumer.vertex(m, -halfSize, 0.0f, -halfSize).color(1.0f, 1.0f, 1.0f, alpha).uv(0.0f, 0.0f).overlayCoords(packedOverlay).uv2(packedLight).normal(0.0f, 1.0f, 0.0f).endVertex();
        consumer.vertex(m, halfSize, 0.0f, -halfSize).color(1.0f, 1.0f, 1.0f, alpha).uv(1.0f, 0.0f).overlayCoords(packedOverlay).uv2(packedLight).normal(0.0f, 1.0f, 0.0f).endVertex();
        consumer.vertex(m, halfSize, 0.0f, halfSize).color(1.0f, 1.0f, 1.0f, alpha).uv(1.0f, 1.0f).overlayCoords(packedOverlay).uv2(packedLight).normal(0.0f, 1.0f, 0.0f).endVertex();
        consumer.vertex(m, -halfSize, 0.0f, halfSize).color(1.0f, 1.0f, 1.0f, alpha).uv(0.0f, 1.0f).overlayCoords(packedOverlay).uv2(packedLight).normal(0.0f, 1.0f, 0.0f).endVertex();

        poseStack.popPose();
    }

}
