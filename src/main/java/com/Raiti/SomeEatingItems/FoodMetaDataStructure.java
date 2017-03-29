package com.Raiti.SomeEatingItems;

import net.minecraft.nbt.NBTTagCompound;

/**
 * <br>Created by Raiti-chan on 2017/03/04.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
public class FoodMetaDataStructure {
	
	public FoodMetaDataStructure(NBTTagCompound compound){
		
		
	}
	
	/**
	 * get FoodMetadataNBT from NBTTagCompound.
	 * @param compound Parent NBTTagCompound.
	 * @return FoodMetadataNBTTagCompound.
	 */
	public static NBTTagCompound getFoodMetaDataStructureNBTTagCompound(NBTTagCompound compound) {
		if (compound == null) return null;
		return compound.getCompoundTag("FoodMetadata");
	}
	
	public static int getEatingTime(NBTTagCompound compound) {
		if (compound == null) return 32;
		int time = compound.getInteger("EatingTime");
		return time <= 0 ? 32 : time;
	}
}
