package org.example.drawer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ���������������� ����� ��� ������������� ��������� Spring.
 * �� ���������� ��������� ��� ��������������� ������������ ����������� � ������ "org.example"
 * � ��������� ������������ ����� Spring ���������.
 */
@Configuration
@ComponentScan(basePackages = "org.example") // ��������� Spring, ��� ���������� ����������� ����� "org.example" ��� ������ �����������
public class DrawerWindowConfig {

}
