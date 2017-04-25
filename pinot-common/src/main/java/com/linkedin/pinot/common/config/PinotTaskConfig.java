/**
 * Copyright (C) 2014-2016 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.common.config;

import com.linkedin.pinot.common.utils.EqualityUtils;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.helix.task.TaskConfig;


public class PinotTaskConfig {
  private final String _taskType;
  private final Map<String, String> _configs;

  public PinotTaskConfig(@Nonnull String taskType, @Nonnull Map<String, String> configs) {
    _taskType = taskType;
    _configs = configs;
  }

  public String getTaskType() {
    return _taskType;
  }

  public Map<String, String> getConfigs() {
    return _configs;
  }

  @Nonnull
  public TaskConfig toHelixTaskConfig() {
    return new TaskConfig(_taskType, _configs);
  }

  @Nonnull
  public static PinotTaskConfig fromHelixTaskConfig(@Nonnull TaskConfig helixTaskConfig) {
    return new PinotTaskConfig(helixTaskConfig.getCommand(), helixTaskConfig.getConfigMap());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PinotTaskConfig) {
      PinotTaskConfig pinotTaskConfig = (PinotTaskConfig) obj;
      return _taskType.equals(pinotTaskConfig._taskType) && _configs.equals(pinotTaskConfig._configs);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return EqualityUtils.hashCodeOf(_taskType.hashCode(), _configs.hashCode());
  }

  @Override
  public String toString() {
    return "Task Type: " + _taskType + ", Configs: " + _configs;
  }
}
