package serg.chuprin.convention

import org.gradle.api.JavaVersion
import serg.chuprin.convention.FinancesBuildType

// Zentrale Konfiguration für SDK Versionen (Ersatz für FinancesConfig)
object FinancesConfig {
  const val NAME_SPACE = "serg.chuprin.finances"
  const val APPLICATION_ID = "serg.chuprin.finances"

  const val MIN_SDK = 28
  const val TARGET_SDK = 35
  const val COMPILE_SDK = 36
  const val VERSION_CODE = 1
  const val VERSION_NAME = "1.0.0-alpha01"

  const val JAVA_VERSION = 17

  object BuildTypes {
    val DEV = FinancesBuildType("dev")
    val DEBUG = FinancesBuildType("debug")
    val RELEASE = FinancesBuildType("release")
  }
}