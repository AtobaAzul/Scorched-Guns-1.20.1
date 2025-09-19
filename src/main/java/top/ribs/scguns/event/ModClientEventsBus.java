package top.ribs.scguns.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.ribs.scguns.Reference;
import top.ribs.scguns.entity.client.*;
import top.ribs.scguns.item.GunItem;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventsBus {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.COG_MINION_LAYER, CogMinionModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.COG_KNIGHT_LAYER, CogKnightModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TRAUMA_UNIT_LAYER, TraumaUnitModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SKY_CARRIER_LAYER, SkyCarrierModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SUPPLY_SCAMP_LAYER, SupplyScampModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.REDCOAT_LAYER, RedcoatModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BLUNDERER_LAYER, BlundererModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DISSIDENT_LAYER, DissidentModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.HIVE_LAYER, HiveModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SWARM_LAYER, SwarmModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.HORNLIN_LAYER, HornlinModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ZOMBIFIED_HORNLIN_LAYER, ZombifiedHornlinModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.THE_MERCHANT_LAYER, TheMerchantModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SIGNAL_BEACON_LAYER, SignalBeaconModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SCAMP_TANK_LAYER, ScampTankModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SCAMP_ROCKET_LAYER, ScampRocketModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SCAMPLER_LAYER, ScamplerModel::createBodyLayer);
    }
}

