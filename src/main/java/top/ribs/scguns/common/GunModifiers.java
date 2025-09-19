package top.ribs.scguns.common;

import net.minecraft.world.item.ItemStack;
import top.ribs.scguns.interfaces.IGunModifier;
import top.ribs.scguns.item.GunItem;


/**
 * Author: MrCrayfish
 */
public class GunModifiers
{
    public static final IGunModifier LONG_SCOPE_SENSITIVITY_COMPENSATION = new IGunModifier()
    {
        @Override
        public double modifyMouseSensitivity(double sensitivity)
        {
            return sensitivity * 2.0;
        }
    };

    public static final IGunModifier MEDIUM_SCOPE_SENSITIVITY_COMPENSATION = new IGunModifier()
    {
        @Override
        public double modifyMouseSensitivity(double sensitivity)
        {
            return sensitivity * 1.5;
        }
    };
    public static final IGunModifier SILENCED = new IGunModifier()
    {
        @Override
        public boolean silencedFire()
        {
            return true;
        }
        @Override
        public double modifyFireSoundRadius(double radius)
        {
            return radius * 0.5;
        }
    };

    public static final IGunModifier REDUCED_DAMAGE = new IGunModifier()
    {
        @Override
        public float modifyProjectileDamage(float damage)
        {
            return damage * 0.9F;
        }
    };
    public static final IGunModifier INCREASED_DAMAGE = new IGunModifier()
    {
        @Override
        public float modifyProjectileDamage(float damage)
        {
            return damage * 1.2F;
        }
    };
    public static final IGunModifier IRON_BAYONET_DAMAGE = new IGunModifier() {
        @Override
        public float additionalDamage() {
            return 2.0F;
        }

        @Override
        public boolean isMeleeOnly() {
            return false;
        }
    };

    public static final IGunModifier ANTHRALITE_BAYONET_DAMAGE = new IGunModifier() {
        @Override
        public float additionalDamage() {
            return 3.0F;
        }

        @Override
        public boolean isMeleeOnly() {
            return false;
        }
    };
    public static final IGunModifier DIAMOND_BAYONET_DAMAGE = new IGunModifier() {
        @Override
        public float additionalDamage() {
            return 3.5F;
        }
        @Override
        public boolean isMeleeOnly() {
            return false;
        }
    };

    public static final IGunModifier NETHERITE_BAYONET_DAMAGE = new IGunModifier() {
        @Override
        public float additionalDamage() {
            return 4.0F;
        }
        @Override
        public boolean isMeleeOnly() {
            return false;
        }
    };
    public static final IGunModifier EXTENDED_BARREL = new IGunModifier()
    {
        @Override
        public double modifyProjectileSpeed(double speed)
        {
            return speed * 1.2;
        }

        @Override
        public float modifyProjectileSpread(float spread)
        {
            return spread * 0.8F;
        }

        @Override
        public float recoilModifier()
        {
            return 1.25F;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 0.9;
        }
    };
    public static final IGunModifier SLOW_ADS = new IGunModifier()
    {
        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 0.97F;
        }

    };
    public static final IGunModifier LONG_SCOPE_RECOIL_REDUCTION = new IGunModifier()
    {
        @Override
        public float recoilModifier()
        {
            return 0.9F;
        }

        @Override
        public float kickModifier()
        {
            return 0.9F;
        }
    };

    public static final IGunModifier LONG_SCOPE_SPREAD_REDUCTION = new IGunModifier()
    {
        @Override
        public float modifyProjectileSpread(float spread)
        {
            return spread * 0.8F;
        }
    };

    public static final IGunModifier MEDIUM_SCOPE_RECOIL_REDUCTION = new IGunModifier()
    {
        @Override
        public float recoilModifier()
        {
            return 0.9F;
        }

        @Override
        public float kickModifier()
        {
            return 0.85F;
        }
    };

    public static final IGunModifier MEDIUM_SCOPE_SPREAD_REDUCTION = new IGunModifier()
    {
        @Override
        public float modifyProjectileSpread(float spread)
        {
            return spread * 0.85F;
        }
    };

    public static final IGunModifier REFLEX_SIGHT_CRIT_BONUS = new IGunModifier()
    {
        @Override
        public float criticalChance()
        {
            return 0.05F;
        }
    };

    public static final IGunModifier REFLEX_SIGHT_ADS_BONUS = new IGunModifier()
    {
        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 1.1F;
        }

        @Override
        public float modifyProjectileSpread(float spread)
        {
            return spread * 0.9F;
        }
    };

    public static final IGunModifier LASER_SIGHT_SPREAD_REDUCTION = new IGunModifier()
    {
        @Override
        public float modifyProjectileSpread(float spread)
        {
            return spread * 0.85F;
        }

        @Override
        public float criticalChance()
        {
            return 0.05F;
        }
    };

    public static final IGunModifier LASER_SIGHT_ADS_BONUS = new IGunModifier()
    {
        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 1.15F;
        }

        @Override
        public float recoilModifier()
        {
            return 0.95F;
        }
    };
    public static final IGunModifier SLOWER_ADS = new IGunModifier()
    {
        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 0.85F;
        }
        @Override
        public float recoilModifier()
        {
            return 1.05F;
        }
    };
    public static final IGunModifier NORMAL_ADS = new IGunModifier()
    {
        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 1.2F;
        }
    };

    public static final IGunModifier LIGHT_RECOIL = new IGunModifier()
    {
        @Override
        public float recoilModifier()
        {
            return 0.85F;
        }
        @Override
        public float kickModifier()
        {
            return 0.85F;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 1.2F;
        }

    };
    public static final IGunModifier REDUCED_RECOIL = new IGunModifier()
    {
        @Override
        public float recoilModifier()
        {
            return 0.7F;
        }
        @Override
        public float kickModifier()
        {
            return 0.8F;
        }
        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 0.97F;
        }

    };

    public static final IGunModifier EXTENDED_MAG = new IGunModifier() {
        @Override
        public int modifyAmmoCapacity(int baseCapacity) {
            return (int) (baseCapacity * 2.0);
        }
    };

    public static final IGunModifier PLUS_P_MAG = new IGunModifier() {
        @Override
        public int modifyAmmoCapacity(int baseCapacity) {
            return (int) (baseCapacity * 0.5);
        }
    };
    public static final IGunModifier LIGHT_STOCK_MODIFIER = new IGunModifier() {
        @Override
        public float recoilModifier() {
            return 0.9F;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed) {
            return speed * 1.1F;
        }
        @Override
        public double modifyDrawSpeed(double speed) {
            return speed * 1.2F;
        }
    };

    public static final IGunModifier WEIGHTED_STOCK_MODIFIER = new IGunModifier() {
        @Override
        public float recoilModifier() {
            return 0.75F;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed) {
            return speed * 0.85F;
        }

        @Override
        public float kickModifier() {
            return 0.9F;
        }
        @Override
        public double modifyDrawSpeed(double speed) {
            return speed * 0.85F;  // 15% slower draw speed
        }
    };

    public static final IGunModifier WOODEN_STOCK_MODIFIER = new IGunModifier() {
        @Override
        public float recoilModifier() {
            return 0.85F;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed) {
            return speed * 1.05F;
        }
    };
    public static final IGunModifier MUZZLE_BRAKE_MODIFIER = new IGunModifier() {
        @Override
        public float modifyProjectileSpread(float spread) {
            return spread * 0.6F;
        }

        @Override
        public double modifyProjectileSpeed(double speed) {
            return speed * 0.9;
        }

        @Override
        public float recoilModifier() {
            return 0.95F;
        }
    };

    public static final IGunModifier EXTENDED_BARREL_MODIFIER = new IGunModifier() {
        @Override
        public double modifyProjectileSpeed(double speed) {
            return speed * 1.25;
        }

        @Override
        public float modifyProjectileSpread(float spread) {
            return spread * 0.85F;
        }

        @Override
        public float recoilModifier(ItemStack weapon) {
            return isCarbineCandidate(weapon) ? 0.9F : 1.15F;
        }

        @Override
        public float kickModifier(ItemStack weapon) {
            return isCarbineCandidate(weapon) ? 0.95F : 1.2F;
        }

        @Override
        public float modifyProjectileDamage(float damage) {
            return damage * 1.1F;
        }

        private boolean isCarbineCandidate(ItemStack weapon) {
            return ((GunItem) weapon.getItem()).isOneHandedCarbineCandidate(weapon);
        }
        @Override
        public double modifyDrawSpeed(double speed) {
            return speed * 0.95F;
        }
    };

        public static final IGunModifier SILENCER_MODIFIER = new IGunModifier() {
        @Override
        public float modifyProjectileDamage(float damage) {
            return damage * 0.9F;
        }

        @Override
        public double modifyProjectileSpeed(double speed) {
            return speed * 0.9;
        }

        @Override
        public float criticalChance() {
            return 0.15F;
        }
    };

    public static final IGunModifier ADVANCED_SILENCER_MODIFIER = new IGunModifier() {
        @Override
        public double modifyProjectileSpeed(double speed) {
            return speed * 0.93;
        }

        @Override
        public float criticalChance() {
            return 0.25F;
        }
    };
    public static final IGunModifier EXTENDED_MAG_MODIFIER = new IGunModifier() {
        @Override
        public double modifyReloadSpeed(double reloadSpeed) {
            return reloadSpeed * 1.4;
        }

        @Override
        public int modifyAmmoCapacity(int baseCapacity) {
            return (int) (baseCapacity * 2.25);
        }

        @Override
        public float recoilModifier() {
            return 0.9F;
        }

        @Override
        public float kickModifier() {
            return 0.9F;
        }
        @Override
        public double modifyDrawSpeed(double speed) {
            return speed * 0.95F;
        }
    };

    public static final IGunModifier SPEED_MAG_MODIFIER = new IGunModifier() {
        @Override
        public double modifyReloadSpeed(double reloadSpeed) {
            return reloadSpeed * 0.45;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed) {
            return speed * 1.1F;
        }

        @Override
        public float recoilModifier() {
            return 0.9F;
        }

        @Override
        public float kickModifier() {
            return 0.95F;
        }
        @Override
        public double modifyDrawSpeed(double speed) {
            return speed * 1.15F;
        }
    };

}