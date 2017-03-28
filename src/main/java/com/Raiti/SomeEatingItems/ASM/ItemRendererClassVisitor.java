package com.Raiti.SomeEatingItems.ASM;


import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * Over writ of {@link net.minecraft.client.renderer.ItemRenderer ItemRenderer} Class.
 * <br>Created by Raiti-chan on 2017/03/07.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ItemRendererClassVisitor extends ClassVisitor {
	
	
	/**
	 * {@link net.minecraft.client.renderer.ItemRenderer#renderItemInFirstPerson(float) ItemRenderer#renderItemInFirstPerson(float)} Method Name Object.
	 */
	private final MethodName renderItemInFirstPerson;
	
	private final MethodName transformEatFirstPerson;
	
	/**
	 * Constructs a new {@link ClassVisitor}.
	 *
	 * @param className className.
	 * @param cv        the class visitor to which this visitor must delegate method
	 */
	ItemRendererClassVisitor (@NotNull String className, @NotNull ClassVisitor cv) {
		super(ASM5, cv);
		renderItemInFirstPerson = new MethodName(className, PairName.RENDER_ITEM_IN_FIRST_PERSON, "(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V");
		transformEatFirstPerson = new MethodName(className, PairName.TRANSFORM_EAT_FIRST_PERSON, "(FLnet/minecraft/util/EnumHandSide;Lnet/minecraft/item/ItemStack;)V");
	}
	
	
	/**
	 * Visits a method of the class. This method <i>must</i> return a new
	 * {@link MethodVisitor} instance (or <tt>null</tt>) each time it is called,
	 * i.e., it should not return a previously returned visitor.
	 *
	 * @param access     the method's access flags (see {@link Opcodes}). This
	 *                   parameter also indicates if the method is synthetic and/or
	 *                   deprecated.
	 * @param name       the method's name.
	 * @param desc       the method's descriptor (see {@link Type Type}).
	 * @param signature  the method's signature. May be <tt>null</tt> if the method
	 *                   parameters, return type and exceptions do not use generic
	 *                   types.
	 * @param exceptions the internal names of the method's exception classes (see
	 *                   {@link Type#getInternalName() getInternalName}). May be
	 *                   <tt>null</tt>.
	 * @return an object to visit the byte code of the method, or <tt>null</tt>
	 * if this class visitor is not interested in visiting the code of
	 * this method.
	 */
	@Override
	public MethodVisitor visitMethod (int access, String name, String desc, String signature, String[] exceptions) {
		final MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
		if (this.renderItemInFirstPerson.match(name, desc)) return new DebugUtil.OutputJVMASMCode(visitor);
		if (this.transformEatFirstPerson.match(name, desc)) return new DebugUtil.OutputJVMASMCode(visitor);
		
		return visitor;
	}
	
	
}
