package com.conversormoedas.ui;

import com.conversormoedas.model.Currency;

import javax.swing.*;
import java.awt.*;

public class CurrencyComboBoxRenderer extends JPanel implements ListCellRenderer<Currency> {

    private final JLabel flagLabel;
    private final JLabel codeLabel;
    private final JLabel nameLabel;
    private boolean isSelected;
    private boolean isComboBox;

    public CurrencyComboBoxRenderer(boolean isComboBox) {
        this.isComboBox = isComboBox;
        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 4));
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        flagLabel = new JLabel();
        flagLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        flagLabel.setPreferredSize(new Dimension(30, 24));

        codeLabel = new JLabel();
        codeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        codeLabel.setPreferredSize(new Dimension(45, 20));

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        add(flagLabel);
        add(codeLabel);
        add(nameLabel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Currency> list, Currency value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        this.isSelected = isSelected;

        if (value != null) {
            flagLabel.setText(value.getFlag());
            codeLabel.setText(value.name());
            nameLabel.setText(value.getDisplayName());

            if (isSelected) {
                setBackground(Theme.PRIMARY);
                setForeground(Theme.TEXT_ON_PRIMARY);
                codeLabel.setForeground(Theme.TEXT_ON_PRIMARY);
                nameLabel.setForeground(Theme.TEXT_ON_PRIMARY);
            } else {
                setBackground(Theme.SURFACE);
                setForeground(Theme.TEXT_PRIMARY);
                codeLabel.setForeground(Theme.TEXT_PRIMARY);
                nameLabel.setForeground(Theme.TEXT_SECONDARY);
            }
        }

        return this;
    }
}
