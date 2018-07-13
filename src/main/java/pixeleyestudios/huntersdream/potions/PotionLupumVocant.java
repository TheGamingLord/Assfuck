package pixeleyestudios.huntersdream.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import pixeleyestudios.huntersdream.util.Reference;

/**
 * Applied when you got bitten by a werewolf
 */
public class PotionLupumVocant extends Potion {
	public PotionLupumVocant() {
		super(true, 15989247);
		setRegistryName(new ResourceLocation(Reference.MODID + ":lupumvocant"));
		setPotionName("effect.lupumvocant");
		// setIconIndex(1, 2);
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {

	}

}