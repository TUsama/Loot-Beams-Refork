package com.clefal.lootbeams.config.impl;

import com.clefal.lootbeams.events.ConfigReloadEvent;

public interface IConfigReloadable {

    void onReload(ConfigReloadEvent event);
}
