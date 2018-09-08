package theblockbox.huntersdream.network;

import java.util.function.Function;
import java.util.function.IntFunction;

import javax.annotation.Nonnull;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import theblockbox.huntersdream.Main;
import theblockbox.huntersdream.util.enums.Transformations;
import theblockbox.huntersdream.util.handlers.ConfigHandler;
import theblockbox.huntersdream.util.helpers.GeneralHelper;

public abstract class MessageBase<T extends MessageBase<T>> implements IMessage {

	public MessageBase() {
	}

	public abstract String getName();

	public static void writeString(ByteBuf buf, String string) {
		ByteBufUtils.writeUTF8String(buf, string);
	}

	public static String readString(ByteBuf buf) {
		return ByteBufUtils.readUTF8String(buf);
	}

	public static ResourceLocation readResourceLocation(ByteBuf buf) {
		return GeneralHelper.newResLoc(readString(buf));
	}

	public static void writeResourceLocation(ByteBuf buf, ResourceLocation resourceLocation) {
		writeString(buf, resourceLocation.toString());
	}

	public static Transformations readTransformation(ByteBuf buf) {
		String transformationName = readString(buf);
		Transformations transformation = Transformations.fromName(transformationName);
		if (transformation == null)
			throw new NullPointerException("Found transformation is null. Name: " + transformationName);
		return transformation;
	}

	public static void writeTransformation(ByteBuf buf, @Nonnull Transformations transformation) {
		if (transformation != null)
			writeString(buf, transformation.toString());
		else
			throw new IllegalArgumentException("The transformation argument is null which it isn't allowed to be");
	}

	public static <T> void writeArray(ByteBuf buf, @Nonnull T[] array, Function<T, String> tToString) {
		buf.writeInt(array.length);
		for (int i = 0; i < array.length; i++)
			writeString(buf, tToString.apply(array[i]));
	}

	public static <T> T[] readArray(ByteBuf buf, Function<String, T> stringToT,
			IntFunction<T[]> newEmptyArrayWithSize) {
		T[] array = newEmptyArrayWithSize.apply(buf.readInt());
		for (int i = 0; i < array.length; i++)
			array[i] = stringToT.apply(readString(buf));
		return array;
	}

	public static void addScheduledTask(MessageContext ctx, Runnable runnableToSchedule) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(runnableToSchedule);
	}

	public abstract MessageHandler<T, ? extends IMessage> getMessageHandler();

	public abstract static class MessageHandler<T extends MessageBase<T>, REPLY extends IMessage>
			implements IMessageHandler<T, REPLY> {
		public MessageHandler() {
		}

		@Override
		public REPLY onMessage(T message, MessageContext ctx) {
			REPLY answer = onMessageReceived(message, ctx);
			if (ConfigHandler.common.showPacketMessages)
				Main.getLogger().info(name() + " packet received on side " + ctx.side.toString());
			return answer;
		}

		public abstract REPLY onMessageReceived(T message, MessageContext ctx);

		public String name() {
			try {
				Object instance = getClass().getDeclaringClass().getConstructor().newInstance();
				return (String) instance.getClass().getMethod("getName").invoke(instance);
			} catch (Exception e) {
				return null;
			}
		}
	}
}
