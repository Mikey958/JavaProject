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
        CategoryDataset dataset = ChartDataMapper.createScoresByThemes(groups); // ���������� ����� �����
        JFreeChart chart = ChartFactory.createLineChart(
                title,                 // ��������� �������
                "Theme",               // ��� X (����)
                "Score",               // ��� Y (�����)
                dataset,               // ������
                PlotOrientation.VERTICAL,  // ���������� �������
                true,                  // �������
                true,                  // �����������
                false                  // URL
        );

        chart.setBackgroundPaint(Color.WHITE);  // ����� ��� ��� ����� �������

        // �������� CategoryPlot ��� ���������
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);

        // ��������� ����� � ����� �����
        plot.setDomainGridlinePaint(Color.BLACK);  // ������������ �����
        plot.setRangeGridlinePaint(Color.BLACK);   // �������������� �����

        // ��������� ������ �����
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);      // ������ ����� � �������
        renderer.setSeriesPaint(1, Color.YELLOW);   // ������ ����� � ������
        renderer.setSeriesPaint(2, Color.ORANGE);    // ������ ����� � �������
        renderer.setSeriesPaint(3, Color.CYAN);     // ��������� ����� � �������
        renderer.setSeriesPaint(4, Color.MAGENTA);  // ����� ����� � ����������
        renderer.setSeriesPaint(5, Color.BLUE);     // ������ ����� � �����

        // ��������� ������� ����� ��� ������ �����
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));  // ������� ����� ��� ������ �����
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));  // ������� ����� ��� ������ �����
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));  // ������� ����� ��� ������� �����
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));  // ������� ����� ��� ��������� �����
        renderer.setSeriesStroke(4, new BasicStroke(2.0f));  // ������� ����� ��� ����� �����
        renderer.setSeriesStroke(5, new BasicStroke(2.0f));  // ������� ����� ��� ������ �����

        // �������� ����������� ����� �� ������ � ������ ����� �����
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShapesVisible(3, true);
        renderer.setSeriesShapesVisible(4, true);
        renderer.setSeriesShapesVisible(5, true);

        // ��������� ����� ����� (������ ����)
        int circleSize = 10;  // ������ �����
        Ellipse2D.Double emptyCircle = new Ellipse2D.Double((double) -circleSize / 2, (double) -circleSize / 2, circleSize, circleSize);
        renderer.setSeriesShape(0, emptyCircle);  // ������ ���� ��� ������ �����
        renderer.setSeriesShape(1, emptyCircle);  // ������ ���� ��� ������ �����
        renderer.setSeriesShape(2, emptyCircle);  // ������ ���� ��� ������� �����
        renderer.setSeriesShape(3, emptyCircle);  // ������ ���� ��� ��������� �����
        renderer.setSeriesShape(4, emptyCircle);  // ������ ���� ��� ����� �����
        renderer.setSeriesShape(5, emptyCircle);  // ������ ���� ��� ������ �����

        // ��������� �������� � �������
        chart.getCategoryPlot().setRenderer(renderer);

        // ��������� ������ � ����
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 1920, 1080);
        return chartFile;
    }

    public File createChartFile(List<Group> groups, String themeNumber, String filePath) throws IOException {
        CategoryDataset dataset = ChartDataMapper.createScoresThemesByGroups(groups, themeNumber);
        JFreeChart chart = ChartFactory.createBarChart(
                title,                 // ��������� �������
                "Group",               // ��� X (�������� ���)
                "Score",               // ��� Y (�����)
                dataset,               // ������
                PlotOrientation.HORIZONTAL, // ���������� �������
                true,                  // �������
                true,                  // �����������
                false                  // URL
        );

        chart.setBackgroundPaint(Color.WHITE);  // ����� ��� ��� ����� �������

        // �������� CategoryPlot ��� ���������
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);

        // ��������� ����� � ����� �����
        plot.setDomainGridlinePaint(Color.BLACK);  // ������������ �����
        plot.setRangeGridlinePaint(Color.BLACK);   // �������������� �����

        BarRenderer renderer = new BarRenderer();

        renderer.setSeriesPaint(0, Color.GRAY);

        // ������� ����
        renderer.setShadowVisible(false);

        // ��������� ������ 3D, ������ ������� ��������
        renderer.setBarPainter(new StandardBarPainter());

        // ������������� ������ ������� ��� ��������
        renderer.setSeriesOutlinePaint(0, Color.BLACK);  // ������ �������

        // �������� ����������� �������
        renderer.setSeriesOutlineStroke(0,new BasicStroke(2.0f),true);

        renderer.setMaximumBarWidth(0.05); // ��������� ������ ��������
        renderer.setItemMargin(0.1); // ������������� ���������� ����� ���������

        // ��������� �������� � �������
        plot.setRenderer(renderer);

        // ��������� ������ � ����
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 1920, 1080);
        return chartFile;
    }
}