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
	
	public static final String TAG_NAME = "FoodMetadata";
	
	public static final String EATING_TIME = "EatingTime";
	
	public FoodMetaDataStructure(NBTTagCompound compound){
		
		
	}
	
	/**
	 * get FoodMetadataNBT from NBTTagCompound.
	 * @param compound Parent NBTTagCompound.
	 * @return FoodMetadataNBTTagCompound.
	 */
	public static NBTTagCompound getFoodMetaDataStructureNBTTagCompound(NBTTagCompound compound) {
		if (compound == null) return null;
		if (!compound.hasKey(TAG_NAME, NBTTagType.COMPOUND.ordinal())) return null;
		return compound.getCompoundTag(TAG_NAME);
	}
	
	public static int getEatingTime(NBTTagCompound compound) {
		if (compound == null) return 32;
		int time = compound.getInteger(EATING_TIME);
		return time <= 0 ? 32 : time;
	}
}
