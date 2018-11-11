package theblockbox.huntersdream.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import theblockbox.huntersdream.util.HuntersJournalPage;
import theblockbox.huntersdream.util.handlers.RegistryHandler;

public class ServerProxy implements IProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
	}

	@Override
	public void preInit() {
		RegistryHandler.preInitServer();
	}

	@Override
	public void init() {
		RegistryHandler.initServer();
	}

	@Override
	public void postInit() {
		RegistryHandler.postInitServer();
	}

	@Override
	public boolean physicalClient() {
		return false;
	}

	@Override
	public EntityPlayer getPlayer() {
		return null;
	}

	@Override
	public void openHuntersJournal(EntityPlayer player, HuntersJournalPage[] pages) {
	}

	@Override
	public <T extends Entity> T getEntityFromID(int id) {
		throw new UnsupportedOperationException("Can't get entity from server");
	}
}
