package top.ribs.scguns;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;
import top.ribs.scguns.client.SwayType;
import top.ribs.scguns.client.render.crosshair.DotRenderMode;
import top.ribs.scguns.client.screen.ButtonAlignment;
import top.ribs.scguns.entity.projectile.turret.TurretProjectileEntity;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Config
{
    /**
     * Client related config options
     */
    public static class Client
    {
        public final Sounds sounds;
        public final Display display;
        public final Particle particle;
        public final Controls controls;
        public final Experimental experimental;
        public final ForgeConfigSpec.BooleanValue hideConfigButton;
        public final ForgeConfigSpec.EnumValue<ButtonAlignment> buttonAlignment;

        public Client(ForgeConfigSpec.Builder builder)
        {
            builder.push("client");
            {
                this.sounds = new Sounds(builder);
                this.display = new Display(builder);
                this.particle = new Particle(builder);
                this.controls = new Controls(builder);
                this.experimental = new Experimental(builder);
            }
            builder.pop();
            this.hideConfigButton = builder.comment("If enabled, hides the config button from the backpack screen").define("hideConfigButton", false);
            this.buttonAlignment = builder.comment("The alignment of the buttons in the backpack inventory screen").defineEnum("buttonAlignment", ButtonAlignment.RIGHT);
        }
    }

    /**
     * Sound related config options
     */
    public static class Sounds
    {
        public final ForgeConfigSpec.BooleanValue playSoundWhenHeadshot;
        public final ForgeConfigSpec.ConfigValue<String> headshotSound;
        public final ForgeConfigSpec.BooleanValue playSoundWhenCritical;
        public final ForgeConfigSpec.ConfigValue<String> criticalSound;
        public final ForgeConfigSpec.DoubleValue impactSoundDistance;

        public Sounds(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Control sounds triggered by guns").push("sounds");
            {
                this.playSoundWhenHeadshot = builder.comment("If true, a sound will play when you successfully hit a headshot on a entity with a gun").define("playSoundWhenHeadshot", true);
                this.headshotSound = builder.comment("The sound to play when a headshot occurs").define("headshotSound", "minecraft:entity.player.attack.knockback");
                this.playSoundWhenCritical = builder.comment("If true, a sound will play when you successfully hit a critical on a entity with a gun").define("playSoundWhenCritical", true);
                this.criticalSound = builder.comment("The sound to play when a critical occurs").define("criticalSound", "minecraft:entity.player.attack.crit");
                this.impactSoundDistance = builder.comment("The maximum distance impact sounds from bullet can be heard").defineInRange("impactSoundDistance", 32.0, 0.0, 32.0);
            }
            builder.pop();
        }
    }

    /**
     * Display related config options
     */
    public static class Display
    {
        public final ForgeConfigSpec.BooleanValue oldAnimations;
        public final ForgeConfigSpec.ConfigValue<String> crosshair;
        public final ForgeConfigSpec.BooleanValue displayGunInfo;
        public final ForgeConfigSpec.BooleanValue immersiveGunInfo;
        public final ForgeConfigSpec.BooleanValue cinematicGunEffects;
        public final ForgeConfigSpec.BooleanValue cooldownIndicator;
        public final ForgeConfigSpec.BooleanValue weaponSway;
        public final ForgeConfigSpec.DoubleValue swaySensitivity;
        public final ForgeConfigSpec.EnumValue<SwayType> swayType;
        public final ForgeConfigSpec.BooleanValue cameraRollEffect;
        public final ForgeConfigSpec.DoubleValue cameraRollAngle;
        public final ForgeConfigSpec.BooleanValue restrictCameraRollToWeapons;
        public final ForgeConfigSpec.BooleanValue sprintAnimation;
        public final ForgeConfigSpec.DoubleValue bobbingIntensity;
        public final ForgeConfigSpec.BooleanValue fireLights;
        public final ForgeConfigSpec.BooleanValue puritySeals;
        public final ForgeConfigSpec.DoubleValue dynamicCrosshairBaseSpread;
        public final ForgeConfigSpec.DoubleValue dynamicCrosshairSpreadMultiplier;
        public final ForgeConfigSpec.DoubleValue dynamicCrosshairReactivity;
        public final ForgeConfigSpec.EnumValue<DotRenderMode> dynamicCrosshairDotMode;
        public final ForgeConfigSpec.BooleanValue onlyRenderDotWhileAiming;
        public final ForgeConfigSpec.DoubleValue dynamicCrosshairDotThreshold;
        public final ForgeConfigSpec.DoubleValue dynamicCrosshairMaxScale;
        public final ForgeConfigSpec.BooleanValue renderArms;



        public Display(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Configuration for display related options").push("display");
            {
                this.oldAnimations = builder.comment("If true, uses the old animation poses for weapons. This is only for nostalgic reasons and not recommended to switch back.").define("oldAnimations", false);
                this.crosshair = builder.comment("The custom crosshair to use for weapons. Go to (Options > Controls > Mouse Settings > Crosshair) in game to change this!").define("crosshair", "scguns:dynamic");
                this.cooldownIndicator = builder.comment("If enabled, renders a cooldown indicator to make it easier to learn when you fire again.").define("cooldownIndicator", true);
                this.weaponSway = builder.comment("If enabled, the weapon will sway when the player moves their look direction. This does not affect aiming and is only visual.").define("weaponSway", true);
                this.swaySensitivity = builder.comment("The sensistivity of the visual weapon sway when the player moves their look direciton. The higher the value the more sway.").defineInRange("swaySensitivity", 0.3, 0.0, 1.0);
                this.swayType = builder.comment("The animation to use for sway. Directional follows the camera better while Drag is more immersive").defineEnum("swayType", SwayType.DRAG);
                this.cameraRollEffect = builder.comment("If enabled, the camera will roll when strafing while holding a gun. This creates a more immersive feeling.").define("cameraRollEffect", true);
                this.cameraRollAngle = builder.comment("When Camera Roll Effect is enabled, this is the absolute maximum angle the roll on the camera can approach.").defineInRange("cameraRollAngle", 1.5F, 0F, 45F);
                this.restrictCameraRollToWeapons = builder.comment("When enabled, the Camera Roll Effect is only applied when holding a weapon.").define("restrictCameraRollToWeapons", true);
                this.sprintAnimation = builder.comment("Enables the sprinting animation on weapons for better immersion. This only applies to weapons that support a sprinting animation.").define("sprintingAnimation", true);
                this.displayGunInfo = builder.comment("If enabled, renders a HUD element displaying the gun's ammo count and ammo capacity, as well as pulse weapon charge.").define("displayGunInfo", true);
                this.immersiveGunInfo = builder.comment("If enabled, the HUD will display when inspecting the gun.").define("immersiveGunInfo", false);
                this.bobbingIntensity = builder.comment("The intensity of the custom bobbing animation while holding a gun").defineInRange("bobbingIntensity", 1.0, 0.0, 2.0);
                this.cinematicGunEffects = builder.comment("If enabled, enables cinematic camera effects on guns ").define("cinematicGunEffects", true);
                this.fireLights = builder.comment("If enabled, enables light sources when firing guns").define("fireLights", true);
                this.puritySeals = builder.comment("If enabled, enables purity seals on weapons").define("puritySeals", true);
                this.dynamicCrosshairBaseSpread = builder.comment("The resting size of the Dynamic Crosshair when spread is zero.").defineInRange("dynamicCrosshairBaseSpread", 1.0, 0.0, 5.0);
                this.dynamicCrosshairSpreadMultiplier = builder.comment("The bloom factor of the Dynamic Crosshair when spread increases.").defineInRange("dynamicCrosshairSpreadMultiplier", 1.0, 1.0, 1.5);
                this.dynamicCrosshairReactivity = builder.comment("How reactive the Dynamic Crosshair is to shooting.").defineInRange("dynamicCrosshairReactivity", 2.0, 0.0, 10.0);
                this.dynamicCrosshairDotMode = builder.comment("The rendering mode used for the Dynamic Crosshair's center dot. At Min Spread will only render the dot when gun spread is stable.").defineEnum("dynamicCrosshairDotMode", DotRenderMode.AT_MIN_SPREAD);
                this.onlyRenderDotWhileAiming = builder.comment("If true, the Dynamic Crosshair's center dot will only render while aiming. Obeys dynamicCrosshairDotMode, and has no effect when mode is set to Never.").define("onlyRenderDotWhileAiming", true);
                this.dynamicCrosshairDotThreshold = builder.comment("The threshold of spread (including modifiers) below which the Dynamic Crosshair's center dot is rendered. Affects the At Min Spread and Threshold modes only.").defineInRange("dynamicCrosshairDotThreshold", 0.8, 0.0, 90.0);
                this.dynamicCrosshairMaxScale = builder
                        .comment("The maximum scale factor for the dynamic crosshair when spread is high")
                        .defineInRange("dynamicCrosshairMaxScale", 8.0, 1.0, 20.0);
                this.renderArms = builder.comment("If true, renders the player's arms when holding a gun").define("renderArms", true);
            }
            builder.pop();
        }
    }

    /**
     * Particle related config options
     */
    public static class Particle
    {
        public final ForgeConfigSpec.IntValue bulletHoleLifeMin;
        public final ForgeConfigSpec.IntValue bulletHoleLifeMax;
        public final ForgeConfigSpec.DoubleValue bulletHoleFadeThreshold;
        public final ForgeConfigSpec.BooleanValue enableBlood;
        public final ForgeConfigSpec.DoubleValue impactParticleDistance;
        public final ForgeConfigSpec.BooleanValue enableWaterImpactParticles;
        public final ForgeConfigSpec.BooleanValue enableLavaImpactParticles;

        public Particle(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to particles").push("particle");
            {
                this.bulletHoleLifeMin = builder.comment("The minimum duration in ticks before bullet holes will disappear").defineInRange("bulletHoleLifeMin", 150, 0, Integer.MAX_VALUE);
                this.bulletHoleLifeMax = builder.comment("The maximum duration in ticks before bullet holes will disappear").defineInRange("bulletHoleLifeMax", 200, 0, Integer.MAX_VALUE);
                this.bulletHoleFadeThreshold = builder.comment("The percentage of the maximum life that must pass before particles begin fading away. 0 makes the particles always fade and 1 removes facing completely").defineInRange("bulletHoleFadeThreshold", 0.98, 0, 1.0);
                this.enableBlood = builder.comment("If true, blood will will spawn from entities that are hit from a projectile").define("enableBlood", true);
                this.impactParticleDistance = builder.comment("The maximum distance impact particles can be seen from the player").defineInRange("impactParticleDistance", 32.0, 0.0, 64.0);
                this.enableWaterImpactParticles = builder.comment("If true, particles will spawn when projectiles impact water").define("enableWaterImpactParticles", true);
                this.enableLavaImpactParticles = builder.comment("If true, particles will spawn when projectiles impact lava").define("enableLavaImpactParticles", true);
            }
            builder.pop();
        }
    }

    public static class Controls
    {
        public final ForgeConfigSpec.DoubleValue aimDownSightSensitivity;
        public final ForgeConfigSpec.BooleanValue flipControls;

        public Controls(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to controls").push("controls");
            {
                this.aimDownSightSensitivity = builder.comment("A value to multiple the mouse sensitivity by when aiming down weapon sights. Go to (Options > Controls > Mouse Settings > ADS Sensitivity) in game to change this!").defineInRange("aimDownSightSensitivity", 0.75, 0.0, 1.0);
                this.flipControls = builder.comment("When enabled, switches the shoot and aim controls of weapons. Due to technical reasons, you won't be able to use offhand items if you enable this setting.").define("flipControls", false);
            }
            builder.pop();
        }
    }

    public static class Experimental
    {
        public Experimental(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Experimental options").push("experimental");
            {
            }
            builder.pop();
        }
    }

    /**
     * Common config options
     */    public static class Common
    {
        public final Gameplay gameplay;
        public final Network network;
        public final AggroMobs aggroMobs;
        public final FleeingMobs fleeingMobs;
        public final Rockets rockets;
        public final Grenades grenades;
        public final StunGrenades stunGrenades;
        public final ProjectileSpread projectileSpread;
        public final Gunsmith Gunsmith;
        public final Turret turret;


        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("common");
            {
                this.gameplay = new Gameplay(builder);
                this.network = new Network(builder);
                this.aggroMobs = new AggroMobs(builder);
                this.fleeingMobs = new FleeingMobs(builder);
                this.rockets = new Rockets(builder);
                this.grenades = new Grenades(builder);
                this.stunGrenades = new StunGrenades(builder);
                this.projectileSpread = new ProjectileSpread(builder);
                this.Gunsmith = new Gunsmith(builder);
                this.turret = new Turret(builder);
            }
            builder.pop();
        }
    }

    /**
     * Gameplay related config options
     */
    public static class Gameplay
    {
        public final Griefing griefing;
        public final ForgeConfigSpec.BooleanValue enableFirePlacement;
        public final ForgeConfigSpec.BooleanValue enableGunDamage;
        public final ForgeConfigSpec.BooleanValue enableAttachmentDamage;
        public  final ForgeConfigSpec.BooleanValue spawnCasings;
        public final ForgeConfigSpec.DoubleValue growBoundingBoxAmount;
        public final ForgeConfigSpec.BooleanValue enableHeadShots;
        public final ForgeConfigSpec.DoubleValue headShotDamageMultiplier;
        public final ForgeConfigSpec.DoubleValue criticalDamageMultiplier;
        public final ForgeConfigSpec.BooleanValue ignoreLeaves;
        public final ForgeConfigSpec.BooleanValue enableKnockback;
        public final ForgeConfigSpec.DoubleValue knockbackStrength;
        public final ForgeConfigSpec.BooleanValue improvedHitboxes;
        public final ForgeConfigSpec.DoubleValue enemyBulletDamage;
        public final ForgeConfigSpec.DoubleValue ammoBoxCapacityMultiplier;
        public final ForgeConfigSpec.IntValue energyProductionRate;
        public final ForgeConfigSpec.BooleanValue drawAnimation;
        public final ForgeConfigSpec.BooleanValue forceEnergyGuns;
        public final ForgeConfigSpec.BooleanValue toggleADS;
        public final ForgeConfigSpec.DoubleValue globalDamageMultiplier;
        public final ForgeConfigSpec.BooleanValue disableVillagerSpawning;
        public final ForgeConfigSpec.DoubleValue dissidentSpawnChance;
        public final ForgeConfigSpec.BooleanValue enableAutoReload;
        public Gameplay(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to gameplay").push("gameplay");
            {
                this.griefing = new Griefing(builder);
                this.drawAnimation = builder.comment("If true, enables the draw animation for weapons").define("drawAnimation", true);
                this.ammoBoxCapacityMultiplier = builder.comment("Multiplier to adjust the capacity of all ammo boxes.").defineInRange("ammoBoxCapacityMultiplier", 1.0, 0.1, 100.0);
                this.enableGunDamage = builder.comment("If true, guns will be damageable and can break, coward if you toggle this").define("enableGunDamage", true);
                this.enableAttachmentDamage = builder.comment("If true, gun attachments will be damageable and can break, also a coward").define("enableAttachmentDamage", true);
                this.spawnCasings = builder.comment("Set to false to disable the spawning of casing items when firing guns.").define("gameplay.spawnCasings", true);
                this.growBoundingBoxAmount = builder.comment("The extra amount to expand an entity's bounding box when checking for projectile collision. Setting this value higher will make it easier to hit entities").defineInRange("growBoundingBoxAmount", 0.3, 0.0, 1.0);
                this.enableHeadShots = builder.comment("Enables the check for head shots for players. Projectiles that hit the head of a player will have increased damage.").define("enableHeadShots", true);
                this.headShotDamageMultiplier = builder.comment("The value to multiply the damage by if projectile hit the players head").defineInRange("headShotDamageMultiplier", 1.25, 1.0, Double.MAX_VALUE);
                this.criticalDamageMultiplier = builder.comment("The value to multiply the damage by if projectile is a critical hit").defineInRange("criticalDamageMultiplier", 1.5, 1.0, Double.MAX_VALUE);
                this.ignoreLeaves = builder.comment("If true, projectiles will ignore leaves when checking for collision").define("ignoreLeaves", true);
                this.enableKnockback = builder.comment("If true, projectiles will cause knockback when an entity is hit. By default this is set to true to match the behaviour of Minecraft.").define("enableKnockback", true);
                this.knockbackStrength = builder.comment("Sets the strength of knockback when shot by a bullet projectile. Knockback must be enabled for this to take effect. If value is equal to zero, knockback will use default minecraft value").defineInRange("knockbackStrength", 0.15, 0.0, 1.0);
                this.improvedHitboxes = builder.comment("If true, improves the accuracy of weapons by considering the ping of the player. This has no affect on singleplayer. This will add a little overhead if enabled.").define("improvedHitboxes", false);
                this.enemyBulletDamage = builder.comment("Damage dealt by the Enemy Guns")
                        .defineInRange("enemyBulletDamage", 4.5, 0.0, Double.MAX_VALUE);
                this.energyProductionRate = builder
                        .comment("Energy produced per tick by the Polar Generator. Adjust this value to balance the generator's output.")
                        .defineInRange("energyProductionRate", 50, 1, Integer.MAX_VALUE);
                this.forceEnergyGuns = builder
                        .comment("If true, guns will always use energy system even if Create mod is loaded NOT WORKING.")
                        .define("forceEnergyGuns", false);
                this.toggleADS = builder
                        .comment("If true, guns will toggle ADS mode.")
                        .define("toggleADS", false);
                this.globalDamageMultiplier = builder
                        .comment("Global multiplier for all gun damage. 1.0 = normal damage, 0.5 = half damage, 2.0 = double damage. Affects all projectile damage from guns.")
                        .defineInRange("globalDamageMultiplier", 1.0, 0.01, 10.0);
                this.disableVillagerSpawning = builder
                        .comment("If true, the Brass Mask ritual will never spawn villagers. When disabled, it will spawn Dissidents instead based on the spawn chance below.")
                        .define("disableVillagerSpawning", false);
                this.dissidentSpawnChance = builder
                        .comment("When villager spawning is disabled, this is the chance (0.0 to 1.0) that a Dissident will spawn. If a Dissident doesn't spawn, nothing will be created from the ritual.")
                        .defineInRange("dissidentSpawnChance", 0.1, 0.0, 1.0);
                this.enableAutoReload = builder
                        .comment("If true, guns will automatically start reloading when fired with an empty magazine if ammo is available")
                        .define("enableAutoReload", true);
                this.enableFirePlacement = builder.comment("If true, allows flamethrowers to place fire on blocks").define("enableFirePlacement", true);
            }
            builder.pop();
        }
    }

    /**
     * Gun griefing related config options
     */
    public static class Griefing
    {
        public final ForgeConfigSpec.BooleanValue enableBlockRemovalOnExplosions;
        public final ForgeConfigSpec.BooleanValue enableGlassBreaking;
        public final ForgeConfigSpec.BooleanValue fragileBlockDrops;
        public final ForgeConfigSpec.DoubleValue fragileBaseBreakChance;
        public final ForgeConfigSpec.BooleanValue setFireToBlocks;
        public final ForgeConfigSpec.BooleanValue enableBlockBreaking;
        public final ForgeConfigSpec.BooleanValue enableBeamMining;

        public Griefing(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties related to gun griefing").push("griefing");
            {
                this.enableBlockRemovalOnExplosions = builder.comment("If enabled, allows block removal on explosions").define("enableBlockRemovalOnExplosions", true);
                this.enableGlassBreaking = builder.comment("If enabled, allows guns to shoot out glass and other fragile objects").define("enableGlassBreaking", true);
                this.fragileBlockDrops = builder.comment("If enabled, fragile blocks will drop their loot when broken").define("fragileBlockDrops", true);
                this.fragileBaseBreakChance = builder.comment("The base chance that a fragile block is broken when impacted by a bullet. The hardness of a block will scale this value; the harder the block, the lower the final calculated chance will be.").defineInRange("fragileBlockBreakChance", 1.0, 0.0, 1.0);
                this.setFireToBlocks = builder.comment("If true, allows guns enchanted with Fire Starter to light and spread fires on blocks").define("setFireToBlocks", true);
                this.enableBlockBreaking = builder.comment("If true, allows heavy rounds to break blocks").define("enableBlockBreaking", true);
                this.enableBeamMining = builder.comment("If true, allows beam weapons to mine blocks").define("enableBeamMining", true);
            }
            builder.pop();
        }
    }

    /**
     * Network related config options
     */
    public static class Network
    {
        public final ForgeConfigSpec.DoubleValue projectileTrackingRange;

        public Network(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to network").push("network");
            {
                this.projectileTrackingRange = builder.comment("The distance players need to be within to be able to track new projectiles trails. Higher values means you can see projectiles from that start from further away.").defineInRange("projectileTrackingRange", 200.0, 1, Double.MAX_VALUE);
            }
            builder.pop();
        }
    }

    /**
     * Mob aggression related config options
     */
    public static class AggroMobs {
        public final ForgeConfigSpec.BooleanValue enabled;
        public final ForgeConfigSpec.DoubleValue unsilencedRange;
        public final ForgeConfigSpec.DoubleValue aggroChance;
        public final ForgeConfigSpec.DoubleValue chainAggroChance;
        public final ForgeConfigSpec.DoubleValue chainAggroRadius;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> exemptEntities;

        public AggroMobs(ForgeConfigSpec.Builder builder) {
            builder.comment("Properties relating to mob aggression from gunfire").push("aggro_mobs");
            {
                this.enabled = builder.comment("If true, hostile mobs may become aggressive when guns are fired nearby.").define("enabled", true);
                this.unsilencedRange = builder.comment("Range in which hostile mobs may detect and react to unsilenced gunfire.").defineInRange("unsilencedRange", 20.0, 0.0, Double.MAX_VALUE);
                this.aggroChance = builder.comment("Chance (0.0 to 1.0) that a hostile mob will target the shooter when detecting gunfire.").defineInRange("aggroChance", 0.25, 0.0, 1.0);
                this.chainAggroChance = builder.comment("Chance (0.0 to 1.0) that nearby mobs of the same type will also aggro when one becomes hostile.").defineInRange("chainAggroChance", 0.5, 0.0, 1.0);
                this.chainAggroRadius = builder.comment("Radius in which chain aggro effects can spread to similar mobs.").defineInRange("chainAggroRadius", 8.0, 0.0, 32.0);
                this.exemptEntities = builder.comment("LEGACY: Entities that will not aggro from gunfire. Use the 'scguns:aggro_from_guns' entity tag for better mod compatibility.").defineList("exemptMobs", Collections.emptyList(), o -> true);
            }
            builder.pop();
        }
    }
    public static class FleeingMobs {
        public final ForgeConfigSpec.BooleanValue enabled;
        public final ForgeConfigSpec.DoubleValue silencedRange;
        public final ForgeConfigSpec.DoubleValue unsilencedRange;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> fleeingEntities;

        public FleeingMobs(ForgeConfigSpec.Builder builder) {
            builder.comment("Properties relating to mob fleeing").push("fleeing_mobs");
            {
                this.enabled = builder.comment("If true, nearby mobs will flee from the firing of guns.").define("enabled", true);
                this.unsilencedRange = builder.comment("Any mobs within a sphere of this radius will flee from the shooter of an unsilenced gun.").defineInRange("unsilencedRange", 20.0, 0.0, Double.MAX_VALUE);
                this.silencedRange = builder.comment("Any mobs within a sphere of this radius will flee from the shooter of a silenced gun.").defineInRange("silencedRange", 5.0, 0.0, Double.MAX_VALUE);
                this.fleeingEntities = builder.comment("LEGACY: Additional entities that will flee from gunshots. Animals are automatically detected. Use the 'scguns:fleeing_from_guns' entity tag instead for better mod compatibility.").defineList("fleeingEntities", Arrays.asList("minecraft:villager"), o -> o instanceof String);
            }
            builder.pop();
        }
    }

    /**
     * Missile related config options
     */
    public static class Rockets
    {
        public final ForgeConfigSpec.DoubleValue explosionRadius;

        public Rockets(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to rockets").push("rockets");
            {
                this.explosionRadius = builder.comment("The max distance which the explosion is effective to").defineInRange("explosionRadius", 4.0, 0.0, Double.MAX_VALUE);
            }
            builder.pop();
        }
    }

    /**
     * Grenade related config options
     */
    public static class Grenades
    {
        public final ForgeConfigSpec.DoubleValue explosionRadius;

        public Grenades(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to grenades").push("grenades");
            {
                this.explosionRadius = builder.comment("The max distance which the explosion is effective to").defineInRange("explosionRadius", 5.0, 0.0, Double.MAX_VALUE);
            }
            builder.pop();
        }
    }
    /**
     * Gunsmith related config options
     */
    public static class Gunsmith
    {
        public Gunsmith(ForgeConfigSpec.Builder builder) {
            builder.comment("Properties relating to gunsmith").push("gunsmith");
            {

            }
            builder.pop();
        }
    }
    public static class Turret
    {
        public final Map<TurretProjectileEntity.BulletType, ForgeConfigSpec.DoubleValue> bulletDamage;
        public final ForgeConfigSpec.BooleanValue enableDamageScaling;
        public final ForgeConfigSpec.DoubleValue damageScalingRate;
        public final ForgeConfigSpec.DoubleValue maxScaledDamage;

        public Turret(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to turrets").push("turret");
            {
                bulletDamage = new EnumMap<>(TurretProjectileEntity.BulletType.class);
                for (TurretProjectileEntity.BulletType type : TurretProjectileEntity.BulletType.values()) {
                    bulletDamage.put(type, builder
                            .comment("Base damage for " + type.name() + " turret projectile")
                            .defineInRange(type.name().toLowerCase() + "_damage", getDefaultDamage(type), 0.0, Double.MAX_VALUE));
                }

                this.enableDamageScaling = builder
                        .comment("If true, turret damage will scale over time")
                        .define("enable_damage_scaling", false);

                this.damageScalingRate = builder
                        .comment("The rate at which turret damage increases per day")
                        .defineInRange("damage_scaling_rate", 0.03, 0.0, Double.MAX_VALUE);

                this.maxScaledDamage = builder
                        .comment("The maximum damage that turret scaling can reach")
                        .defineInRange("max_scaled_damage", Double.MAX_VALUE, 0.0, Double.MAX_VALUE);
            }
            builder.pop();
        }

        private double getDefaultDamage(TurretProjectileEntity.BulletType type) {
            return switch (type) {
                case STANDARD_COPPER_ROUND -> 6.0;
                case ADVANCED_ROUND -> 8.0;
                case GIBBS_ROUND -> 10.0;
                case COMPACT_COPPER_ROUND -> 5.0;
                case COMPACT_ADVANCED_ROUND ->6.5;
                case HOG_ROUND -> 7.0;
                case SHOTGUN_SHELL -> 22.0;
                case BEARPACK_SHELL -> 28.0;
            };
        }
    }
    /**
     * Stun Grenade related config options
     */
    public static class StunGrenades
    {
        public final Blind blind;
        public final Deafen deafen;

        public StunGrenades(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to stun grenades").push("stun_grenades");
            {
                this.blind = new Blind(builder);
                this.deafen = new Deafen(builder);
            }
            builder.pop();
        }
    }

    /**
     * Stun grenade blinding related config options
     */
    public static class Blind
    {
        public final EffectCriteria criteria;
        public final ForgeConfigSpec.BooleanValue blindMobs;

        public Blind(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Blinding properties of stun grenades").push("blind");
            {
                this.criteria = new EffectCriteria(builder, 15, 220, 10, 170, 0.75, true);
                this.blindMobs = builder.comment("If true, hostile mobs will be unable to target entities while they are blinded by a stun grenade.").define("blindMobs", true);
            }
            builder.pop();
        }
    }

    /**
     * Stun grenade deafening related config options
     */
    public static class Deafen
    {
        public final EffectCriteria criteria;
        public final ForgeConfigSpec.BooleanValue panicMobs;

        public Deafen(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Deafening properties of stun grenades").push("deafen");
            {
                this.criteria = new EffectCriteria(builder, 15, 280, 100, 360, 0.75, false);
                this.panicMobs = builder.comment("If true, peaceful mobs will panic upon being deafened by a stun grenade.").define("panicMobs", true);
            }
            builder.pop();
        }
    }

    /**
     * Config options for effect criteria
     */
    public static class EffectCriteria
    {
        public final ForgeConfigSpec.DoubleValue radius;
        public final ForgeConfigSpec.IntValue durationMax;
        public final ForgeConfigSpec.IntValue durationMin;
        public final ForgeConfigSpec.DoubleValue angleEffect;
        public final ForgeConfigSpec.DoubleValue angleAttenuationMax;
        public final ForgeConfigSpec.BooleanValue raytraceOpaqueBlocks;

        public EffectCriteria(ForgeConfigSpec.Builder builder, double radius, int durationMax, int durationMin, double angleEffect, double angleAttenuationMax, boolean raytraceOpaqueBlocks)
        {
            builder.push("effect_criteria");
            {
                this.radius = builder.comment("Grenade must be no more than this many meters away to have an effect.").defineInRange("radius", radius, 0.0, Double.MAX_VALUE);
                this.durationMax = builder.comment("Effect will have this duration (in ticks) if the grenade is directly at the player's eyes while looking directly at it.").defineInRange("durationMax", durationMax, 0, Integer.MAX_VALUE);
                this.durationMin = builder.comment("Effect will have this duration (in ticks) if the grenade is the maximum distance from the player's eyes while looking directly at it.").defineInRange("durationMin", durationMin, 0, Integer.MAX_VALUE);
                this.angleEffect = builder.comment("Angle between the eye/looking direction and the eye/grenade direction must be no more than half this many degrees to have an effect.").defineInRange("angleEffect", angleEffect, 0, 360);
                this.angleAttenuationMax = builder.comment("After duration is attenuated by distance, it will be further attenuated depending on the angle (in degrees) between the eye/looking direction and the eye/grenade direction. This is done by multiplying it by 1 (no attenuation) if the angle is 0; and by this value if the angle is the maximum within the angle of effect.").defineInRange("angleAttenuationMax", angleAttenuationMax, 0.0, 1.0);
                this.raytraceOpaqueBlocks = builder.comment("If true, the effect is only applied if the line between the eyes and the grenade does not intersect any non-liquid blocks with an opacity greater than 0.").define("raytraceOpaqueBlocks", raytraceOpaqueBlocks);
            }
            builder.pop();
        }
    }

    /**
     * Projectile spread config options
     */
    public static class ProjectileSpread
    {
        public final ForgeConfigSpec.IntValue spreadThreshold;
        public final ForgeConfigSpec.IntValue maxCount;

        public ProjectileSpread(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Properties relating to projectile spread").push("projectile_spread");
            {
                this.spreadThreshold = builder.comment("The amount of time in milliseconds before logic to apply spread is skipped. The value indicates a reasonable amount of time before a weapon is considered stable again.").defineInRange("spreadThreshold", 300, 0, 1000);
                this.maxCount = builder.comment("The amount of times a player has to shoot within the spread threshold before the maximum amount of spread is applied. Setting the value higher means it will take longer for the spread to be applied.").defineInRange("maxCount", 10, 1, Integer.MAX_VALUE);
            }
            builder.pop();
        }
    }
    public static class GunScalingConfig {
        private static final ForgeConfigSpec SPEC;
        public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        private static final GunScalingConfig INSTANCE = new GunScalingConfig();

        private static final ForgeConfigSpec.BooleanValue enableScalingDamage;
        private static final ForgeConfigSpec.DoubleValue damageIncreaseRate;
        private static final ForgeConfigSpec.DoubleValue baseDamage;
        private static final ForgeConfigSpec.DoubleValue maxDamage;

        static {
            BUILDER.push("Scaling Damage");

            enableScalingDamage = BUILDER
                    .comment("If true, gun damage will scale with the days in the world.")
                    .define("Enable Scaling Damage", false);

            damageIncreaseRate = BUILDER
                    .comment("The decimal amount that gun damage increases per day.")
                    .defineInRange("Damage Increase Rate", 0.03, 0.0, Double.POSITIVE_INFINITY);

            baseDamage = BUILDER
                    .comment("The base damage value for the guns.")
                    .defineInRange("Base Damage", 1.0, 0.0, Double.POSITIVE_INFINITY);

            maxDamage = BUILDER
                    .comment("The maximum damage that gun scaling can reach.")
                    .defineInRange("Max Scaled Damage", Double.POSITIVE_INFINITY, 0.0, Double.POSITIVE_INFINITY);

            BUILDER.pop();

            SPEC = BUILDER.build();
        }

        // Getters
        public boolean isScalingEnabled() {
            return enableScalingDamage.get();
        }

        public double getDamageIncreaseRate() {
            return damageIncreaseRate.get();
        }

        public double getBaseDamage() {
            return baseDamage.get();
        }

        public double getMaxDamage() {
            return maxDamage.get();
        }

        // Setters
        public void setScalingEnabled(boolean enabled) {
            enableScalingDamage.set(enabled);
        }

        public void setDamageIncreaseRate(double rate) {
            damageIncreaseRate.set(rate);
        }

        public void setBaseDamage(double damage) {
            baseDamage.set(damage);
        }

        public void setMaxDamage(double max) {
            maxDamage.set(max);
        }

        public static void setup() {
            Path configPath = FMLPaths.CONFIGDIR.get();
            Path gunConfigPath = Paths.get(configPath.toAbsolutePath().toString(), "gun_scaling");

            // Create the config folder
            try {
                Files.createDirectory(gunConfigPath);
            } catch (Exception e) {
                // Do nothing
            }

            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "gun_scaling/main.toml");
        }

        public static GunScalingConfig getInstance() {
            return INSTANCE;
        }

        public void save() {
            SPEC.save();
        }
    }

    /**
     * Server related config options
     */
    public static class Server
    {
        public final ForgeConfigSpec.IntValue alphaOverlay;
        public final ForgeConfigSpec.IntValue alphaFadeThreshold;
        public final ForgeConfigSpec.DoubleValue soundPercentage;
        public final ForgeConfigSpec.IntValue soundFadeThreshold;
        public final ForgeConfigSpec.DoubleValue ringVolume;
        public final ForgeConfigSpec.DoubleValue gunShotMaxDistance;
        public final ForgeConfigSpec.DoubleValue reloadMaxDistance;
        public final ForgeConfigSpec.BooleanValue enableCameraRecoil;
        public final ForgeConfigSpec.IntValue cooldownThreshold;
        public final Experimental experimental;

        public Server(ForgeConfigSpec.Builder builder)
        {
            builder.push("server");
            {
                builder.comment("Stun Grenade related properties").push("grenade");
                {
                    this.alphaOverlay = builder.comment("After the duration drops to this many ticks, the transparency of the overlay when blinded will gradually fade to 0 alpha.").defineInRange("alphaOverlay", 255, 0, 255);
                    this.alphaFadeThreshold = builder.comment("Transparency of the overlay when blinded will be this alpha value, before eventually fading to 0 alpha.").defineInRange("alphaFadeThreshold", 40, 0, Integer.MAX_VALUE);
                    this.soundPercentage = builder.comment("Volume of most game sounds when deafened will play at this percent, before eventually fading back to %100.").defineInRange("soundPercentage", 0.05, 0.0, 1.0);
                    this.soundFadeThreshold = builder.comment("After the duration drops to this many ticks, the ringing volume will gradually fade to 0 and other sound volumes will fade back to %100.").defineInRange("soundFadeThreshold", 90, 0, Integer.MAX_VALUE);
                    this.ringVolume = builder.comment("Volume of the ringing sound when deafened will play at this volume, before eventually fading to 0.").defineInRange("ringVolume", 1.0, 0.0, 1.0);
                }
                builder.pop();

                builder.comment("Audio properties").push("audio");
                {
                    this.gunShotMaxDistance = builder.comment("The maximum distance weapons can be heard by players.").defineInRange("gunShotMaxDistance", 100, 0, Double.MAX_VALUE);
                    this.reloadMaxDistance = builder.comment("The maximum distance reloading can be heard by players.").defineInRange("reloadMaxDistance", 24, 0, Double.MAX_VALUE);
                }
                builder.pop();

                this.enableCameraRecoil = builder.comment("If true, enables camera recoil when firing a weapon").define("enableCameraRecoil", true);
                this.cooldownThreshold = builder.comment("The maximum amount of cooldown time remaining before the server will accept another shoot packet from a client. This allows for a litle slack since the server may be lagging").defineInRange("cooldownThreshold", 0, 75, 1000);

                this.experimental = new Experimental(builder);
            }
            builder.pop();
        }

        public static class Experimental
        {
            public final ForgeConfigSpec.BooleanValue forceDyeableAttachments;

            public Experimental(ForgeConfigSpec.Builder builder)
            {
                builder.push("experimental");
                this.forceDyeableAttachments = builder.comment("Forces all attachments to be dyeable regardless if they have an affect on the model. This is useful if your server uses custom models for attachments and the models have dyeable elements").define("forceDyeableAttachments", false);
                builder.pop();
            }
        }
    }

    static final ForgeConfigSpec clientSpec;
    public static final Config.Client CLIENT;

    static final ForgeConfigSpec commonSpec;
    public static final Config.Common COMMON;

    static final ForgeConfigSpec serverSpec;
    public static final Config.Server SERVER;

    static
    {
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Config.Client::new);
        clientSpec = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();

        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();
    }

    public static void saveClientConfig()
    {
        clientSpec.save();
    }
}
