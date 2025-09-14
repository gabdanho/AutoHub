package com.example.autohub.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Основная тема приложения, адаптирующаяся под различные размеры экрана.
 *
 * Определяет типографику и размеры отступов в зависимости от текущей конфигурации
 * экрана, обеспечивая адаптивный дизайн для различных устройств.
 *
 * @param content Компонент Compose, которому будет применена тема.
 */
@Composable
fun MainTheme(
    content: @Composable () -> Unit,
) {
    /**
     * Определяет цвета приложения.
     */
    val colors = lightColors

    /**
     * Определяет размеры отступов.
     */
    val dimensions = defaultDimensions

    /**
     * Определяет размеры форм.
     */
    val shapes = defaultShapes

    /**
     * Определяет размеры шрифтов.
     */
    val fonts = defaultFontSizes

    /**
     * Предоставляет утилиты приложения через [ProvideAppUtils],
     * обеспечивая доступ к типографике и размерам отступов во всем приложении.
     */
    ProvideAppUtils(
        mainDimens = dimensions,
        colors = colors,
        shapes = shapes,
        fonts = fonts
    ) {
        MaterialTheme(
            content = content
        )
    }
}

/**
 * Объект для доступа к текущей теме приложения.
 *
 *  [AppTheme.colors], [AppTheme.dimens], [AppTheme.shapes], [AppTheme.fonts] во всех компонентах Compose.
 */
object AppTheme {

    /**
     * Текущая цветовая схема приложения.
     *
     * Получает экземпляр [Colors] из локального провайдера [LocalAppColor].
     *
     * @see Colors
     */
    val colors: Colors
        @Composable
        get() = LocalAppColor.current

    /**
     * Текущие размеры отступов приложения.
     *
     * Получает значение из [LocalAppDimension].
     *
     * @return Объект [Dimensions], определяющий отступы.
     */

    val dimens: Dimensions
        @Composable
        get() = LocalAppDimension.current

    /**
     * Текущие формы приложения.
     *
     * Получает значение из [LocalAppShapes].
     *
     * @return Объект [Shapes], определяющий формы объектов.
     */

    val shapes: Shapes
        @Composable
        get() = LocalAppShapes.current

    /**
     * Текущие размеры шрифтов приложения.
     *
     * Получает значение из [LocalAppShapes].
     *
     * @return Объект [Shapes], определяющий размеры шрифтов.
     */

    val fonts: FontSizes
        @Composable
        get() = LocalAppFonts.current
}