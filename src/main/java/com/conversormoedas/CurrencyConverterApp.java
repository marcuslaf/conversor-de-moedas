package com.conversormoedas;

import com.conversormoedas.service.ExchangeRateService;
import com.conversormoedas.ui.MainFrame;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class CurrencyConverterApp {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            configureUIManager();
        } catch (Exception e) {
            System.err.println("FlatLaf não carregou, usando Metal: " + e.getMessage());
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("Look and feel padrão não disponível");
            }
        }

        SwingUtilities.invokeLater(() -> {
            try {
                ExchangeRateService service = new ExchangeRateService();
                MainFrame frame = new MainFrame(service);
                frame.setVisible(true);
            } catch (IllegalStateException e) {
                JOptionPane.showMessageDialog(null,
                    "Erro de configuração:\n\n" + e.getMessage() +
                    "\n\nA aplicação será encerrada.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

    private static void configureUIManager() {
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("TextComponent.arc", 8);

        UIManager.put("Button.background", new Color(9, 132, 227));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 14));

        UIManager.put("Component.background", Color.WHITE);
        UIManager.put("Component.foreground", new Color(45, 52, 54));
        UIManager.put("Component.font", new Font("SansSerif", Font.PLAIN, 14));

        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", new Color(45, 52, 54));
        UIManager.put("ComboBox.font", new Font("SansSerif", Font.PLAIN, 14));

        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", new Color(45, 52, 54));
        UIManager.put("TextField.font", new Font("SansSerif", Font.BOLD, 16));
        UIManager.put("TextField.placeholderForeground", new Color(178, 190, 195));

        UIManager.put("List.background", Color.WHITE);
        UIManager.put("List.foreground", new Color(45, 52, 54));
        UIManager.put("List.selectionBackground", new Color(9, 132, 227));
        UIManager.put("List.selectionForeground", Color.WHITE);

        UIManager.put("ScrollPane.background", new Color(245, 246, 250));
        UIManager.put("ScrollBar.background", new Color(245, 246, 250));
        UIManager.put("ScrollBar.thumb", new Color(200, 200, 210));
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbWidth", 8);
    }
}
