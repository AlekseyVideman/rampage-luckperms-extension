package com.rampagemc.lpextension;

import com.rampagemc.backlib.configuration.property.RestConfiguration;
import com.rampagemc.lpextension.service.CheckCommandDoneService;
import com.rampagemc.lpextension.service.ExecuteCommandService;
import io.javalin.Javalin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.extension.Extension;

public class RampageExtension implements Extension {

    private final ExecuteCommandService commandService;
    private final CheckCommandDoneService checkCommandDoneService;
    private final Javalin httpServer;
    private final CommandExecutionObserver commandExecutionObserver;

    public RampageExtension(LuckPerms plugin) {
        this.httpServer = setupHttpServer();
        this.commandExecutionObserver = new CommandExecutionObserver(plugin);
             commandExecutionObserver.startObserving();
        this.commandService = new ExecuteCommandService(plugin);
        this.checkCommandDoneService = new CheckCommandDoneService(commandExecutionObserver);
    }

    @Override
    public void load() {
        httpServer.start(8080);
    }

    @Override
    public void unload() {
        httpServer.stop();
    }

    private Javalin setupHttpServer() {
        return Javalin.create()
                .post(RestConfiguration.PRIVATE.concat("/luckperms"), ctx -> {
                    final var body = ctx.body();
                    if (body.isBlank()) ctx.status(400).result("Error: Command cannot be empty");

                    commandService.execute(body);

                    final var commandDone = checkCommandDoneService.checkLastCommandExecuted();
                    if (!commandDone) ctx.status(400).result("Error: The command is not executed");
                });
    }
}
