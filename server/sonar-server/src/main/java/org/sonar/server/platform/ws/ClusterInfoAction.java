/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.platform.ws;

import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.Response;
import org.sonar.api.server.ws.WebService;
import org.sonar.api.utils.text.JsonWriter;
import org.sonar.server.user.UserSession;

public class ClusterInfoAction implements SystemWsAction {

  private final UserSession userSession;

  public ClusterInfoAction(UserSession userSession) {
    this.userSession = userSession;
  }

  @Override
  public void define(WebService.NewController controller) {
    controller.createAction("cluster_info")
      .setDescription("WIP")
      .setSince("6.6")
      .setInternal(true)
      .setResponseExample(getClass().getResource("/org/sonar/server/platform/ws/info-example.json"))
      .setHandler(this);
  }

  @Override
  public void handle(Request request, Response response) {
    userSession.checkIsSystemAdministrator();

    try (JsonWriter json = response.newJsonWriter()) {
      json.beginObject();

      // global section
      json.prop("cluster", "true");
      json.prop("clusterName", "foo");
      json.prop("serverId", "ABC123");
      json.prop("health", "RED");
      json
        .name("healthCauses")
        .beginArray().beginObject().prop("message", "Requires at least two search nodes").endObject().endArray();

      json.name("settings");
      json.beginObject();
      json.prop("sonar.forceAuthentication", true);
      json.endObject();

      json.name("computeEngine");
      json
        .beginObject()
        .prop("pending", 5)
        .prop("inProgress", 4)
        .prop("waitingTimeMs", 4000)
        .prop("workers", 8)
        .prop("workersPerNode", 4)
        .endObject();

      json.name("database");
      json
        .beginObject()
        .prop("name", "PostgreSQL")
        .prop("version", "9.6.3")
        .endObject();

      json.name("applicationNodes");
      json
        .beginArray()
        .beginObject()
        .prop("name", "Mont Blanc")
        .prop("host", "10.158.92.16")
        .prop("health", "YELLOW")
        .name("healthCauses").beginArray().beginObject().prop("message", "Db connectivity error").endObject().endArray()
        .prop("startedAt", 1500000000)
        .prop("officialDistribution", "true")
        .prop("processors", 4)
        .endObject()
        .endArray();

      json.name("searchNodes");
      json
        .beginArray()
        .beginObject()
        .prop("name", "Parmelan")
        .prop("host", "10.158.92.19")
        .prop("health", "GREEN")
        .name("healthCauses").beginArray().endArray()
        .prop("startedAt", 1500004000)
        .prop("processors", 2)
        .endObject()
        .endArray();

      json.endObject();
    }
  }
}
