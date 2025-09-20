package top.ribs.scguns.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import top.ribs.scguns.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class ModTags
{
    public static Map<ResourceLocation, TagKey<Block>> blockTagCache = new HashMap<>();

    public static class Blocks
    {
        public static final TagKey<Block> FRAGILE = tag("fragile");
        public static final TagKey<Block> SCULK_BLOCKS = tag("sculk_blocks");
        public static final TagKey<Block> TANK_BREAKABLE = tag("tank_breakable");
        private static TagKey<Block> tag(String name)
        {
            return BlockTags.create(new ResourceLocation(Reference.MOD_ID, name));
        }
        private static TagKey<Block> tag()
        {
            return BlockTags.create(new ResourceLocation(Reference.MOD_ID, "fragile"));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> DOES_NOT_EJECT_CASINGS = tag("does_not_eject_casings");
        public static final TagKey<Item> SINGLE_SHOT = tag("single_shot");
        public static final TagKey<Item> NON_COLLATERAL = tag("non_collateral");
        public static final TagKey<Item> NON_UNDERWATER = tag("non_underwater");
        public static final TagKey<Item> ONE_HANDED_CARBINE  = tag("one_handed_carbine");
        public static final TagKey<Item> HEAVY_WEAPON = tag("heavy_weapon");
        public static final TagKey<Item> OCEAN_GUN = tag("ocean_gun");
        public static final TagKey<Item> PIGLIN_GUN = tag("piglin_gun");
        public static final TagKey<Item> BUILT_IN_BAYONET  = tag("built_in_bayonet");
        public static final TagKey<Item> WEAK_FILTER = tag("weak_filter");
        public static final TagKey<Item> STRONG_FILTER = tag("strong_filter");
        public static final TagKey<Item> WEAK_COMPOST = tag("weak_compost");
        public static final TagKey<Item> NORMAL_COMPOST = tag("normal_compost");
        public static final TagKey<Item> STRONG_COMPOST = tag("strong_compost");
        public static final TagKey<Item> COMPOST_DROPS= tag("compost_drops");
        public static final TagKey<Item> GAS_MASK = tag("gas_mask");
        public static final TagKey<Item> EXPLOSIVE_BLOCK = tag("explosive_block");
        public static final TagKey<Item> GEOTHERMAL_VENT_OUTPUT = tag("geothermal_vent_output");
        public static final TagKey<Item> SULFUR_VENT_OUTPUT = tag("sulfur_vent_output");
        public static final TagKey<Item> MINING_GUN = tag("mining_gun");

        private static TagKey<Item> tag(String name)
        {
            return ItemTags.create(new ResourceLocation(Reference.MOD_ID, name));
        }
    }
    public static class Entities
    {

        public static final TagKey<EntityType<?>> RED_BLOOD = tag("red_blood");
        public static final TagKey<EntityType<?>> WHITE_BLOOD = tag("white_blood");
        public static final TagKey<EntityType<?>> GREEN_BLOOD = tag("green_blood");
        public static final TagKey<EntityType<?>> BLUE_BLOOD = tag("blue_blood");
        public static final TagKey<EntityType<?>> YELLOW_BLOOD = tag("yellow_blood");
        public static final TagKey<EntityType<?>> PURPLE_BLOOD = tag("purple_blood");
        public static final TagKey<EntityType<?>> BLACK_BLOOD = tag("black_blood");

        public static final TagKey<EntityType<?>> NON_SWARM_TARGETED = tag("non_swarm_targeted");
        public static final TagKey<EntityType<?>> FLEEING_FROM_GUNS = tag("fleeing_from_guns");
        public static final TagKey<EntityType<?>> AGGRO_FROM_GUNS = tag("aggro_from_guns");


        public static final TagKey<EntityType<?>> NONE = tag("none");
        public static final TagKey<EntityType<?>> HEAVY = tag("heavy");
        public static final TagKey<EntityType<?>> VERY_HEAVY = tag("very_heavy");
        public static final TagKey<EntityType<?>> UNDEAD = tag("undead");
        public static final TagKey<EntityType<?>> GHOST = tag("ghost");
        public static final TagKey<EntityType<?>> WITHER = tag("wither");
        public static final TagKey<EntityType<?>> FIRE = tag("fire");
        public static final TagKey<EntityType<?>> ILLAGER = tag("illager");
        public static final TagKey<EntityType<?>> BOT = tag("bot");
        public static final TagKey<EntityType<?>> WATER = tag("water");
        public static final TagKey<EntityType<?>> TURRET_BLACKLIST = tag("turret_blacklist");

        public static TagKey<EntityType<?>> tag(String name)
        {
            return TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation(Reference.MOD_ID, name));
        }
    }
}
