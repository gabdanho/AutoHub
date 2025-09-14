package com.example.autohub.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

/**
 * Предоставляет утилиты приложения через [CompositionLocalProvider].
 *
 * Оборачивает контент в провайдеры локальных значений, обеспечивая доступ к
 * типографике и размерам отступов по горизонтали и вертикали во всем приложении.
 *
 * @param mainDimens Объект [Dimensions], содержащий набор отступов, используемых в приложении.
 * @param colors Объект [Colors], определяющий основную цветовую схему приложения.
 * @param shapes Объект [Shapes], содержащий набор форм, используемых в приложении.
 * @param content Компонент Compose, которому будут предоставлены локальные значения.
 */
@Composable
fun ProvideAppUtils(
    mainDimens: Dimensions,
    colors: Colors,
    shapes: Shapes,
    fonts: FontSizes,
    content: @Composable () -> Unit
) {
    val appDimens = remember { mainDimens }
    val appColors = remember { colors }
    val appShapes = remember { shapes }
    val appFonts = remember { fonts }

    CompositionLocalProvider(
        LocalAppDimension provides appDimens,
        LocalAppColor provides appColors,
        LocalAppShapes provides appShapes,
        LocalAppFonts provides appFonts,
        content = content
    )
}

/**
 * Локальное значение для цветовой схемы приложения.
 *
 * Предоставляет доступ к экземпляру [Colors], определяющему набор основных цветов для оформления
 * пользовательского интерфейса в приложении.
 *
 * @see Colors
 */
val LocalAppColor = compositionLocalOf {
    lightColors
}

/**
 * Локальное значение для размеров отступов в приложении.
 *
 * Предоставляет доступ к экземпляру [Dimensions], определяющему набор отступов,
 * используемых в различных компонентах пользовательского интерфейса.
 *
 * @see Dimensions
 */
val LocalAppDimension = compositionLocalOf {
    defaultDimensions
}

/**
 * Локальное значение для размеров форм в приложении.
 *
 * Предоставляет доступ к экземпляру [Shapes], определяющему набор форм,
 * используемых в различных компонентах пользовательского интерфейса.
 *
 * @see Shapes
 */
val LocalAppShapes = compositionLocalOf {
    defaultShapes
}

/**
 * Локальное значение для размеров форм в приложении.
 *
 * Предоставляет доступ к экземпляру [FontSizes], определяющему набор размеры шрифтов,
 * используемых в различных компонентах пользовательского интерфейса.
 *
 * @see FontSizes
 */
val LocalAppFonts = compositionLocalOf {
    defaultFontSizes
}