package com.Raiti.SomeEatingItems.ASM;



import java.util.function.Function;

import net.minecraft.client.entity.EntityPlayerSP;

import com.Raiti.SomeEatingItems.FoodMetaDataStructure;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * <br>Created by Raiti-chan on 2017/03/06.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class SomeEatingItemsTransformer implements IClassTransformer {
	
	private static final String ITEM_RENDERER = "net.minecraft.client.renderer.ItemRenderer";
	
	@Override
	public byte[] transform (final String name, final String transformedName, final byte[] basicClass) {
		if (FMLLaunchHandler.side() == Side.SERVER || !transformedName.equals(ITEM_RENDERER)) return basicClass;
		if (transformedName.equals(ITEM_RENDERER)) return getClassWriter(basicClass, classWriter -> new ItemRendererClassVisitor(name, classWriter)).toByteArray();
		return basicClass;
	}
	
	
	private static ClassWriter getClassWriter (byte[] basicClass, Function<ClassWriter, ClassVisitor> function) {
		ClassReader reader = new ClassReader(basicClass);
		ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		ClassVisitor visitor = function.apply(writer);
		reader.accept(visitor, 0);
		return writer;
	}
	
	
	@SuppressWarnings("unused")
	public static boolean checkItem (ItemStack stack) {
		return stack.getItemUseAction() == EnumAction.EAT || stack.getItemUseAction() == EnumAction.DRINK || FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(stack.getTagCompound()) != null;
	}
	
	
	@SuppressWarnings("unused")
	public static int getMaxItemUseDuration(ItemStack stack) {
		NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(stack.getTagCompound());
		if (compound == null) return stack.getMaxItemUseDuration();
		int eatingTime = compound.getInteger("EatingTime");
		return (eatingTime <= 0 ? 32 : eatingTime);
	}
	
	@SuppressWarnings("unused")
	public static int getItemInUseCount(EntityPlayerSP player) {
		ItemStack stack = player.getActiveItemStack();
		if (FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(stack.getTagCompound()) == null) return player.getItemInUseCount();
		return player.getItemInUseCount() - 26;
	}
}
