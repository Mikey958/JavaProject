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

public class Drawer {
    private String title;

    public Drawer(String title) {
        this.title = title;
    }

    public File createChartFile(List<Group> groups, String filePath) throws IOException {
        CategoryDataset dataset = ChartDataMapper.createScoresByThemes(groups); // Используем новый метод
        JFreeChart chart = ChartFactory.createLineChart(
                title,                 // Заголовок графика
                "Theme",               // Ось X (Темы)
                "Score",               // Ось Y (Баллы)
                dataset,               // Данные
                PlotOrientation.VERTICAL,  // Ориентация графика
                true,                  // Легенда
                true,                  // Инструменты
                false                  // URL
        );

        chart.setBackgroundPaint(Color.WHITE);  // Белый фон для всего графика

        // Получаем CategoryPlot для настройки
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);

        // Настройка цвета и стиля сетки
        plot.setDomainGridlinePaint(Color.BLACK);  // Вертикальная сетка
        plot.setRangeGridlinePaint(Color.BLACK);   // Горизонтальная сетка

        // Настройка цветов линий
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);      // Первая линия — красный
        renderer.setSeriesPaint(1, Color.YELLOW);   // Вторая линия — желтый
        renderer.setSeriesPaint(2, Color.ORANGE);    // Третья линия — зеленый
        renderer.setSeriesPaint(3, Color.CYAN);     // Четвертая линия — голубой
        renderer.setSeriesPaint(4, Color.MAGENTA);  // Пятая линия — фиолетовый
        renderer.setSeriesPaint(5, Color.BLUE);     // Шестая линия — синий

        // Настройка толщины линий для каждой серии
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));  // Толщина линии для первой серии
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));  // Толщина линии для второй серии
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));  // Толщина линии для третьей серии
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));  // Толщина линии для четвертой серии
        renderer.setSeriesStroke(4, new BasicStroke(2.0f));  // Толщина линии для пятой серии
        renderer.setSeriesStroke(5, new BasicStroke(2.0f));  // Толщина линии для шестой серии

        // Включаем отображение точек на линиях и меняем форму точек
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShapesVisible(3, true);
        renderer.setSeriesShapesVisible(4, true);
        renderer.setSeriesShapesVisible(5, true);

        // Настройка формы точек (пустой круг)
        int circleSize = 10;  // Радиус круга
        Ellipse2D.Double emptyCircle = new Ellipse2D.Double((double) -circleSize / 2, (double) -circleSize / 2, circleSize, circleSize);
        renderer.setSeriesShape(0, emptyCircle);  // Пустой круг для первой линии
        renderer.setSeriesShape(1, emptyCircle);  // Пустой круг для второй линии
        renderer.setSeriesShape(2, emptyCircle);  // Пустой круг для третьей линии
        renderer.setSeriesShape(3, emptyCircle);  // Пустой круг для четвертой линии
        renderer.setSeriesShape(4, emptyCircle);  // Пустой круг для пятой линии
        renderer.setSeriesShape(5, emptyCircle);  // Пустой круг для шестой линии

        // Применяем рендерер к графику
        chart.getCategoryPlot().setRenderer(renderer);

        // Сохраняем график в файл
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 1920, 1080);
        return chartFile;
    }

    public File createChartFile(List<Group> groups, String themeNumber, String filePath) throws IOException {
        CategoryDataset dataset = ChartDataMapper.createScoresThemesByGroups(groups, themeNumber);
        JFreeChart chart = ChartFactory.createBarChart(
                title,                 // Заголовок графика
                "Group",               // Ось X (названия тем)
                "Score",               // Ось Y (баллы)
                dataset,               // Данные
                PlotOrientation.HORIZONTAL, // Ориентация графика
                true,                  // Легенда
                true,                  // Инструменты
                false                  // URL
        );

        chart.setBackgroundPaint(Color.WHITE);  // Белый фон для всего графика

        // Получаем CategoryPlot для настройки
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);

        // Настройка цвета и стиля сетки
        plot.setDomainGridlinePaint(Color.BLACK);  // Вертикальная сетка
        plot.setRangeGridlinePaint(Color.BLACK);   // Горизонтальная сетка

        BarRenderer renderer = new BarRenderer();

        renderer.setSeriesPaint(0, Color.GRAY);

        // Убираем тени
        renderer.setShadowVisible(false);

        // Отключаем эффект 3D, делаем столбцы плоскими
        renderer.setBarPainter(new StandardBarPainter());

        // Устанавливаем черную обводку для столбцов
        renderer.setSeriesOutlinePaint(0, Color.BLACK);  // Черная обводка

        // Включаем отображение обводки
        renderer.setSeriesOutlineStroke(0,new BasicStroke(2.0f),true);

        renderer.setMaximumBarWidth(0.05); // Уменьшаем ширину столбцов
        renderer.setItemMargin(0.1); // Устанавливаем промежуток между столбцами

        // Применяем рендерер к графику
        plot.setRenderer(renderer);

        // Сохраняем график в файл
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 1920, 1080);
        return chartFile;
    }
}