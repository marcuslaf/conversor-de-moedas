package com.conversormoedas.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public final class Theme {

    private Theme() {
        throw new UnsupportedOperationException("Classe utilitária");
    }

    public static final Color PRIMARY = new Color(9, 132, 227);
    public static final Color PRIMARY_DARK = new Color(0, 100, 180);
    public static final Color PRIMARY_LIGHT = new Color(116, 185, 255);

    public static final Color SUCCESS = new Color(0, 184, 148);
    public static final Color SUCCESS_DARK = new Color(0, 150, 120);

    public static final Color WARNING = new Color(253, 203, 110);
    public static final Color WARNING_DARK = new Color(243, 156, 18);

    public static final Color ERROR = new Color(214, 48, 49);
    public static final Color ERROR_DARK = new Color(192, 57, 43);

    public static final Color BACKGROUND = new Color(245, 246, 250);
    public static final Color SURFACE = Color.WHITE;
    public static final Color SURFACE_HOVER = new Color(240, 242, 245);
    public static final Color SURFACE_BORDER = new Color(220, 224, 230);

    public static final Color TEXT_PRIMARY = new Color(45, 52, 54);
    public static final Color TEXT_SECONDARY = new Color(99, 110, 114);
    public static final Color TEXT_MUTED = new Color(178, 190, 195);
    public static final Color TEXT_ON_PRIMARY = Color.WHITE;

    public static final Color DIVIDER = new Color(223, 230, 233);

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font FONT_RESULT = new Font("SansSerif", Font.BOLD, 28);
    public static final Font FONT_RATE = new Font("SansSerif", Font.PLAIN, 13);

    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_LARGE = 24;
    public static final int PADDING_XLARGE = 32;

    public static final int CORNER_RADIUS = 12;
    public static final int BUTTON_CORNER_RADIUS = 8;
    public static final int FIELD_CORNER_RADIUS = 8;

    public static final int BUTTON_HEIGHT = 48;
    public static final int FIELD_HEIGHT = 44;
    public static final int ICON_SIZE = 20;

    public static final Cursor HAND_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public static final Cursor TEXT_CURSOR = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);

    public static final Color[] CHART_COLORS = {
        new Color(9, 132, 227),
        new Color(0, 184, 148),
        new Color(253, 203, 110),
        new Color(214, 48, 49),
        new Color(108, 92, 231),
        new Color(255, 118, 117),
        new Color(0, 206, 209),
        new Color(255, 165, 0),
        new Color(32, 178, 70),
        new Color(178, 102, 255)
    };
}
