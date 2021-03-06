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

package org.sonar.ce;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import org.picocontainer.Startable;
import org.sonar.ce.taskprocessor.CeWorkerFactory;
import org.sonar.cluster.ClusterObjectKeys;
import org.sonar.cluster.localclient.HazelcastClient;

import static org.sonar.cluster.ClusterObjectKeys.WORKER_UUIDS;
import static org.sonar.core.util.stream.MoreCollectors.toSet;

/**
 * Provide the set of worker's UUID in a clustered SonarQube instance
 */
public class CeDistributedInformationImpl implements CeDistributedInformation, Startable {
  private final HazelcastClient hazelcastClient;
  private final CeWorkerFactory ceCeWorkerFactory;

  public CeDistributedInformationImpl(HazelcastClient hazelcastClient, CeWorkerFactory ceCeWorkerFactory) {
    this.hazelcastClient = hazelcastClient;
    this.ceCeWorkerFactory = ceCeWorkerFactory;
  }

  @Override
  public Set<String> getWorkerUUIDs() {
    Set<String> connectedWorkerUUIDs = hazelcastClient.getMemberUuids();

    return getClusteredWorkerUUIDs().entrySet().stream()
      .filter(e -> connectedWorkerUUIDs.contains(e.getKey()))
      .map(Map.Entry::getValue)
      .flatMap(Set::stream)
      .collect(toSet());
  }

  @Override
  public void broadcastWorkerUUIDs() {
    getClusteredWorkerUUIDs().put(hazelcastClient.getUUID(), ceCeWorkerFactory.getWorkerUUIDs());
  }

  @Override
  public Lock acquireCleanJobLock() {
    return hazelcastClient.getLock(ClusterObjectKeys.CE_CLEANING_JOB_LOCK);
  }

  @Override
  public void start() {
    // Nothing to do here
  }

  @Override
  public void stop() {
    // Removing the worker UUIDs
    getClusteredWorkerUUIDs().remove(hazelcastClient.getUUID());
  }

  private Map<String, Set<String>> getClusteredWorkerUUIDs() {
    return hazelcastClient.getReplicatedMap(WORKER_UUIDS);
  }
}
