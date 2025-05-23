package me.clefal.lootbeams.config.impl;

import me.clefal.lootbeams.events.ConfigReloadEvent;

public interface IConfigReloadable {

    void onReload(ConfigReloadEvent event);
}
