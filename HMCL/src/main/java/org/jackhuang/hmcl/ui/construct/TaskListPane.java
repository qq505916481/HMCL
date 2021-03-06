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
package org.jackhuang.hmcl.ui.construct;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.jackhuang.hmcl.Main;
import org.jackhuang.hmcl.download.forge.ForgeInstallTask;
import org.jackhuang.hmcl.download.game.GameAssetDownloadTask;
import org.jackhuang.hmcl.download.game.GameAssetRefreshTask;
import org.jackhuang.hmcl.download.liteloader.LiteLoaderInstallTask;
import org.jackhuang.hmcl.download.optifine.OptiFineInstallTask;
import org.jackhuang.hmcl.game.HMCLModpackExportTask;
import org.jackhuang.hmcl.game.HMCLModpackInstallTask;
import org.jackhuang.hmcl.mod.CurseCompletionTask;
import org.jackhuang.hmcl.mod.CurseInstallTask;
import org.jackhuang.hmcl.mod.MinecraftInstanceTask;
import org.jackhuang.hmcl.mod.MultiMCModpackInstallTask;
import org.jackhuang.hmcl.task.FileDownloadTask;
import org.jackhuang.hmcl.task.Task;
import org.jackhuang.hmcl.task.TaskExecutor;
import org.jackhuang.hmcl.task.TaskListener;
import org.jackhuang.hmcl.ui.AdvancedListBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class TaskListPane extends StackPane {
    private final AdvancedListBox listBox = new AdvancedListBox();
    private final Map<Task, ProgressListNode> nodes = new HashMap<>();

    public TaskListPane() {
        listBox.setSpacing(0);

        getChildren().setAll(listBox);
    }

    public void setExecutor(TaskExecutor executor) {
        executor.addTaskListener(new TaskListener() {
            @Override
            public void onStart() {
                Platform.runLater(listBox::clear);
            }

            @Override
            public void onReady(Task task) {
                if (!task.getSignificance().shouldShow())
                    return;

                if (task instanceof GameAssetRefreshTask) {
                    task.setName(Main.i18n("assets.download"));
                } else if (task instanceof GameAssetDownloadTask) {
                    task.setName(Main.i18n("assets.download_all"));
                } else if (task instanceof ForgeInstallTask) {
                    task.setName(Main.i18n("install.installer.install", Main.i18n("install.installer.forge")));
                } else if (task instanceof LiteLoaderInstallTask) {
                    task.setName(Main.i18n("install.installer.install", Main.i18n("install.installer.liteloader")));
                } else if (task instanceof OptiFineInstallTask) {
                    task.setName(Main.i18n("install.installer.install", Main.i18n("install.installer.optifine")));
                } else if (task instanceof CurseCompletionTask) {
                    task.setName(Main.i18n("modpack.type.curse.completion"));
                } else if (task instanceof CurseInstallTask) {
                    task.setName(Main.i18n("modpack.install", Main.i18n("modpack.type.curse")));
                } else if (task instanceof MultiMCModpackInstallTask) {
                    task.setName(Main.i18n("modpack.install", Main.i18n("modpack.type.multimc")));
                } else if (task instanceof HMCLModpackInstallTask) {
                    task.setName(Main.i18n("modpack.install", Main.i18n("modpack.type.hmcl")));
                } else if (task instanceof HMCLModpackExportTask) {
                    task.setName(Main.i18n("modpack.export"));
                } else if (task instanceof MinecraftInstanceTask) {
                    task.setName(Main.i18n("modpack.scan"));
                }

                ProgressListNode node = new ProgressListNode(task);
                nodes.put(task, node);
                Platform.runLater(() -> listBox.add(node));

            }

            @Override
            public void onFinished(Task task) {
                ProgressListNode node = nodes.remove(task);
                if (node == null)
                    return;
                node.unbind();
                Platform.runLater(() -> listBox.remove(node));
            }

            @Override
            public void onFailed(Task task, Throwable throwable) {
                ProgressListNode node = nodes.remove(task);
                if (node == null)
                    return;
                Platform.runLater(() -> node.setThrowable(throwable));
            }
        });
    }

    private static class ProgressListNode extends VBox {
        private final JFXProgressBar bar = new JFXProgressBar();
        private final Label title = new Label();
        private final Label state = new Label();

        public ProgressListNode(Task task) {
            bar.progressProperty().bind(task.progressProperty());
            title.setText(task.getName());
            state.textProperty().bind(task.messageProperty());

            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(title);
            borderPane.setRight(state);
            getChildren().addAll(borderPane, bar);

            bar.minWidthProperty().bind(widthProperty());
            bar.prefWidthProperty().bind(widthProperty());
            bar.maxWidthProperty().bind(widthProperty());
        }

        public void unbind() {
            bar.progressProperty().unbind();
            state.textProperty().unbind();
        }

        public void setThrowable(Throwable throwable) {
            unbind();
            state.setText(throwable.getLocalizedMessage());
            bar.setProgress(0);
        }
    }
}
