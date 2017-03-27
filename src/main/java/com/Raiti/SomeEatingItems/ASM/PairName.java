package com.Raiti.SomeEatingItems.ASM;

import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import org.jetbrains.annotations.NotNull;

/**
 * Pair of mpcName and srgName.
 * <br>Created by Raiti-chan on 2017/03/08.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PairName {
	
	/**
	 * Method PairName object at {@link net.minecraft.client.renderer.ItemRenderer#renderItemInFirstPerson(float) ItemRenderer#renderItemInFirstPerson(float)}
	 */
	public static final PairName RENDER_ITEM_IN_FIRST_PERSON = new PairName("renderItemInFirstPerson", "func_78440_a");
	
	/**
	 * Method PairName object at {@link ItemStack#getItemUseAction() ItemStack#getItemUseAction()}
	 */
	public static final PairName GET_ITEM_USE_ACTION = new PairName("getItemUseAction", "func_77975_n");
	
	/**
	 * Method PairName object at {@link ItemStack#getMaxItemUseDuration() ItemStack#getMaxItemUseDuration()}
	 */
	public static final PairName GET_MAX_ITEM_USE_DURATION = new PairName("getMaxItemUseDuration", "func_77626_a");
	
	
	public static final PairName GET_ITEM_IN_USE_COUNT = new PairName("getItemInUseCount", "func_71052_bv");
	
	/**
	 * MPC Name
	 */
	private final String mpcName;
	
	/**
	 * SRG Name
	 */
	private final String srgName;
	
	/**
	 * Name set.
	 * @param mpcName mpc name.
	 * @param srgName srg name.
	 */
	public PairName(@NotNull String mpcName, @NotNull String srgName){
		this.mpcName = mpcName;
		this.srgName = srgName;
	}
	
	/**
	 * If mpcName equal srgName, this constructor can use.
	 * @param name mpc and srg name.
	 */
	public PairName(@NotNull String name){
		this.mpcName = name;
		this.srgName = name;
	}
	
	/**
	 * Get this name.
	 * If {@link PairName#useSrgName()} is return true, return srgName.
	 * @return If {@link PairName#useSrgName()} is return true, return srgName, else return mpcName.
	 */
	public @NotNull String name() {
		return useSrgName() ? this.srgName : this.mpcName;
	}
	
	/**
	 * Get {@link PairName#srgName SRG Name}.
	 * @return SRG Name
	 */
	public @NotNull String getSrgName() {
		return srgName;
	}
	
	/**
	 * Get {@link PairName#mpcName MPC Name}.
	 * @return MPC Name
	 */
	public @NotNull String getMpcName() {
		return mpcName;
	}
	
	/**
	 * This object to string so its format is"MPCName:SRGName".
	 * @return MPCName:SRGName
	 */
	@Override
	public String toString () {
		return String.format("%s:%s", this.mpcName, this.srgName);
	}
	
	/**
	 * Get whether FML is using a SRG name.
	 *
	 * @return If FML is using a SRG name, return true.
	 */
	public static boolean useSrgName () {
		Boolean bool = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
		return bool == null || !bool;
	}
	
}
