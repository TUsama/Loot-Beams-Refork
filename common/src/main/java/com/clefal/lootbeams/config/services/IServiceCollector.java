package com.clefal.lootbeams.config.services;

import com.clefal.lootbeams.platform.Services;

import java.util.List;

public interface IServiceCollector {

    IServiceCollector COLLECTOR = Services.load(IServiceCollector.class);

    List<String> gatherModIDList();

}
