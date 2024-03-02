package com.rampagemc.lpextension.service;

import me.lucko.luckperms.common.api.LuckPermsApiProvider;
import me.lucko.luckperms.common.plugin.LuckPermsPlugin;
import me.lucko.luckperms.standalone.LPStandaloneBootstrap;
import me.lucko.luckperms.standalone.app.integration.CommandExecutor;
import net.luckperms.api.LuckPerms;
import org.joor.Reflect;

public class ExecuteCommandService {

    private final CommandExecutor commandExecutor;

    public ExecuteCommandService(LuckPerms luckPerms) {
        this.commandExecutor = getCommandManager(luckPerms);
    }

    public void execute(String command) {
        commandExecutor.execute(command).join();
    }

    private CommandExecutor getCommandManager(LuckPerms luckPerms) {
        final var apiProvider = ((LuckPermsApiProvider) luckPerms);
        final LuckPermsPlugin luckPermsPlugin = Reflect.on(apiProvider).get("plugin");
        final var standaloneBootstrap = ((LPStandaloneBootstrap) luckPermsPlugin.getBootstrap());
        final var luckPermsLoader = standaloneBootstrap.getLoader();

        return luckPermsLoader.getCommandExecutor();
    }
}
