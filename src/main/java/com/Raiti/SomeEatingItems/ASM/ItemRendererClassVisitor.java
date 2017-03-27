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
	
	/**
	 * Constructs a new {@link ClassVisitor}.
	 *
	 * @param className className.
	 * @param cv        the class visitor to which this visitor must delegate method
	 */
	ItemRendererClassVisitor (@NotNull String className, @NotNull ClassVisitor cv) {
		super(ASM5, cv);
		renderItemInFirstPerson = new MethodName(className, PairName.RENDER_ITEM_IN_FIRST_PERSON, "(F)V");
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
		
		if (this.renderItemInFirstPerson.match(name, desc)) return new RenderItemInFirstPersonMethodVisitor(visitor);
		
		
		return visitor;
	}
	
	/**
	 * Over writ of {@link net.minecraft.client.renderer.ItemRenderer#renderItemInFirstPerson(float) ItemRenderer#renderItemInFirstPerson(float)} Method.
	 * <br>Created by Raiti-chan on 2017/03/07.
	 *
	 * @author Raiti-chan
	 * @version 1.0.0
	 * @since 1.0.0
	 */
	private class RenderItemInFirstPersonMethodVisitor extends MethodVisitor {
		
		private final MethodName triggerMethod_1;
		
		private final MethodName triggerMethod_2;
		
		private final MethodName triggerMethod_3;
		
		private boolean flag_1 = false;
		
		private boolean flag_2 = false;
		
		private boolean flag_3 = false;
		
		private Label label_1 = null;
		
		RenderItemInFirstPersonMethodVisitor (MethodVisitor mv) {
			super(ASM5, mv);
			triggerMethod_1 = new MethodName(MethodName.getMappedClassName("net/minecraft/item/ItemStack"), PairName.GET_ITEM_USE_ACTION, "()Lnet/minecraft/item/EnumAction;");
			triggerMethod_2 = new MethodName(MethodName.getMappedClassName("net/minecraft/client/entity/EntityClientPlayerMP"), PairName.GET_ITEM_IN_USE_COUNT, "()I");
			triggerMethod_3 = new MethodName(MethodName.getMappedClassName("net/minecraft/item/ItemStack"), PairName.GET_MAX_ITEM_USE_DURATION, "()I");
		}
		
		
		@Override
		public void visitMethodInsn (int opcode, String owner, String name, String desc, boolean itf) {
			flag_1 = false;
			if (opcode == Opcodes.INVOKEVIRTUAL) {
				if (triggerMethod_1.match(name, desc)) {
					flag_1 = true;
					System.out.println("flag_1 catch!!!!!!!!!!!!!!");
				}
				if (triggerMethod_2.match(name, desc)) {
					super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/Raiti/SomeEatingItems/ASM/SomeEatingItemsTransformer", "getItemInUseCount", "(Lnet/minecraft/client/entity/EntityClientPlayerMP;)I", false);
					return;
				}
				if (triggerMethod_3.match(name, desc) && !flag_3) {
					super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/Raiti/SomeEatingItems/ASM/SomeEatingItemsTransformer", "getMaxItemUseDuration", "(Lnet/minecraft/item/ItemStack;)I", false);
					flag_3 = true;
					return;
				}
			}
			
			super.visitMethodInsn(opcode, owner, name, desc, itf);
		}
		
		@Override
		public void visitVarInsn (int opcode, int var) {
			if (flag_1 && flag_2) {
				System.out.println(opcode + ":" + var);
				return;
			}
			
			if (flag_1 && opcode == Opcodes.ASTORE && var == 21) {
				flag_2 = true;
				System.out.println("flag_2 catch!!!!!!!!!!!!");
			}
			super.visitVarInsn(opcode, var);
		}
		
		@Override
		public void visitFieldInsn (int opcode, String owner, String name, String desc) {
			if (flag_1 && flag_2) {
				System.out.println(opcode + ":" + name + ":" + desc);
				return;
			}
			super.visitFieldInsn(opcode, owner, name, desc);
		}
		
		@Override
		public void visitJumpInsn (int opcode, Label label) {
			if (flag_1 && flag_2) {
				System.out.println(opcode + ":" + label);
				if (opcode == Opcodes.IF_ACMPNE) {
					super.visitVarInsn(Opcodes.ALOAD, 8);
					super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/Raiti/SomeEatingItems/ASM/SomeEatingItemsTransformer", "checkItem", "(Lnet/minecraft/item/ItemStack;)Z", false);
					super.visitJumpInsn(Opcodes.IFEQ, label);
					flag_1 = false;
					flag_2 = false;
				}
				return;
			}
			
			super.visitJumpInsn(opcode, label);
		}
	}
}
