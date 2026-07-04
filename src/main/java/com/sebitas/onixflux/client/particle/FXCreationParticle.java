package com.sebitas.onixflux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FXCreationParticle extends SimpleAnimatedParticle {

    private final double originX;
    private final double originY;
    private final double originZ;

    protected FXCreationParticle(ClientLevel level, double x, double y, double z,
                                 double vx, double vy, double vz, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0);
        this.originX = x;
        this.originY = y;
        this.originZ = z;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.quadSize = 0.25f + random.nextFloat() * 0.15f;
        this.lifetime = 20 + random.nextInt(20);
        this.setSpriteFromAge(sprites);
        this.hasPhysics = false;
        this.alpha = 1.0f;
    }

    @Override
    public void tick() {
        super.tick();
        float progress = (float) age / lifetime;
        alpha = Mth.clamp(1.0f - progress * 1.5f, 0, 1);
        quadSize += 0.01f;
        xd *= 0.95;
        yd += 0.005;
        zd *= 0.95;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements net.minecraft.client.particle.ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public TextureSheetParticle createParticle(SimpleParticleType type, ClientLevel level,
                                                   double x, double y, double z,
                                                   double vx, double vy, double vz) {
            return new FXCreationParticle(level, x, y, z, vx, vy, vz, sprites);
        }
    }

}
