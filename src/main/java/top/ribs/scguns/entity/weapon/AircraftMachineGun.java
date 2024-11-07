package top.ribs.scguns.entity.weapon;

import com.mrcrayfish.framework.api.network.LevelLocation;
import immersive_aircraft.cobalt.network.NetworkHandler;
import immersive_aircraft.entity.VehicleEntity;
import immersive_aircraft.entity.misc.WeaponMount;
import immersive_aircraft.entity.weapon.BulletWeapon;
import immersive_aircraft.network.c2s.FireMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import top.ribs.scguns.Config;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.common.ProjectileManager;
import top.ribs.scguns.entity.projectile.ProjectileEntity;
import top.ribs.scguns.init.ModItems;
import top.ribs.scguns.init.ModSounds;
import top.ribs.scguns.interfaces.IProjectileFactory;
import top.ribs.scguns.item.GunItem;
import top.ribs.scguns.network.PacketHandler;
import top.ribs.scguns.network.message.S2CMessageBulletTrail;
import top.ribs.scguns.util.GunEnchantmentHelper;

import java.util.concurrent.ThreadLocalRandom;

public class AircraftMachineGun extends BulletWeapon {
    private static final float MAX_COOLDOWN = 0.125f;
    private final float velocity;
    private final float inaccuracy;
    private float cooldown = 0.0f;

    public AircraftMachineGun(VehicleEntity entity, ItemStack stack, WeaponMount mount, int slot) {
        this(entity, stack, mount, slot, 3.0f, 0.0f);

    }

    public AircraftMachineGun(VehicleEntity entity, ItemStack stack, WeaponMount mount, int slot, float velocity, float inaccuracy) {
        super(entity, stack, mount, slot);

        this.velocity = velocity;
        this.inaccuracy = inaccuracy;
    }


    @Override
    protected float getBarrelLength() {
        return 0.5f;
    }

    @Override
    protected Vector4f getBarrelOffset() {
        return new Vector4f(0.0f, 0.3f, 0.0f, 1.0f);
    }

    public float getVelocity() {
        return velocity;
    }

    public float getInaccuracy() {
        return inaccuracy;
    }

    @Override
    protected Entity getBullet(Entity shooter, Vector4f position, Vector3f direction) {
        System.out.println("SHOOT!!!");
        System.out.println(shooter);

        LivingEntity pilot = shooter.getControllingPassenger();

        //TODO: Make a custom gun for this??
        ItemStack gunStack = ModItems.M3_CARABINE.get().getDefaultInstance();
        Gun gun = ModItems.M3_CARABINE.get().getGun();
        final Level level = shooter.level();

        Gun.Projectile projectileProps = gun.getProjectile();
        System.out.println("For loop!!!");
        ResourceLocation projectileItemLocation = ForgeRegistries.ITEMS.getKey(projectileProps.getItem());
        IProjectileFactory factory = ProjectileManager.getInstance().getFactory(projectileItemLocation);
        ProjectileEntity projectileEntity = factory.create(level, pilot, gunStack, (GunItem) gunStack.getItem(), gun);
        System.out.println(projectileEntity);

        projectileEntity.setWeapon(gunStack);
        projectileEntity.setAdditionalDamage(Gun.getAdditionalDamage(gunStack));
        final Vec3 startPos = new Vec3(position.x, position.y, position.z);
        final Vec3 track = new Vec3(direction.x, direction.y, direction.z);

        projectileEntity.setPos(startPos.add(track));
        projectileEntity.setDeltaMovement(track.scale(10));
        level.addFreshEntity(projectileEntity);
        projectileEntity.tick();

        System.out.println("what the fuck do I do");

        if (!projectileProps.isVisible()) {
            System.out.print("It visible");
            int radius = (int) position.x;
            int y1 = (int) (position.z + 3.0);
            int z1 = (int) position.z;
            double r = Config.COMMON.network.projectileTrackingRange.get();
            ParticleOptions data = GunEnchantmentHelper.getParticle(gunStack);

            S2CMessageBulletTrail messageBulletTrail = new S2CMessageBulletTrail(new ProjectileEntity[]{projectileEntity}, projectileProps, -1, data);
            PacketHandler.getPlayChannel().sendToNearbyPlayers(
                    () -> LevelLocation.create(level, radius, y1, z1, r),
                    messageBulletTrail
            );
        }


        return projectileEntity;
    }

    @Override
    public void tick() {
        cooldown -= 1.0f / 20.0f;
    }

    @Override
    public void fire(Vector3f direction) {
        super.fire(direction);
    }

    @Override
    public void clientFire(int index) {
        if (cooldown <= 0.0f) {
            cooldown = MAX_COOLDOWN;
            NetworkHandler.sendToServer(new FireMessage(getSlot(), index, getDirection()));
        }
    }

    private Vector3f getDirection() {
        Vector3f direction = new Vector3f(0, 0, 1.0f);
        direction.mul(new Matrix3f(getMount().transform()));
        direction.mul(getEntity().getVehicleNormalTransform());
        return direction;
    }

    @Override
    public SoundEvent getSound() {
        return ModSounds.IRON_RIFLE_FIRE.get();
    };

    public float getCooldown() {
        return Math.max(0.0f, cooldown / MAX_COOLDOWN);
    }

}
