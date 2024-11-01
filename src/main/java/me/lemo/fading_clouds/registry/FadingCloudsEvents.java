package me.lemo.fading_clouds.registry;

import me.lemo.fading_clouds.event.FillCloudBottleEvent;

public class FadingCloudsEvents {

    public static void addListeners() {
        FillCloudBottleEvent.registerEvent();
    }
}
