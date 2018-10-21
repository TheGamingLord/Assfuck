package theblockbox.huntersdream.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theblockbox.huntersdream.util.handlers.PacketHandler;
import theblockbox.huntersdream.util.helpers.VampireHelper;
import theblockbox.huntersdream.util.interfaces.transformation.IVampire;

/**
 * A class for vampires extending {@link FoodStats} to change the way hunger
 * works for vampires
 */
public class VampireFoodStats extends FoodStats {
	public static final VampireFoodStats INSTANCE = new VampireFoodStats();
	long millis = 0;

	private VampireFoodStats() {
	}

	@Override
	public float getSaturationLevel() {
		return 0F;
	}

	@Override
	public int getFoodLevel() {
		// returning ten to make players move with normal speed
		return 20;
	}

	@Override
	public void onUpdate(EntityPlayer player) {
		IVampire vampire = VampireHelper.getIVampire(player);
		if (player.ticksExisted % 2 == 0) {
			int blood = vampire.getBlood();

			if (player.isPotionActive(MobEffects.HUNGER) && (blood != 0)) {
				double newBlood = vampire.getBloodDouble()
						- (0.04F * (player.getActivePotionEffect(MobEffects.HUNGER).getAmplifier() + 1.0F));

				if (newBlood <= 0) {
					newBlood = 0;
				}

				vampire.setBlood(newBlood);
			}

			if (player.ticksExisted % 78 == 0) {
				if (vampire.getBloodDouble() >= 1 && player.shouldHeal()) {
					vampire.decrementBlood();
					player.heal(1);
				} else if (blood <= 0 && player.getHealth() > 1.0F) {
					player.attackEntityFrom(DamageSource.STARVE, 1.0F);
					// TODO: Add bloodlust effect here
				}

				if (player.ticksExisted % 156 == 0 && blood >= 1) {
					vampire.setBlood(vampire.getBloodDouble() - 0.0416D);
				}
			}

			if (vampire.getBlood() != blood)
				PacketHandler.sendBloodMessage((EntityPlayerMP) player);
		}
	}

	@Override
	public void addStats(ItemFood foodItem, ItemStack stack) {
	}

	@Override
	public void addStats(int foodLevelIn, float foodSaturationModifier) {
	}

	@Override
	public void addExhaustion(float exhaustion) {
	}

	@Override
	public void setFoodLevel(int foodLevelIn) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setFoodSaturationLevel(float foodSaturationLevelIn) {
	}

	@Override
	public boolean needFood() {
		return false;
	}

	@Override
	public void readNBT(NBTTagCompound compound) {
	}

	@Override
	public void writeNBT(NBTTagCompound compound) {
	}
}