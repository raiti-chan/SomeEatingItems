package com.Raiti.SomeEatingItems.ASM;



import com.Raiti.SomeEatingItems.FoodMetaDataStructure;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.nbt.NBTTagCompound;
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
	
	private static final String TARGET_CLASS = "net.minecraft.client.renderer.ItemRenderer";
	
	@Override
	public byte[] transform (String name, String transformedName, byte[] basicClass) {
		if (FMLLaunchHandler.side() == Side.SERVER || !transformedName.equals(TARGET_CLASS)) return basicClass;
		ClassReader reader = new ClassReader(basicClass);
		ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		ClassVisitor visitor = new ItemRendererClassVisitor(name, writer);
		
		reader.accept(visitor, 0);
		return writer.toByteArray();
	}
	
	
	@SuppressWarnings("unused")
	public static boolean checkItem (ItemStack stack) {
		return stack.getItemUseAction() == EnumAction.eat || stack.getItemUseAction() == EnumAction.drink || FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(stack.getTagCompound()) != null;
	}
	
	
	@SuppressWarnings("unused")
	public static int getMaxItemUseDuration(ItemStack stack) {
		NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(stack.getTagCompound());
		if (compound == null) return stack.getMaxItemUseDuration();
		int eatingTime = compound.getInteger("EatingTime");
		return (eatingTime <= 0 ? 32 : eatingTime);
	}
	
	@SuppressWarnings("unused")
	public static int getItemInUseCount(EntityClientPlayerMP playerMP) {
		ItemStack stack = playerMP.getItemInUse();
		if (stack == null) return playerMP.getItemInUseCount();
		if (FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(stack.getTagCompound()) == null) return playerMP.getItemInUseCount();
		return playerMP.getItemInUseCount() - 26;
	}
}
