package com.conversormoedas.ui;

import com.conversormoedas.model.ConversionResult;
import com.conversormoedas.model.Currency;
import com.conversormoedas.service.ExchangeRateService;
import com.conversormoedas.util.CurrencyFormatter;
import com.conversormoedas.util.NumericDocumentFilter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private final ExchangeRateService exchangeRateService;
    private final List<ConversionResult> history;

    private JTextField amountField;
    private JComboBox<Currency> fromCurrencyCombo;
    private JComboBox<Currency> toCurrencyCombo;
    private JButton convertButton;
    private JButton swapButton;
    private JLabel resultLabel;
    private JLabel rateLabel;
    private JLabel statusLabel;
    private JPanel resultPanel;
    private JList<String> historyList;
    private DefaultListModel<String> historyModel;

    public MainFrame(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
        this.history = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        setTitle("Conversor de Moedas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 750);
        setMinimumSize(new Dimension(480, 700));
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Theme.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(
            Theme.PADDING_LARGE, Theme.PADDING_LARGE,
            Theme.PADDING_LARGE, Theme.PADDING_LARGE
        ));

        mainPanel.add(createHeader());
        mainPanel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        mainPanel.add(createInputPanel());
        mainPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        mainPanel.add(createSwapButton());
        mainPanel.add(Box.createVerticalStrut(Theme.PADDING_SMALL));
        mainPanel.add(createOutputPanel());
        mainPanel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        mainPanel.add(createResultPanel());
        mainPanel.add(Box.createVerticalStrut(Theme.PADDING_MEDIUM));
        mainPanel.add(createHistoryPanel());
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(createFooter());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel titleLabel = new JLabel("Conversor de Moedas");
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Taxas de câmbio em tempo real");
        subtitleLabel.setFont(Theme.FONT_SMALL);
        subtitleLabel.setForeground(Theme.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titleLabel);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitleLabel);

        return header;
    }

    private JPanel createInputPanel() {
        JPanel panel = createCard();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 12, 8, 12);

        JLabel amountLabel = new JLabel("Valor");
        amountLabel.setFont(Theme.FONT_BODY_BOLD);
        amountLabel.setForeground(Theme.TEXT_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(amountLabel, gbc);

        amountField = new JTextField("1.00");
        amountField.setFont(new Font("SansSerif", Font.BOLD, 18));
        amountField.setPreferredSize(new Dimension(200, Theme.FIELD_HEIGHT));
        amountField.setMaximumSize(new Dimension(200, Theme.FIELD_HEIGHT));
        amountField.setBackground(Theme.SURFACE);
        amountField.setForeground(Theme.TEXT_PRIMARY);
        amountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.SURFACE_BORDER, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        amountField.setFocusTraversalKeysEnabled(false);

        ((AbstractDocument) amountField.getDocument()).setDocumentFilter(
            new NumericDocumentFilter(true, 10)
        );

        amountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performConversion();
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(amountField, gbc);

        JLabel fromLabel = new JLabel("De:");
        fromLabel.setFont(Theme.FONT_BODY_BOLD);
        fromLabel.setForeground(Theme.TEXT_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(fromLabel, gbc);

        fromCurrencyCombo = createCurrencyComboBox();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(fromCurrencyCombo, gbc);

        return panel;
    }

    private JButton createSwapButton() {
        swapButton = new JButton("Trocar Moedas");
        swapButton.setFont(Theme.FONT_BODY);
        swapButton.setForeground(Theme.PRIMARY);
        swapButton.setBackground(Theme.SURFACE);
        swapButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.PRIMARY, 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        swapButton.setCursor(Theme.HAND_CURSOR);
        swapButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        swapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                swapButton.setBackground(Theme.PRIMARY);
                swapButton.setForeground(Theme.TEXT_ON_PRIMARY);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                swapButton.setBackground(Theme.SURFACE);
                swapButton.setForeground(Theme.PRIMARY);
            }
        });

        swapButton.addActionListener(e -> swapCurrencies());

        return swapButton;
    }

    private JPanel createOutputPanel() {
        JPanel panel = createCard();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 12, 8, 12);

        JLabel toLabel = new JLabel("Para:");
        toLabel.setFont(Theme.FONT_BODY_BOLD);
        toLabel.setForeground(Theme.TEXT_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(toLabel, gbc);

        toCurrencyCombo = createCurrencyComboBox();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(toCurrencyCombo, gbc);

        return panel;
    }

    private JPanel createResultPanel() {
        resultPanel = createCard();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.SUCCESS, 1),
            new EmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE,
                          Theme.PADDING_MEDIUM, Theme.PADDING_LARGE)
        ));
        resultPanel.setBackground(new Color(232, 255, 248));

        resultLabel = new JLabel("Selecione as moedas e clique em Converter");
        resultLabel.setFont(Theme.FONT_RESULT);
        resultLabel.setForeground(Theme.TEXT_SECONDARY);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rateLabel = new JLabel("");
        rateLabel.setFont(Theme.FONT_RATE);
        rateLabel.setForeground(Theme.TEXT_MUTED);
        rateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultPanel.add(resultLabel);
        resultPanel.add(Box.createVerticalStrut(8));
        resultPanel.add(rateLabel);

        return resultPanel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = createCard();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(
            Theme.PADDING_SMALL, Theme.PADDING_SMALL,
            Theme.PADDING_SMALL, Theme.PADDING_SMALL
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        JLabel historyTitle = new JLabel("Histórico");
        historyTitle.setFont(Theme.FONT_SUBTITLE);
        historyTitle.setForeground(Theme.TEXT_PRIMARY);
        historyTitle.setBorder(new EmptyBorder(0, 8, 8, 0));

        historyModel = new DefaultListModel<>();
        historyList = new JList<>(historyModel);
        historyList.setFont(Theme.FONT_SMALL);
        historyList.setForeground(Theme.TEXT_SECONDARY);
        historyList.setBackground(Theme.SURFACE);
        historyList.setSelectionBackground(Theme.PRIMARY_LIGHT);
        historyList.setSelectionForeground(Theme.TEXT_ON_PRIMARY);

        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(0, 120));

        panel.add(historyTitle, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        statusLabel = new JLabel("Pronto");
        statusLabel.setFont(Theme.FONT_SMALL);
        statusLabel.setForeground(Theme.TEXT_MUTED);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        footer.add(statusLabel);
        return footer;
    }

    private JPanel createCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Theme.SURFACE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), Theme.CORNER_RADIUS, Theme.CORNER_RADIUS);
                g2d.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(
            Theme.PADDING_MEDIUM, Theme.PADDING_LARGE,
            Theme.PADDING_MEDIUM, Theme.PADDING_LARGE
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        return card;
    }

    private JComboBox<Currency> createCurrencyComboBox() {
        JComboBox<Currency> comboBox = new JComboBox<>(Currency.values());
        comboBox.setFont(Theme.FONT_BODY);
        comboBox.setPreferredSize(new Dimension(300, Theme.FIELD_HEIGHT));
        comboBox.setMaximumSize(new Dimension(300, Theme.FIELD_HEIGHT));
        comboBox.setRenderer(new CurrencyComboBoxRenderer(true));
        return comboBox;
    }

    private void swapCurrencies() {
        Currency from = (Currency) fromCurrencyCombo.getSelectedItem();
        Currency to = (Currency) toCurrencyCombo.getSelectedItem();

        if (from != null && to != null) {
            fromCurrencyCombo.setSelectedItem(to);
            toCurrencyCombo.setSelectedItem(from);

            if (!amountField.getText().isEmpty()) {
                performConversion();
            }
        }
    }

    private void performConversion() {
        String amountText = amountField.getText().trim();

        if (amountText.isEmpty()) {
            showError("Por favor, insira um valor para converter.");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountText);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showError("O valor deve ser maior que zero.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Valor inválido. Use apenas números.");
            return;
        }

        Currency from = (Currency) fromCurrencyCombo.getSelectedItem();
        Currency to = (Currency) toCurrencyCombo.getSelectedItem();

        if (from == null || to == null) {
            showError("Selecione as moedas de origem e destino.");
            return;
        }

        setConverting(true);
        statusLabel.setText("Convertendo...");
        statusLabel.setForeground(Theme.PRIMARY);

        SwingWorker<ConversionResult, Void> worker = new SwingWorker<>() {
            @Override
            protected ConversionResult doInBackground() throws Exception {
                return exchangeRateService.convert(amount, from, to);
            }

            @Override
            protected void done() {
                try {
                    ConversionResult result = get();
                    showResult(result);
                    addToHistory(result);
                    statusLabel.setText("Conversão realizada com sucesso");
                    statusLabel.setForeground(Theme.SUCCESS);
                } catch (Exception e) {
                    String errorMsg = e.getCause() != null
                        ? e.getCause().getMessage()
                        : e.getMessage();
                    showError("Erro ao converter: " + errorMsg);
                    statusLabel.setText("Erro na conversão");
                    statusLabel.setForeground(Theme.ERROR);
                } finally {
                    setConverting(false);
                }
            }
        };

        worker.execute();
    }

    private void showResult(ConversionResult result) {
        resultLabel.setText(result.getSummary());
        resultLabel.setForeground(Theme.SUCCESS_DARK);
        rateLabel.setText(result.getFormattedRate());

        resultPanel.setBackground(new Color(232, 255, 248));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.SUCCESS, 2),
            new EmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE,
                          Theme.PADDING_MEDIUM, Theme.PADDING_LARGE)
        ));
    }

    private void showError(String message) {
        resultLabel.setText(message);
        resultLabel.setForeground(Theme.ERROR);
        rateLabel.setText("");

        resultPanel.setBackground(new Color(255, 235, 236));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.ERROR, 2),
            new EmptyBorder(Theme.PADDING_MEDIUM, Theme.PADDING_LARGE,
                          Theme.PADDING_MEDIUM, Theme.PADDING_LARGE)
        ));
    }

    private void addToHistory(ConversionResult result) {
        String entry = String.format("%s → %s (%s)",
            result.getFormattedOriginal(),
            result.getFormattedConverted(),
            result.getFormattedRate()
        );
        historyModel.add(0, entry);

        if (historyModel.size() > 20) {
            historyModel.remove(20);
        }
    }

    private void setConverting(boolean converting) {
        convertButton.setEnabled(!converting);
        swapButton.setEnabled(!converting);
        amountField.setEnabled(!converting);
        fromCurrencyCombo.setEnabled(!converting);
        toCurrencyCombo.setEnabled(!converting);

        if (converting) {
            convertButton.setText("Convertendo...");
            convertButton.setBackground(Theme.TEXT_MUTED);
        } else {
            convertButton.setText("Converter");
            convertButton.setBackground(Theme.PRIMARY);
        }
    }
}
