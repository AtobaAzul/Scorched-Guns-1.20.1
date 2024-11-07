package top.ribs.scguns.init;

import immersive_aircraft.WeaponRegistry;
import immersive_aircraft.cobalt.registration.Registration;
import immersive_aircraft.entity.misc.WeaponMount;
import immersive_aircraft.item.WeaponItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import top.ribs.scguns.entity.weapon.AircraftMachineGun;

import static top.ribs.scguns.Reference.MOD_ID;
import static immersive_aircraft.Items.baseProps;

public class AircraftItems {
    public static void init() {

        WeaponRegistry.register(new ResourceLocation(MOD_ID, "aircraft_machinegun"), AircraftMachineGun::new);

        Registration.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "aircraft_machinegun"), () -> new WeaponItem(baseProps().stacksTo(1), WeaponMount.Type.FRONT));
    }
}
