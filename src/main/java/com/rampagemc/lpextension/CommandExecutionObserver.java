package com.rampagemc.lpextension;

import lombok.RequiredArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.group.GroupCreateEvent;
import net.luckperms.api.event.group.GroupDeleteEvent;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.event.track.TrackCreateEvent;
import net.luckperms.api.event.track.TrackDeleteEvent;
import net.luckperms.api.event.track.mutate.TrackMutateEvent;
import net.luckperms.api.event.user.track.UserTrackEvent;

/**
 * An observer that marks {@link #isCommandExecuted} as true if one of the event handlers were called up.
 */
@RequiredArgsConstructor
public class CommandExecutionObserver {

    private final LuckPerms plugin;
    private boolean isCommandExecuted;

    @SuppressWarnings("all")
    public void startObserving() {
        plugin.getEventBus().subscribe(NodeMutateEvent.class, e -> markTrue());
        plugin.getEventBus().subscribe(GroupCreateEvent.class, e -> markTrue());
        plugin.getEventBus().subscribe(GroupDeleteEvent.class, e -> markTrue());
        plugin.getEventBus().subscribe(UserTrackEvent.class, e -> markTrue());
        plugin.getEventBus().subscribe(TrackMutateEvent.class, e -> markTrue());
        plugin.getEventBus().subscribe(TrackCreateEvent.class, e -> markTrue());
        plugin.getEventBus().subscribe(TrackDeleteEvent.class, e -> markTrue());
    }

    private void markTrue() {
        isCommandExecuted = true;
    }

    public boolean isCommandExecuted() {
        final var result = this.isCommandExecuted;
        this.isCommandExecuted = false; // refresh for next execution
        return result;
    }
}
