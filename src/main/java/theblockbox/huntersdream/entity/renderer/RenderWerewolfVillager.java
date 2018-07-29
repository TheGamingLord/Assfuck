package theblockbox.huntersdream.entity.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import theblockbox.huntersdream.entity.EntityWerewolfVillager;
import theblockbox.huntersdream.entity.model.ModelCustomVillager;
import theblockbox.huntersdream.util.Reference;

/**
 * For the villager that is a werewolf but not transformed
 * 
 * @deprecated Now using normal {@link EntityVillager} instead of this here
 */
@Deprecated
public class RenderWerewolfVillager extends RenderLiving<EntityWerewolfVillager> {
	public static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MODID,
			"textures/entity/werewolf_villager.png");

	public RenderWerewolfVillager(RenderManager manager) {
		super(manager, new ModelCustomVillager(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWerewolfVillager entity) {
		return TEXTURE_NORMAL;
	}
}