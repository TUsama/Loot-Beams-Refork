package com.clefal.lootbeams.config.services;

import com.clefal.lootbeams.platform.Services;

public class PlatformChecker {

    public static final IServicesChecker PLATFORM = Services.load(IServicesChecker.class);
}
