package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2damagetracker.init.data.DamageTypeAndTagsGen;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class DamageTypeGen extends DamageTypeAndTagsGen {

	public static final ResourceKey<DamageType> BLEED = create("bleed", "%s bleed to death");
	public static final ResourceKey<DamageType> LIFE_SYNC = create("life_sync", "%s was drained");
	public static final ResourceKey<DamageType> VOID_EYE = create("void_eye", "%s was cursed by void eye");

	public static final ResourceKey<DamageType> EMERALD = create("emerald",
			"%s was killed by emerald splash", "%s was killed by emerald splash from %s");
	public static final ResourceKey<DamageType> SOUL_FLAME = create("soul_flame",
			"%s has its soul burnt out", "%s has its soul burnt out by %s");

	public static void register() {

	}

	public DamageTypeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> pvd, ExistingFileHelper helper) {
		super(output, pvd, helper, L2Complements.MODID);
		new DamageTypeHolder(EMERALD, new DamageType("emerald", DamageScaling.NEVER, 0.1f))
				.add(DamageTypeTags.AVOIDS_GUARDIAN_THORNS);

		new DamageTypeHolder(SOUL_FLAME, new DamageType("soul_flame", DamageScaling.NEVER, 0, DamageEffects.BURNING))
				.add(DamageTypeTags.BYPASSES_ARMOR, L2DamageTypes.MAGIC, DamageTypeTags.AVOIDS_GUARDIAN_THORNS, L2DamageTypes.NO_SCALE, DamageTypeTags.NO_IMPACT);
		new DamageTypeHolder(BLEED, new DamageType("bleed", DamageScaling.NEVER, 0.1f))
				.add(DamageTypeTags.BYPASSES_ARMOR, L2DamageTypes.NO_SCALE, DamageTypeTags.NO_IMPACT).add(L2DamageTypes.BYPASS_MAGIC);
		new DamageTypeHolder(LIFE_SYNC, new DamageType("life_sync", DamageScaling.NEVER, 0f))
				.add(DamageTypeTags.BYPASSES_ARMOR, L2DamageTypes.NO_SCALE, DamageTypeTags.NO_IMPACT).add(L2DamageTypes.BYPASS_MAGIC);
		new DamageTypeHolder(VOID_EYE, new DamageType("void_eye", DamageScaling.NEVER, 0f))
				.add(DamageTypeTags.NO_IMPACT).add(L2DamageTypes.BYPASS_INVUL);
	}

	public static Holder<DamageType> forKey(Level level, ResourceKey<DamageType> key) {
		return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
	}

	private static ResourceKey<DamageType> create(String id, String msg) {
		return create(id, msg, msg);
	}

	private static ResourceKey<DamageType> create(String id, String msg, String player) {
		return create(id, msg, player, player);
	}

	private static ResourceKey<DamageType> create(String id, String msg, String player, String item) {
		L2Complements.REGISTRATE.addRawLang("death.attack." + id, msg);
		L2Complements.REGISTRATE.addRawLang("death.attack." + id + ".player", player);
		L2Complements.REGISTRATE.addRawLang("death.attack." + id + ".item", item);
		return create(id);
	}

	private static ResourceKey<DamageType> create(String id) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(L2Complements.MODID, id));
	}

}
