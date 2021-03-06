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
package org.jackhuang.hmcl.ui.download;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.jackhuang.hmcl.download.RemoteVersion;
import org.jackhuang.hmcl.ui.FXUtils;

/**
 * @author huangyuhui
 */
public final class VersionsPageItem extends StackPane {
    private final RemoteVersion<?> remoteVersion;
    @FXML
    private Label lblSelfVersion;
    @FXML
    private Label lblGameVersion;

    public VersionsPageItem(RemoteVersion<?> remoteVersion) {
        this.remoteVersion = remoteVersion;

        FXUtils.loadFXML(this, "/assets/fxml/download/versions-list-item.fxml");
        lblSelfVersion.setText(remoteVersion.getSelfVersion());
        lblGameVersion.setText(remoteVersion.getGameVersion());
    }

    public RemoteVersion<?> getRemoteVersion() {
        return remoteVersion;
    }
}
