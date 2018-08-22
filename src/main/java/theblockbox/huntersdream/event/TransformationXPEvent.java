package theblockbox.huntersdream.event;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import theblockbox.huntersdream.util.enums.Transformations;
import theblockbox.huntersdream.util.helpers.TransformationHelper;
import theblockbox.huntersdream.util.interfaces.transformation.ITransformationPlayer;

/**
 * This event is fired shortly before the player's transformation xp is changed
 * (through the method {@link TransformationHelper#setXP(EntityPlayerMP, int)}
 * or methods that call this method). When this event is canceled, the player
 * won't get any xp added, no packet and no level up message will be sent. The
 * amount is the xp the player will have after the post of this event (given
 * that the event is not canceled). Posted on {@link MinecraftForge#EVENT_BUS}
 */
@Cancelable
public class TransformationXPEvent extends PlayerEvent {
	public final ITransformationPlayer TRANSFORMATION_PLAYER;
	/** The player's current xp (before applying it) */
	public final int XP_BEFORE;
	private int xpAfter;
	public final TransformationXPSentReason REASON;

	public TransformationXPEvent(EntityPlayer player, int xpAfter, TransformationXPSentReason reason) {
		super(player);
		ITransformationPlayer cap = TransformationHelper.getCap(player);
		this.TRANSFORMATION_PLAYER = cap;
		this.XP_BEFORE = cap.getXP();
		this.xpAfter = xpAfter;
		this.REASON = reason;
	}

	/** Returns the xp the player should have AFTER this event is fired */
	public int getAmount() {
		return this.xpAfter;
	}

	/** Set the player's xp */
	public void setAmount(int amount) {
		this.xpAfter = amount;
	}

	@Override
	public Result getResult() {
		return super.getResult();
	}

	public enum TransformationXPSentReason {
		WEREWOLF_HAS_KILLED(Transformations.WEREWOLF), WEREWOLF_UNDER_MOON(Transformations.WEREWOLF), COMMAND;

		/** The transformations that can receive xp through this cause */
		public final Transformations[] TRANSFORMATIONS;

		private TransformationXPSentReason(Transformations... transformations) {
			this.TRANSFORMATIONS = transformations;
		}

		public static boolean validReason(@Nonnull TransformationXPSentReason reason,
				@Nonnull Transformations transformation) {
			return Stream.of(reason.TRANSFORMATIONS).anyMatch(transformation::equals);
		}
	}
}
