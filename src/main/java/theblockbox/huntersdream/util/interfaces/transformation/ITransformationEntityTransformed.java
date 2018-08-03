package theblockbox.huntersdream.util.interfaces.transformation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import theblockbox.huntersdream.util.enums.Transformations;
import theblockbox.huntersdream.util.helpers.TransformationHelper;

/**
 * The name may seem a bit weird. This interface is for entities that
 * are/represent the transformed form (for example werewolf counts, werewolf
 * villager does not)
 */
public interface ITransformationEntityTransformed extends ITransformation {
	@Override
	default boolean transformed() {
		return true;
	}

	@Override
	default void setTransformed(boolean transformed) {
		throw new UnsupportedOperationException("This entity is always transformed");
	}

	@Override
	default void setTransformationID(int transformation) {
		throw new UnsupportedOperationException("This creature's transformation is already determined");
	}

	/** Used to get the entity before the transformation */
	public String getEntityName();

	public static void transformBack(EntityCreature creature) {
		if (creature instanceof ITransformationEntityTransformed) {
			ITransformationEntityTransformed entity = (ITransformationEntityTransformed) creature;
			if (creature.ticksExisted % 40 == 0) {
				if (!creature.world.isRemote) {
					EntityLiving e = null;
					String entityName = entity.getEntityName();

					if (!entityName.startsWith("player")) {

						if (!entityName.startsWith("$bycap")) {
							try {
								@SuppressWarnings("unchecked")
								Class<? extends Entity> entityClass = (Class<? extends Entity>) Class
										.forName(entityName);
								Constructor<?> constructor = entityClass.getConstructor(World.class, int.class,
										Transformations.class);
								e = (EntityLiving) constructor.newInstance(creature.world, entity.getTextureIndex(),
										entity.getTransformation());
							} catch (ClassNotFoundException ex) {
								throw new NullPointerException("Can't find class " + entityName);
							} catch (ClassCastException ex) {
								throw new IllegalArgumentException("Given class " + entityName + " is not an entity");
							} catch (NoSuchMethodException | InvocationTargetException | SecurityException
									| IllegalAccessException ex) {
								throw new NullPointerException("Class " + entityName
										+ " does not have an accessible constructor with parameters World, int, Transformations");
							} catch (InstantiationException ex) {
								throw new IllegalArgumentException("Can't instantiate class " + entityName);
							}
						} else {
							String eName = entityName.substring(6);
							try {
								@SuppressWarnings("unchecked")
								Class<? extends Entity> entityClass = (Class<? extends Entity>) Class.forName(eName);
								Constructor<?> constructor = entityClass.getConstructor(World.class);
								e = (EntityCreature) constructor.newInstance(creature.world);
								// remember: this is only server side and the client doesn't actually need to
								// know about this
								ITransformationCreature transformation = TransformationHelper
										.getITransformationCreature((EntityCreature) e);
								transformation.setTextureIndex(entity.getTextureIndex());
								transformation.setCurrentTransformation(entity.getTransformation());
							} catch (NullPointerException ex) {
								NullPointerException exception = new NullPointerException(
										"Either the entity's capability or something else was null");
								exception.setStackTrace(ex.getStackTrace());
								throw exception;
							} catch (ClassNotFoundException ex) {
								throw new NullPointerException("Can't find class " + eName);
							} catch (ClassCastException ex) {
								throw new IllegalArgumentException("Given class " + eName + " is not an entity");
							} catch (NoSuchMethodException | SecurityException | IllegalAccessException
									| InstantiationException | InvocationTargetException ex) {
								throw new IllegalArgumentException(
										"This entity does not have an accessible constructor and is therefore not registered");
							}
						}

						e.setPosition(creature.posX, creature.posY, creature.posZ);
						creature.world.spawnEntity(e);
					}

					creature.world.removeEntity(creature);
				}
			}
		} else {
			throw new IllegalArgumentException(
					"The given entity's class does not implement interface ITransformationEntityTransformed");
		}
	}
}