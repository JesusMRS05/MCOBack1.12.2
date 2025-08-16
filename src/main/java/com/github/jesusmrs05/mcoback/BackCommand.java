package com.github.jesusmrs05.mcoback;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;

class BackCommand implements ICommand {

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if (player != null) {

			if (MCOBack.coordinates.isEmpty() || MCOBack.dimension.isEmpty()) {
				player.sendMessage(new TextComponentString(TextFormatting.RED + "No location saved"));
			} else {
				String cmd = "/tpto " + MCOBack.coordinates + " " + MCOBack.dimension;
				player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "About to use " + cmd));
				player.sendChatMessage(cmd);
			}
		}
	}

	@Override public String getName() {
		return "back";
	}
	@Override public String getUsage(ICommandSender sender) {
		return "/back";
	}
	@Override public List<String> getAliases() {
		return Collections.emptyList();
	}
	@Override public int compareTo(ICommand o) {
		return this.getName().compareTo(o.getName());
	}
	@Override public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}
	@Override public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		return Collections.emptyList();
	}
	@Override public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}