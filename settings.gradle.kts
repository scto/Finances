/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://jcenter.bintray.com/") } // Wichtig für serg.chuprin
    }
}

rootProject.name = "Finances"


include(
// region App
    ":app",
// region Core
    ":core:api",
    ":core:mvi",
    ":core:impl",
    ":core:test",
    ":core:firebase",
    ":core:pie-chart",
    ":core:category-shares",
    ":core:currency-choice-api",
    ":core:currency-choice-impl",
// region Feature
    ":feature:authorization",
    ":feature:categories-list",
    ":feature:dashboard",
    ":feature:dashboard-setup-api",
    ":feature:dashboard-setup-impl",
    ":feature:money-account",
    ":feature:money-account-details",
    ":feature:money-accounts-list",
    ":feature:onboarding",
    ":feature:transaction",
    ":feature:transactions-report",
    ":feature:user-profile"
)