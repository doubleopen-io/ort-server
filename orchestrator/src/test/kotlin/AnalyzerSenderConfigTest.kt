/*
 * Copyright (C) 2026 The ORT Server Authors (See <https://github.com/eclipse-apoapsis/ort-server/blob/main/NOTICE>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package org.eclipse.apoapsis.ortserver.orchestrator

import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigResolveOptions

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AnalyzerSenderConfigTest : StringSpec({
    "The Analyzer environment allowlist should be passed to the Kubernetes sender configuration" {
        analyzerSenderConfigWithAllowlist("JAVA_TOOL_OPTIONS,LOG_FORMAT") shouldBe "JAVA_TOOL_OPTIONS,LOG_FORMAT"
    }

    "An empty Analyzer environment allowlist should remain configured" {
        analyzerSenderConfigWithAllowlist("") shouldBe ""
    }
})

private fun analyzerSenderConfigWithAllowlist(value: String): String =
    ConfigFactory.parseResources("application.conf")
        .withFallback(ConfigFactory.parseMap(mapOf("ANALYZER_ENVIRONMENT_ALLOWLIST" to value)))
        .resolve(ConfigResolveOptions.defaults().setUseSystemEnvironment(false))
        .getConfig("analyzer.sender")
        .getString("environmentAllowlist")
