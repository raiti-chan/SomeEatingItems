package com.Raiti.SomeEatingItems.ASM;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static java.lang.System.out;
import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.INSTANCEOF;

/**
 * <br>Created by Raiti-chan on 2017/03/26.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class DebugUtil {
	
	@SuppressWarnings("unused")
	public static class OutputJVMASMCode extends MethodVisitor {
		
		/**
		 * Constructs a new {@link MethodVisitor}.
		 *
		 * @param mv the method visitor to which this visitor must delegate method
		 *           calls. May be null.
		 */
		public OutputJVMASMCode (MethodVisitor mv) {
			super(ASM5, mv);
		}
		
		
		@Override
		public void visitVarInsn (int opcode, int var) {
			switch (opcode) {
				case ILOAD:
					out.println("ILOAD: index-" + var);
					break;
				case LLOAD:
					out.println("LLOAD: index-" + var);
					break;
				case FLOAD:
					out.println("FLOAD: index-" + var);
					break;
				case DLOAD:
					out.println("DLOAD: index-" + var);
					break;
				case ALOAD:
					out.println("ALOAD: index-" + var);
					break;
				case ISTORE:
					out.println("ISTORE: index-" + var);
					break;
				case LSTORE:
					out.println("LSTORE: index-" + var);
					break;
				case FSTORE:
					out.println("FSTORE: index-" + var);
					break;
				case DSTORE:
					out.println("DSTORE: index-" + var);
					break;
				case ASTORE:
					out.println("ASTORE: index-" + var);
					break;
				case RET:
					out.println("RET: index-" + var);
					break;
			}
			
			super.visitVarInsn(opcode, var);
		}
		
		@Override
		public void visitFieldInsn (int opcode, String owner, String name, String desc) {
			switch (opcode) {
				case GETSTATIC:
					out.println("GETSTATIC: class-" + owner + " name-" + name + " descriptor-" + desc);
					break;
				case PUTSTATIC:
					out.println("PUTSTATIC: class-" + owner + " name-" + name + " descriptor-" + desc);
					break;
				case GETFIELD:
					out.println("GETFIELD: class-" + owner + " name-" + name + " descriptor-" + desc);
					break;
				case PUTFIELD:
					out.println("PUTFIELD: class-" + owner + " name-" + name + " descriptor-" + desc);
					break;
			}
			super.visitFieldInsn(opcode, owner, name, desc);
		}
		
		@Override
		public void visitJumpInsn (int opcode, Label label) {
			switch (opcode) {
				case IFEQ:
					out.println("IFEQ: label-" + label);
					break;
				case IFNE:
					out.println("IFNE: label-" + label);
					break;
				case IFLT:
					out.println("IFLT: label-" + label);
					break;
				case IFGE:
					out.println("IFGE: label-" + label);
					break;
				case IFGT:
					out.println("IFGT: label-" + label);
					break;
				case IFLE:
					out.println("IFLE: label-" + label);
					break;
				case IF_ICMPEQ:
					out.println("IF_ICMPEQ: label-" + label);
					break;
				case IF_ICMPNE:
					out.println("IF_ICMPNE: label-" + label);
					break;
				case IF_ICMPLT:
					out.println("IF_ICMPLT: label-" + label);
					break;
				case IF_ICMPGE:
					out.println("IF_ICMPGE: label-" + label);
					break;
				case IF_ICMPGT:
					out.println("IF_ICMPGT: label-" + label);
					break;
				case IF_ICMPLE:
					out.println("IF_ICMPLE: label-" + label);
					break;
				case IF_ACMPEQ:
					out.println("IF_ACMPEQ: label-" + label);
					break;
				case IF_ACMPNE:
					out.println("IF_ACMPNE: label-" + label);
					break;
				case GOTO:
					out.println("GOTO: label-" + label);
					break;
				case JSR:
					out.println("JSR: label-" + label);
					break;
				case IFNULL:
					out.println("IFNULL: label-" + label);
					break;
				case IFNONNULL:
					out.println("IFNONNULL: label-" + label);
					break;
			}
			super.visitJumpInsn(opcode, label);
		}
		
		@Override
		public void visitLineNumber (int line, Label start) {
			
			out.println("LINE: number-" + line + " label-" + start);
			
			super.visitLineNumber(line, start);
		}
		
		@Override
		public void visitInsn (int opcode) {
			switch (opcode) {
				case NOP:
					out.println("NOP");
					break;
				case ACONST_NULL:
					out.println("ACONST_NULL");
					break;
				case ICONST_M1:
					out.println("ICONST_M1");
					break;
				case ICONST_0:
					out.println("ICONST_0");
					break;
				case ICONST_1:
					out.println("ICONST_1");
					break;
				case ICONST_2:
					out.println("ICONST_2");
					break;
				case ICONST_3:
					out.println("ICONST_3");
					break;
				case ICONST_4:
					out.println("ICONST_4");
					break;
				case ICONST_5:
					out.println("ICONST_5");
					break;
				case LCONST_0:
					out.println("LCONST_0");
					break;
				case LCONST_1:
					out.println("LCONST_1");
					break;
				case FCONST_0:
					out.println("FCONST_0");
					break;
				case FCONST_1:
					out.println("FCONST_1");
					break;
				case FCONST_2:
					out.println("FCONST_2");
					break;
				case DCONST_0:
					out.println("DCONST_0");
					break;
				case DCONST_1:
					out.println("DCONST_1");
					break;
				case IALOAD:
					out.println("IALOAD");
					break;
				case LALOAD:
					out.println("LALOAD");
					break;
				case FALOAD:
					out.println("FALOAD");
					break;
				case DALOAD:
					out.println("DALOAD");
					break;
				case AALOAD:
					out.println("AALOAD");
					break;
				case BALOAD:
					out.println("BALOAD");
					break;
				case CALOAD:
					out.println("CALOAD");
					break;
				case SALOAD:
					out.println("SALOAD");
					break;
				case IASTORE:
					out.println("IASTORE");
					break;
				case LASTORE:
					out.println("LASTORE");
					break;
				case DASTORE:
					out.println("DASTORE");
					break;
				case FASTORE:
					out.println("FASTORE");
					break;
				case AASTORE:
					out.println("AASTORE");
					break;
				case BASTORE:
					out.println("BASTORE");
					break;
				case CASTORE:
					out.println("CASTORE");
					break;
				case SASTORE:
					out.println("SASTORE");
					break;
				case POP:
					out.println("POP");
					break;
				case POP2:
					out.println("POP2");
					break;
				case DUP:
					out.println("DUP");
					break;
				case DUP_X1:
					out.println("DUP_X1");
					break;
				case DUP_X2:
					out.println("SASTORE");
					break;
				case DUP2:
					out.println("DUP2");
					break;
				case DUP2_X1:
					out.println("DUP2_X1");
					break;
				case DUP2_X2:
					out.println("DUP2_X2");
					break;
				case SWAP:
					out.println("SWAP");
					break;
				case IADD:
					out.println("IADD");
					break;
				case LADD:
					out.println("LADD");
					break;
				case FADD:
					out.println("FADD");
					break;
				case DADD:
					out.println("DADD");
					break;
				case ISUB:
					out.println("ISUB");
					break;
				case LSUB:
					out.println("LSUB");
					break;
				case FSUB:
					out.println("FSUB");
					break;
				case DSUB:
					out.println("DSUB");
					break;
				case IMUL:
					out.println("IMUL");
					break;
				case LMUL:
					out.println("LMUL");
					break;
				case FMUL:
					out.println("FMUL");
					break;
				case DMUL:
					out.println("DMUL");
					break;
				case IDIV:
					out.println("IDIV");
					break;
				case LDIV:
					out.println("LDIV");
					break;
				case FDIV:
					out.println("FDIV");
					break;
				case DDIV:
					out.println("DDIV");
					break;
				case IREM:
					out.println("IREM");
					break;
				case LREM:
					out.println("LREM");
					break;
				case FREM:
					out.println("FREM");
					break;
				case DREM:
					out.println("DREM");
					break;
				case INEG:
					out.println("INEG");
					break;
				case LNEG:
					out.println("LNEG");
					break;
				case FNEG:
					out.println("FNEG");
					break;
				case DNEG:
					out.println("DNEG");
					break;
				case ISHL:
					out.println("ISHL");
					break;
				case LSHL:
					out.println("LSHL");
					break;
				case ISHR:
					out.println("ISHR");
					break;
				case LSHR:
					out.println("ISHL");
					break;
				case IUSHR:
					out.println("IUSHR");
					break;
				case LUSHR:
					out.println("LUSHR");
					break;
				case IAND:
					out.println("IAND");
					break;
				case LAND:
					out.println("LAND");
					break;
				case IOR:
					out.println("IOR");
					break;
				case LOR:
					out.println("LOR");
					break;
				case IXOR:
					out.println("IXOR");
					break;
				case LXOR:
					out.println("LXOR");
					break;
				case I2L:
					out.println("I2L");
					break;
				case I2F:
					out.println("I2F");
					break;
				case I2D:
					out.println("I2D");
					break;
				case L2I:
					out.println("L2I");
					break;
				case L2F:
					out.println("L2F");
					break;
				case L2D:
					out.println("L2D");
					break;
				case F2I:
					out.println("F2I");
					break;
				case F2L:
					out.println("F2L");
					break;
				case F2D:
					out.println("F2D");
					break;
				case D2I:
					out.println("D2I");
					break;
				case D2L:
					out.println("D2L");
					break;
				case D2F:
					out.println("D2F");
					break;
				case I2B:
					out.println("I2L");
					break;
				case I2C:
					out.println("I2C");
					break;
				case I2S:
					out.println("I2S");
					break;
				case LCMP:
					out.println("LCMP");
					break;
				case FCMPL:
					out.println("FCMPL");
					break;
				case FCMPG:
					out.println("FCMPG");
					break;
				case DCMPL:
					out.println("DCMPL");
					break;
				case DCMPG:
					out.println("DCMPG");
					break;
				case IRETURN:
					out.println("IRETURN");
					break;
				case LRETURN:
					out.println("LRETURN");
					break;
				case FRETURN:
					out.println("FRETURN");
					break;
				case DRETURN:
					out.println("DRETURN");
					break;
				case ARETURN:
					out.println("ARETURN");
					break;
				case RETURN:
					out.println("RETURN");
					break;
				case ARRAYLENGTH:
					out.println("ARRAYLENGTH");
					break;
				case ATHROW:
					out.println("ATHROW");
					break;
				case MONITORENTER:
					out.println("MONITORENTER");
					break;
				case MONITOREXIT:
					out.println("MONIROEXIT");
					break;
			}
			super.visitInsn(opcode);
		}
		
		@Override
		public void visitLocalVariable (String name, String desc, String signature, Label start, Label end, int index) {
			out.println("LocalVariable: name-" + name + " descriptor-" + desc + " signature-" + signature + " start-" + start + " end-" + end + " index-" + index);
			super.visitLocalVariable(name, desc, signature, start, end, index);
		}
		
		@Override
		public void visitParameter (String name, int access) {
			out.println("Parameter: name-" + name + " access-" + access);
			super.visitParameter(name, access);
		}
		
		
		@Override
		public void visitEnd () {
			out.println("FINISH!!");
			super.visitEnd();
		}
		
		
		@Override
		public void visitIntInsn (int opcode, int operand) {
			switch (opcode) {
				case BIPUSH:
					out.println("BIPUSH: value-" + operand);
					break;
				case SIPUSH:
					out.println("SIPUSH: value-" + operand);
					break;
				case NEWARRAY:
					out.println("NEWARRAY: -type" + operand);
					break;
			}
			super.visitIntInsn(opcode, operand);
		}
		
		@Override
		public void visitIincInsn (int var, int increment) {
			out.println("IINC: index-" + var + " value-" + increment);
			super.visitIincInsn(var, increment);
		}
		
		@Override
		public void visitInvokeDynamicInsn (String name, String desc, Handle bsm, Object... bsmArgs) {
			out.println("INVOKEDYNAMICK: name-" + name + "descriptor-" + desc);
			super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
		}
		
		@Override
		public void visitLabel (Label label) {
			out.println("LABEL: -object" + label);
			super.visitLabel(label);
		}
		
		@Override
		public void visitLdcInsn (Object cst) {
			out.println("LDC: -value" + cst);
			super.visitLdcInsn(cst);
		}
		
		@Override
		public void visitLookupSwitchInsn (Label dflt, int[] keys, Label[] labels) {
			out.println("LOOKUPSWITCH: -default" + dflt);
			super.visitLookupSwitchInsn(dflt, keys, labels);
		}
		
		
		@Override
		public void visitMaxs (int maxStack, int maxLocals) {
			out.println("MaxSTACK:" + maxStack + " MaxLOCALS:" + maxLocals);
			super.visitMaxs(maxStack, maxLocals);
		}
		
		@Override
		public void visitMethodInsn (int opcode, String owner, String name, String desc, boolean itf) {
			switch (opcode) {
				case INVOKEVIRTUAL:
					out.println("INVOKEVIRTUAL: class-" + owner + " name-" + name + " descriptor-" + desc + " isInterface-" + itf);
					break;
				case INVOKESPECIAL:
					out.println("INVOKESPECIAL: class-" + owner + " name-" + name + " descriptor-" + desc + " isInterface-" + itf);
					break;
				case INVOKESTATIC:
					out.println("INVOKESTATIC: class-" + owner + " name-" + name + " descriptor-" + desc + " isInterface-" + itf);
					break;
				case INVOKEINTERFACE:
					out.println("INVOKEINTERFACE: class-" + owner + " name-" + name + " descriptor-" + desc + " isInterface-" + itf);
					break;
			}
			super.visitMethodInsn(opcode, owner, name, desc, itf);
		}
		
		@Override
		public void visitMultiANewArrayInsn (String desc, int dims) {
			out.println("MULTIANEWARRAY: description-" + desc + " dimensions-" + dims);
			super.visitMultiANewArrayInsn(desc, dims);
		}
		
		@Override
		public void visitTableSwitchInsn (int min, int max, Label dflt, Label... labels) {
			out.println("TABLESWITCH: min-" + min + " max-" + max + " default-" + dflt);
			super.visitTableSwitchInsn(min, max, dflt, labels);
		}
		
		@Override
		public void visitTryCatchBlock (Label start, Label end, Label handler, String type) {
			out.println("TryCatchBlock: start-" + start + " end-" + end + " handler-" + handler + " type-" + type);
			super.visitTryCatchBlock(start, end, handler, type);
		}
		
		@Override
		public void visitTypeInsn (int opcode, String type) {
			switch (opcode) {
				case NEW:
					out.println("NEW: type-" + type);
					break;
				case ANEWARRAY:
					out.println("ANEWARRAY: type-" + type);
					break;
				case CHECKCAST:
					out.println("CHECKCAST: type-" + type);
					break;
				case INSTANCEOF:
					out.println("INSTANCEOF: type-" + type);
					break;
			}
			super.visitTypeInsn(opcode, type);
		}
	}
	
}
