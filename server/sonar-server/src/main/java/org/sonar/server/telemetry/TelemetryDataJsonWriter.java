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

package org.sonar.server.telemetry;

import org.sonar.api.utils.text.JsonWriter;

import static org.sonar.api.measures.CoreMetrics.LINES_KEY;
import static org.sonar.api.measures.CoreMetrics.NCLOC_KEY;

public class TelemetryDataJsonWriter {
  private TelemetryDataJsonWriter() {
    // static methods
  }

  public static void writeTelemetryData(JsonWriter json, TelemetryData statistics) {
    json.beginObject();
    json.prop("id", statistics.getServerId());
    json.prop("version", statistics.getVersion());
    json.name("plugins");
    json.valueObject(statistics.getPlugins());
    json.prop("userCount", statistics.getUserCount());
    json.prop("projectCount", statistics.getProjectCount());
    json.prop(LINES_KEY, statistics.getLines());
    json.prop(NCLOC_KEY, statistics.getNcloc());
    json.name("projectCountByLanguage");
    json.valueObject(statistics.getProjectCountByLanguage());
    json.name("nclocByLanguage");
    json.valueObject(statistics.getNclocByLanguage());
    json.endObject();
  }
}
