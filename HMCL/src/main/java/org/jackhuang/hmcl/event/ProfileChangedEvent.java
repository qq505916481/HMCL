/*
 * Hello Minecraft! Launcher.
 * Copyright (C) 2017  huangyuhui <huanghongxun2008@126.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see {http://www.gnu.org/licenses/}.
 */
package org.jackhuang.hmcl.event;

import org.jackhuang.hmcl.setting.Profile;

/**
 * This event gets fired when the selected profile changed.
 * <br>
 * This event is fired on the {@link org.jackhuang.hmcl.event.EventBus#EVENT_BUS}
 * @author huangyuhui
 */
public final class ProfileChangedEvent extends Event {
    private final Profile profile;

    /**
     * Constructor.
     *
     * @param source {@link org.jackhuang.hmcl.setting.Settings}
     * @param profile the new profile.
     */
    public ProfileChangedEvent(Object source, Profile profile) {
        super(source);

        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

}
