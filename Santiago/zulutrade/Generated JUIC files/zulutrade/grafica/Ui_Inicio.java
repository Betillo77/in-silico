/********************************************************************************
** Form generated from reading ui file 'Inicio.jui'
**
** Created: mié 17. jun 17:53:20 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package zulutrade.grafica;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Inicio implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QWidget centralwidget;
    public QToolButton proveedores;
    public QToolButton potenciales;
    public QLabel label;
    public QLabel label_2;
    public QLabel label_3;
    public QMenuBar menubar;
    public QStatusBar statusbar;

    public Ui_Inicio() { super(); }

    public void setupUi(QMainWindow Inicio)
    {
        Inicio.setObjectName("Inicio");
        Inicio.resize(new QSize(366, 245).expandedTo(Inicio.minimumSizeHint()));
        centralwidget = new QWidget(Inicio);
        centralwidget.setObjectName("centralwidget");
        proveedores = new QToolButton(centralwidget);
        proveedores.setObjectName("proveedores");
        proveedores.setGeometry(new QRect(120, 80, 141, 41));
        QFont font = new QFont();
        font.setPointSize(12);
        proveedores.setFont(font);
        proveedores.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.TabFocus);
        potenciales = new QToolButton(centralwidget);
        potenciales.setObjectName("potenciales");
        potenciales.setGeometry(new QRect(120, 130, 141, 41));
        QFont font1 = new QFont();
        font1.setPointSize(12);
        potenciales.setFont(font1);
        potenciales.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.TabFocus);
        label = new QLabel(centralwidget);
        label.setObjectName("label");
        label.setGeometry(new QRect(50, 200, 101, 31));
        QFont font2 = new QFont();
        font2.setPointSize(12);
        label.setFont(font2);
        label_2 = new QLabel(centralwidget);
        label_2.setObjectName("label_2");
        label_2.setGeometry(new QRect(140, 200, 211, 31));
        QFont font3 = new QFont();
        font3.setFamily("Comic Sans MS");
        font3.setPointSize(12);
        font3.setItalic(true);
        label_2.setFont(font3);
        label_3 = new QLabel(centralwidget);
        label_3.setObjectName("label_3");
        label_3.setGeometry(new QRect(60, 20, 271, 41));
        QFont font4 = new QFont();
        font4.setPointSize(15);
        label_3.setFont(font4);
        Inicio.setCentralWidget(centralwidget);
        menubar = new QMenuBar(Inicio);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 366, 22));
        Inicio.setMenuBar(menubar);
        statusbar = new QStatusBar(Inicio);
        statusbar.setObjectName("statusbar");
        Inicio.setStatusBar(statusbar);
        retranslateUi(Inicio);

        Inicio.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow Inicio)
    {
        Inicio.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Inicio", "Zulutrade", null));
        proveedores.setText(com.trolltech.qt.core.QCoreApplication.translate("Inicio", "Proveedores", null));
        potenciales.setText(com.trolltech.qt.core.QCoreApplication.translate("Inicio", "Potenciales", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Inicio", "Hecho por:", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("Inicio", "Santiago Gutierrez Alzate", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("Inicio", "Programa analisis Zulutrade", null));
    } // retranslateUi

}

