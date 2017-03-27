package com.Raiti.SomeEatingItems.ASM;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.jetbrains.annotations.NotNull;

/**
 * This class express Method name.<br>
 * This class is Immutable class.
 * <br>Created by Raiti-chan on 2017/03/07.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
public class MethodName {
	
	/**
	 * ClassName
	 */
	@NotNull
	private final String className;
	
	/**
	 * MethodName
	 */
	@NotNull
	private final PairName methodName;
	
	/**
	 * Method's descriptor
	 */
	@NotNull
	private final String descriptor;
	
	
	/**
	 * Method Name.
	 *
	 * @param className  class name.
	 * @param methodName method name.
	 * @param descriptor method descriptor.
	 */
	public MethodName (@NotNull String className, @NotNull PairName methodName, @NotNull String descriptor) {
		this.className = className;
		this.methodName = methodName;
		this.descriptor = descriptor;
	}
	
	
	/**
	 * Compare if method is the same.
	 *
	 * @param methodName MethodName.
	 * @param descriptor MethodDescriptor.
	 * @return If this method equal  methodName and methodDescriptor, return true.
	 */
	public boolean match (final @NotNull String methodName, final @NotNull String descriptor) {
		System.out.println(this.methodName.getMpcName() + "==" + methodName + " : " + this.descriptor + "==" + descriptor);
		if (this.methodName.getMpcName().equals(methodName) && this.descriptor.equals(descriptor)) return true;
		if (!PairName.useSrgName()) return false;
		String unmappedDesc = FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(descriptor);
		System.out.println(this.descriptor + "==" + unmappedDesc);
		if (!unmappedDesc.equals(this.descriptor)) return false;
		String unmappedName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(this.className, methodName, descriptor);
		System.out.println(this.methodName.getSrgName() + "==" + unmappedName);
		return unmappedName.equals(this.methodName.getSrgName());
	}
	
	
	/**
	 * Get to mapped class name from MCPName.
	 * @param className Class name of MCPName.
	 * @return MappedName. If {@link PairName#useSrgName()} return is true , return MappedName , else return MCPName
	 */
	public static String getMappedClassName (String className) {
		return PairName.useSrgName() ? FMLDeobfuscatingRemapper.INSTANCE.unmap(className) : className;
	}
}
