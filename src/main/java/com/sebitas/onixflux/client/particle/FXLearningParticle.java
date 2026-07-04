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
public class FXLearningParticle extends SimpleAnimatedParticle {

    private final double targetX;
    private final double targetY;
    private final double targetZ;

    protected FXLearningParticle(ClientLevel level, double x, double y, double z,
                                 double tx, double ty, double tz, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0);
        this.targetX = tx;
        this.targetY = ty;
        this.targetZ = tz;
        this.quadSize = 0.2f + random.nextFloat() * 0.1f;
        this.lifetime = 25 + random.nextInt(15);
        this.setSpriteFromAge(sprites);
        this.hasPhysics = false;
        this.alpha = 1.0f;

        double dx = targetX - x;
        double dy = targetY - y;
        double dz = targetZ - z;
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double speed = 0.04 + random.nextDouble() * 0.02;
        this.xd = dx / dist * speed;
        this.yd = dy / dist * speed;
        this.zd = dz / dist * speed;
    }

    @Override
    public void tick() {
        super.tick();
        float progress = (float) age / lifetime;
        alpha = Mth.clamp(1.0f - progress * 1.2f, 0, 1);
        quadSize *= 0.97f;
        xd *= 0.98;
        yd *= 0.98;
        zd *= 0.98;
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
            return new FXLearningParticle(level, x, y, z, vx, vy, vz, sprites);
        }
    }

}
