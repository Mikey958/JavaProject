package org.example.drawer;

import org.example.mapper.ChartDataMapper;
import org.example.model.Group;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Класс для рисования графиков.
 */
public class Drawer {
    private String title;

    /**
     * Конструктор класса.
     *
     * @param title Название графика
     */
    public Drawer(String title) {
        this.title = title;
    }

    /**
     * Метод для создания и сохранения линейного графика.
     *
     * @param groups Список групп для построения графика
     * @param filePath Путь для сохранения изображения графика
     * @return Файл с изображением графика
     * @throws IOException При возникновении ошибок с файловыми операциями
     */
    public File createChartFile(List<Group> groups, String filePath) throws IOException {
        // Создание набора данных на основе информации о группах
        CategoryDataset dataset = ChartDataMapper.createScoresByThemes(groups);

        // Создание линейного графика
        JFreeChart chart = ChartFactory.createLineChart(
                title,                 // Заголовок графика
                "Theme",               // Ось X
                "Score",               // Ось Y
                dataset,               // Данные для графика
                PlotOrientation.VERTICAL,  // Ориентация графика
                true,                  // Легенда
                true,                  // Подсказки
                false                  // Включение ссылок на URL
        );

        chart.setBackgroundPaint(Color.WHITE);  // Установка белого фона для графика

        // Настройка внешнего вида графика
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY); // Легкий фон графика

        plot.setDomainGridlinePaint(Color.BLACK);  // Линии сетки по оси X
        plot.setRangeGridlinePaint(Color.BLACK);   // Линии сетки по оси Y

        // Настройка стиля графика
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);      // Цвет линии для серии 0
        renderer.setSeriesPaint(1, Color.YELLOW);   // Цвет линии для серии 1
        renderer.setSeriesPaint(2, Color.ORANGE);    // Цвет линии для серии 2
        renderer.setSeriesPaint(3, Color.CYAN);     // Цвет линии для серии 3
        renderer.setSeriesPaint(4, Color.MAGENTA);  // Цвет линии для серии 4
        renderer.setSeriesPaint(5, Color.BLUE);     // Цвет линии для серии 5

        // Установка толщины линий для всех серий
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));  // Толщина линии для серии 0
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));  // Толщина линии для серии 1
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));  // Толщина линии для серии 2
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));  // Толщина линии для серии 3
        renderer.setSeriesStroke(4, new BasicStroke(2.0f));  // Толщина линии для серии 4
        renderer.setSeriesStroke(5, new BasicStroke(2.0f));  // Толщина линии для серии 5

        // Установка видимости точек на графике
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShapesVisible(3, true);
        renderer.setSeriesShapesVisible(4, true);
        renderer.setSeriesShapesVisible(5, true);

        // Установка формы точек (пустой круг)
        int circleSize = 10;
        Ellipse2D.Double emptyCircle = new Ellipse2D.Double((double) -circleSize / 2, (double) -circleSize / 2, circleSize, circleSize);
        renderer.setSeriesShape(0, emptyCircle);
        renderer.setSeriesShape(1, emptyCircle);
        renderer.setSeriesShape(2, emptyCircle);
        renderer.setSeriesShape(3, emptyCircle);
        renderer.setSeriesShape(4, emptyCircle);
        renderer.setSeriesShape(5, emptyCircle);

        // Применение рендерера к графику
        chart.getCategoryPlot().setRenderer(renderer);

        // Сохранение графика в файл
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 1920, 1080);
        return chartFile;
    }

    /**
     * Метод для создания и сохранения горизонтального столбчатого графика для конкретной темы.
     *
     * @param groups Список групп для построения графика
     * @param themeNumber Номер темы для отображения
     * @param filePath Путь для сохранения изображения графика
     * @return Файл с изображением графика
     * @throws IOException При возникновении ошибок с файловыми операциями
     */
    public File createChartFile(List<Group> groups, String themeNumber, String filePath) throws IOException {
        // Создание набора данных для конкретной темы
        CategoryDataset dataset = ChartDataMapper.createScoresThemesByGroups(groups, themeNumber);

        // Создание столбчатого графика
        JFreeChart chart = ChartFactory.createBarChart(
                title,                 // Заголовок графика
                "Group",               // Ось X (Группы)
                "Score",               // Ось Y (Оценки)
                dataset,               // Данные для графика
                PlotOrientation.HORIZONTAL, // Ориентация графика
                true,                  // Легенда
                true,                  // Подсказки
                false                  // Включение ссылок на URL
        );

        chart.setBackgroundPaint(Color.WHITE);  // Установка белого фона для графика

        // Настройка внешнего вида графика
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY); // Легкий фон графика

        plot.setDomainGridlinePaint(Color.BLACK);  // Линии сетки по оси X
        plot.setRangeGridlinePaint(Color.BLACK);   // Линии сетки по оси Y

        BarRenderer renderer = new BarRenderer();

        // Установка цвета для столбцов
        renderer.setSeriesPaint(0, Color.GRAY);

        // Отключение теней для столбцов
        renderer.setShadowVisible(false);

        // Применение 3D-эффекта для столбцов
        renderer.setBarPainter(new StandardBarPainter());

        // Установка контуров для столбцов
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f), true);

        // Ограничение ширины столбцов
        renderer.setMaximumBarWidth(0.05);
        renderer.setItemMargin(0.1); // Установка маржи между столбцами

        // Применение рендерера к графику
        plot.setRenderer(renderer);

        // Сохранение графика в файл
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 1920, 1080);
        return chartFile;
    }
}
