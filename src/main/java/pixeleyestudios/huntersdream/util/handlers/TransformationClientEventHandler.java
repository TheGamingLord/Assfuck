package pixeleyestudios.huntersdream.util.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixeleyestudios.huntersdream.entity.renderer.RenderWolfmanPlayer;
import pixeleyestudios.huntersdream.util.Reference;
import pixeleyestudios.huntersdream.util.helpers.TransformationHelper;
import pixeleyestudios.huntersdream.util.helpers.TransformationHelper.Transformations;
import pixeleyestudios.huntersdream.util.helpers.WerewolfHelper;
import pixeleyestudios.huntersdream.util.interfaces.ITransformationPlayer;

/**
 * Handles events which are important for transforming
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class TransformationClientEventHandler {

	private static RenderWolfmanPlayer renderWerewolf = null;
	private static ResourceLocation texture = null;

	@SubscribeEvent
	public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
		if (ConfigHandler.customPlayerRender) {
			EntityPlayer player = event.getEntityPlayer();
			ITransformationPlayer cap = TransformationHelper.getCap(player);

			// werewolf
			if ((cap.getTransformation() == Transformations.WEREWOLF) && cap.transformed()
					&& WerewolfHelper.hasControl(player)) {
				event.setCanceled(true);
				if (renderWerewolf == null)
					renderWerewolf = new RenderWolfmanPlayer(Minecraft.getMinecraft().getRenderManager());
				renderWerewolf.doRender(player, event.getX(), event.getY(), event.getZ(), player.rotationYaw,
						event.getPartialRenderTick());
			}
		}
	}

	// TODO: Custom hand render
	@SubscribeEvent
	public static void onRenderPlayerHand(RenderHandEvent event) {
		if (ConfigHandler.customPlayerRender) {

		}
	}

	// TODO: Transformation xp handler
	@SubscribeEvent
	public static void onGameOverlayRenderPost(RenderGameOverlayEvent.Post event) {
		if (ConfigHandler.renderXPBar) {
			Minecraft mc = Minecraft.getMinecraft();
			if (!mc.player.capabilities.isCreativeMode) {
				if ((!event.isCancelable()) && event.getType() == ElementType.EXPERIENCE) {
					if (texture == null)
						texture = new ResourceLocation(Reference.MODID, "textures/gui/transformation_xp_bar.png");

					mc.renderEngine.bindTexture(texture);
					int x = event.getResolution().getScaledWidth() / 2 + 10;
					int y = event.getResolution().getScaledHeight() - 48;
					mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, 81, 8);
					int percent = (int) (TransformationHelper.getTransformation(mc.player)
							.getPercentageToNextLevel(mc.player) * 79);
					mc.ingameGUI.drawTexturedModalRect(x + 1, y + 1, 0, 9, percent, 6);
				}
			}
		}
	}
}