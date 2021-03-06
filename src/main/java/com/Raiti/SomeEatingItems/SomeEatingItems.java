package com.Raiti.SomeEatingItems;

import com.Raiti.SomeEatingItems.Packet.PacketHander;
import com.Raiti.SomeEatingItems.Server.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * MainClass.<br>
 * This mod makes it possible to eat various items on use NBT Data.
 * <br>Created by Raiti-chan on 2017/03/04.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
@Mod(modid = SomeEatingItems.MOD_ID, name = SomeEatingItems.MOD_NAME, version = SomeEatingItems.MOD_VERSION, useMetadata = true)
public class SomeEatingItems {
	
	/**
	 * This mod ID.
	 */
	public static final String MOD_ID = "SomeEatingItems";
	
	/**
	 * This mod name.
	 */
	public static final String MOD_NAME = "Some eating items";
	
	/**
	 * This mod version.
	 */
	public static final String MOD_VERSION = "1.0.0 a";
	
	/**
	 * This mod's proxy instance.
	 */
	@SuppressWarnings("unused")
	@SidedProxy(clientSide = "com.Raiti.SomeEatingItems.Client.ClientProxy", serverSide = "com.Raiti.SomeEatingItems.Server.CommonProxy")
	public static CommonProxy proxy;
	
	/**
	 * This mod Metadata.
	 */
	@Mod.Metadata
	public static ModMetadata metadata;
	
	
	/**
	 * This mod's PreInitializer.
	 * @param event FML Event
	 */
	@SuppressWarnings("unused")
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		writeMetaData(metadata);
		PacketHander.init();
		
	}
	
	
	/**
	 * This mod's PostInitializer.
	 * @param event FML Event
	 */
	@SuppressWarnings("unused")
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ForgeEventHook());
	}
	
	/**
	 * Write this mod's metadata.
	 * @param metadata This mod's metadata
	 */
	private void writeMetaData(ModMetadata metadata) {
		metadata.modId = MOD_ID;
		metadata.name = MOD_NAME;
		metadata.version = MOD_VERSION;
		metadata.authorList.add("Raiti-chan");
		metadata.description = "This mod makes it possible to eat various items on use NBT Data.";
		
		metadata.autogenerated = false;
	}
}
