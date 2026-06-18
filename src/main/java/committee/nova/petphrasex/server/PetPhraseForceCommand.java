package committee.nova.petphrasex.server;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import committee.nova.petphrasex.server.forced.ForcedPhraseData;
import committee.nova.petphrasex.server.forced.ForcedPhraseField;
import committee.nova.petphrasex.server.forced.ForcedPhraseManager;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;

public final class PetPhraseForceCommand {
    private static final SimpleCommandExceptionType UNKNOWN_FIELD = new SimpleCommandExceptionType(Component.literal("Unknown field."));

    private PetPhraseForceCommand() {
    }

    public static void register(com.mojang.brigadier.CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        dispatcher.register(Commands.literal("petphrasex")
                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                .then(Commands.literal("force")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.literal("set")
                                        .then(Commands.argument("field", StringArgumentType.word())
                                                .then(Commands.argument("value", StringArgumentType.greedyString())
                                                        .executes(PetPhraseForceCommand::setField))))
                                .then(Commands.literal("clear").executes(PetPhraseForceCommand::clear))
                                .then(Commands.literal("view").executes(PetPhraseForceCommand::view)))));
    }

    private static int setField(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ForcedPhraseField field = ForcedPhraseField.fromCommandName(StringArgumentType.getString(context, "field"));
        if (field == null) {
            throw UNKNOWN_FIELD.create();
        }

        String value = StringArgumentType.getString(context, "value");
        ForcedPhraseManager.setField(player, field, value);
        context.getSource().sendSuccess(() -> Component.literal("Set " + field.commandName() + " for " + player.getName().getString() + "."), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int clear(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        boolean removed = ForcedPhraseManager.clear(player);
        if (removed) {
            context.getSource().sendSuccess(() -> Component.literal("Cleared forced phrase for " + player.getName().getString() + "."), true);
            return Command.SINGLE_SUCCESS;
        }

        context.getSource().sendFailure(Component.literal("No forced phrase was set for " + player.getName().getString() + "."));
        return 0;
    }

    private static int view(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ForcedPhraseData data = ForcedPhraseManager.get(player);
        if (data == null) {
            context.getSource().sendSuccess(() -> Component.literal(player.getName().getString() + " has no forced phrase settings."), false);
            return Command.SINGLE_SUCCESS;
        }

        context.getSource().sendSuccess(() -> Component.literal(player.getName().getString()
                + " forced=" + data.active()
                + ", ignoreMark=" + data.ignoreMark()
                + ", prefix=" + data.prefix()
                + ", suffix=" + data.suffix()
                + ", sentencePrefix=" + data.sentencePrefix()
                + ", sentenceSuffix=" + data.sentenceSuffix()), false);
        return Command.SINGLE_SUCCESS;
    }
}
