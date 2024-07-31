package dev.xkmc.l2complements.content.item.curios;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class FireproofItem extends Item {

	public FireproofItem(Properties properties) {
		super(properties.stacksTo(1).fireResistant().rarity(Rarity.EPIC));
	}

	public FireproofItem(Properties properties, int durability) {
		super(properties.durability(durability).fireResistant().rarity(Rarity.EPIC));
	}

}