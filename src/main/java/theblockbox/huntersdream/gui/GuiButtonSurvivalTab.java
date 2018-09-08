package theblockbox.huntersdream.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import theblockbox.huntersdream.Main;
import theblockbox.huntersdream.init.ItemInit;

public class GuiButtonSurvivalTab extends GuiButton {
	private boolean hasMouseBeenPressed = false;

	public GuiButtonSurvivalTab(int buttonId, int x, int y) {
		super(buttonId, x, y, 18, 18, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			super.drawButton(mc, mouseX, mouseY, partialTicks);
			if (this.mousePressed(mc, mouseX, mouseY) && this.hasMouseBeenPressed) {
				// TODO: Add opening of survival tab here
				Main.getLogger().debug("Survival tab button has been clicked");
				this.hasMouseBeenPressed = false;
			}
			mc.getRenderItem().renderItemIntoGUI(new ItemStack(ItemInit.HUNTERS_JOURNAL), x + 1, y + 1);
		}
	}

	@Override
	public void playPressSound(SoundHandler soundHandlerIn) {
		this.hasMouseBeenPressed = true;
	}

	public boolean hasMouseBeenPressed() {
		return this.hasMouseBeenPressed;
	}
}