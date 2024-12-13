package org.example.drawer;

import org.example.model.Group;
import org.example.mapper.ChartDataMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.util.List;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;

public class DrawerWindow extends JFrame {

    private JTabbedPane tabbedPane;
    private RestTemplate restTemplate;

    public DrawerWindow(String title) {
        super(title);
        restTemplate = new RestTemplate();

        // Получаем данные через контроллер
        List<Group> groups = getGroupsFromController();

        // Настройка вкладок для отображения графиков
        tabbedPane = new JTabbedPane();

        // Устанавливаем ориентацию вкладок сверху вниз
        tabbedPane.setTabPlacement(JTabbedPane.TOP);

        // Вкладка для всех тем (Scores by Themes)
        tabbedPane.addTab("Scores by all Themes", createScoresByThemesPanel(groups));

        // Вкладка для всех групп (Scores by Groups)
        tabbedPane.addTab("Scores by all Groups", createScoresByGroupsPanel(groups));

        // Вкладки для каждой из 12 тем
        for (int i = 1; i <= 12; i++) {
            tabbedPane.addTab("Theme " + i, createThemePanel(groups, i));
        }

        // Настроить внешний вид вкладок
        customizeTabbedPane();

        // Устанавливаем содержимое окна
        setContentPane(tabbedPane);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Вкладка для всех тем (Scores by Themes)
    private JPanel createScoresByThemesPanel(List<Group> groups) {
        JPanel panel = new JPanel(new BorderLayout());
        JFreeChart chart = createScoresByThemesChart(groups);
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    // Вкладка для всех групп (Scores by Groups)
    private JPanel createScoresByGroupsPanel(List<Group> groups) {
        JPanel panel = new JPanel(new BorderLayout());
        JFreeChart chart = createScoresByGroupsChart(groups);
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    // Вкладка для конкретной темы
    private JPanel createThemePanel(List<Group> groups, int themeNumber) {
        JPanel panel = new JPanel(new BorderLayout());
        JFreeChart chart = createChartForTheme(groups, themeNumber);
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    // Создаем график для всех тем (Scores by Themes)
    private JFreeChart createScoresByThemesChart(List<Group> groups) {
        CategoryDataset dataset = ChartDataMapper.createScoresByThemes(groups);
        JFreeChart chart = ChartFactory.createLineChart(
                "Scores by Themes",            // Заголовок графика
                "Theme",                       // Ось X
                "Score",                       // Ось Y
                dataset,                       // Данные
                PlotOrientation.VERTICAL,      // Ориентация
                true,                          // Легенда
                true,                          // Инструменты
                false                          // URL
        );

        // Применяем стиль для линейного графика
        customizeLineChartAppearance(chart);
        return chart;
    }

    // Создаем график для всех групп (Scores by Groups)
    private JFreeChart createScoresByGroupsChart(List<Group> groups) {
        CategoryDataset dataset = ChartDataMapper.createScoresByGroups(groups);
        JFreeChart chart = ChartFactory.createBarChart(
                "Scores by Groups",           // Заголовок графика
                "Group",                      // Ось X
                "Score",                      // Ось Y
                dataset,                      // Данные
                PlotOrientation.HORIZONTAL,   // Ориентация
                true,                         // Легенда
                true,                         // Инструменты
                false                         // URL
        );

        // Применяем стиль для барового графика
        customizeBarChartAppearance(chart);
        return chart;
    }

    // Создаем график для конкретной темы
    private JFreeChart createChartForTheme(List<Group> groups, int themeNumber) {
        CategoryDataset dataset = ChartDataMapper.createScoresThemesByGroups(groups, String.valueOf(themeNumber));
        JFreeChart chart = ChartFactory.createBarChart(
                "Scores for Theme " + themeNumber,  // Заголовок графика
                "Group",                           // Ось X
                "Score",                           // Ось Y
                dataset,                           // Данные
                PlotOrientation.HORIZONTAL,        // Ориентация
                true,                              // Легенда
                true,                              // Инструменты
                false                              // URL
        );

        // Применяем стиль для барового графика
        customizeBarChartAppearance(chart);
        return chart;
    }
    //Метод для настройки внешнего вида вкладок
    private void customizeTabbedPane() {
        // Устанавливаем цвет фона для активной и неактивной вкладки
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int index, int x, int y, int w, int h, boolean isSelected) {
                if (isSelected) {
                    g.setColor(new Color(0, 128, 255));  // Цвет активной вкладки (например, голубой)
                } else {
                    g.setColor(Color.WHITE);  // Цвет фона для неактивных вкладок (белый)
                }
                g.fillRect(x, y, w, h);
            }

            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
                if (isSelected) {
                    g.setColor(Color.BLACK);  // Цвет текста для активной вкладки
                } else {
                    g.setColor(Color.BLACK);  // Цвет текста для неактивных вкладок (черный)
                }
                g.setFont(font);
                g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            }
        });
    }

    // Метод для настройки внешнего вида линейного графика
    private void customizeLineChartAppearance(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);

        // Получаем CategoryPlot для настройки
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);  // Светло-серый фон для области графика

        // Настройка цвета и стиля сетки
        plot.setDomainGridlinePaint(Color.BLACK);  // Вертикальная сетка
        plot.setRangeGridlinePaint(Color.BLACK);   // Горизонтальная сетка

        // Настройка цветов линий
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);      // Первая линия — красный
        renderer.setSeriesPaint(1, Color.YELLOW);   // Вторая линия — желтый
        renderer.setSeriesPaint(2, Color.ORANGE);   // Третья линия — оранжевый
        renderer.setSeriesPaint(3, Color.CYAN);     // Четвертая линия — голубой
        renderer.setSeriesPaint(4, Color.MAGENTA);  // Пятая линия — фиолетовый
        renderer.setSeriesPaint(5, Color.BLUE);     // Шестая линия — синий

        // Настройка толщины линий для каждой серии
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(4.0f));
        renderer.setSeriesStroke(2, new BasicStroke(4.0f));
        renderer.setSeriesStroke(3, new BasicStroke(4.0f));
        renderer.setSeriesStroke(4, new BasicStroke(4.0f));
        renderer.setSeriesStroke(5, new BasicStroke(4.0f));

        // Включаем отображение точек на линиях и меняем форму точек
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShapesVisible(3, true);
        renderer.setSeriesShapesVisible(4, true);
        renderer.setSeriesShapesVisible(5, true);

        // Настройка формы точек
        int circleSize = 8;
        Ellipse2D.Double emptyCircle = new Ellipse2D.Double((double) -circleSize / 2, (double) -circleSize / 2, circleSize, circleSize);
        renderer.setSeriesShape(0, emptyCircle);
        renderer.setSeriesShape(1, emptyCircle);
        renderer.setSeriesShape(2, emptyCircle);
        renderer.setSeriesShape(3, emptyCircle);
        renderer.setSeriesShape(4, emptyCircle);
        renderer.setSeriesShape(5, emptyCircle);

        plot.setRenderer(renderer);  // Применяем рендерер

        // Настройка шрифта для заголовка
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 16));

        // Настройка шрифта для меток осей
        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.PLAIN, 14));  // Ось X
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.PLAIN, 14));   // Ось Y

        // Настройка шрифта для подписей категорий на оси X
        plot.getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 12));

        // Настройка шрифта для подписей категорий на оси Y
        plot.getRangeAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
    }

    // Метод для настройки внешнего вида барового графика
    private void customizeBarChartAppearance(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);  // Белый фон для всего графика

        // Получаем CategoryPlot для настройки
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);  // Светло-серый фон для области графика

        // Настройка цвета и стиля сетки
        plot.setDomainGridlinePaint(Color.BLACK);  // Вертикальная сетка
        plot.setRangeGridlinePaint(Color.BLACK);   // Горизонтальная сетка

        // Настройка рендерера для баров
        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, new Color(100, 150, 255));  // Синий для баров
        renderer.setSeriesOutlinePaint(0, Color.BLACK);  // Черная обводка

        renderer.setShadowVisible(false);

        // Устанавливаем черную обводку для столбцов
        renderer.setSeriesOutlinePaint(0, Color.BLACK);  // Черная обводка

        // Включаем отображение обводки
        renderer.setSeriesOutlineStroke(0,new BasicStroke(2.0f),true);

        renderer.setMaximumBarWidth(0.05); // Уменьшаем ширину столбцов
        renderer.setItemMargin(0.1); // Устанавливаем промежуток между столбцами

        // Отключаем эффект 3D, делаем столбцы плоскими
        renderer.setBarPainter(new StandardBarPainter());

        plot.setRenderer(renderer);  // Применяем рендерер

        // Настройка шрифта для заголовка
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 16));

        // Настройка шрифта для меток осей
        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.PLAIN, 14));  // Ось X
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.PLAIN, 14));   // Ось Y

        // Настройка шрифта для подписей категорий на оси X
        plot.getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 12));

        // Настройка шрифта для подписей категорий на оси Y
        plot.getRangeAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 12));

    }

    // Метод для получения списка групп с помощью RestTemplate
    private List<Group> getGroupsFromController() {
        String url = "http://localhost:9090/groups";
        return List.of(restTemplate.getForObject(url, Group[].class));
    }

    public static void main(String[] args) {
        // Создаем окно
        SwingUtilities.invokeLater(() -> {
            DrawerWindow window = new DrawerWindow("Group Scores");
            window.setVisible(true);
        });
    }
}
