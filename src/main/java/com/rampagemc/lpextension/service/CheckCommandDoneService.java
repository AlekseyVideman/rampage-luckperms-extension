package com.rampagemc.lpextension.service;

import com.rampagemc.lpextension.CommandExecutionObserver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckCommandDoneService {

    private final CommandExecutionObserver commandExecutionObserver;

    public boolean checkLastCommandExecuted() {
        return commandExecutionObserver.isCommandExecuted();
    }
}
