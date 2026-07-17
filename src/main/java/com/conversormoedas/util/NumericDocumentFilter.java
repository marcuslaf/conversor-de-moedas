package com.conversormoedas.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericDocumentFilter extends DocumentFilter {

    private final boolean allowDecimals;
    private final int maxLength;

    public NumericDocumentFilter() {
        this(true, 15);
    }

    public NumericDocumentFilter(boolean allowDecimals) {
        this(allowDecimals, 15);
    }

    public NumericDocumentFilter(boolean allowDecimals, int maxLength) {
        this.allowDecimals = allowDecimals;
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null || string.isEmpty()) {
            return;
        }

        String filtered = filterInput(string);
        if (!filtered.isEmpty()) {
            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = current.substring(0, offset) + filtered + current.substring(offset);

            if (newText.length() <= maxLength && isValidFormat(newText)) {
                super.insertString(fb, offset, filtered, attr);
            }
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null || text.isEmpty()) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }

        String filtered = filterInput(text);
        if (!filtered.isEmpty()) {
            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = current.substring(0, offset) + filtered + current.substring(offset + length);

            if (newText.length() <= maxLength && isValidFormat(newText)) {
                super.replace(fb, offset, length, filtered, attrs);
            }
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    private String filterInput(String input) {
        StringBuilder filtered = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                filtered.append(c);
            } else if (allowDecimals && c == '.' && filtered.indexOf('.') == -1) {
                filtered.append(c);
            } else if (allowDecimals && c == ',' && filtered.indexOf('.') == -1) {
                filtered.append('.');
            }
        }

        return filtered.toString();
    }

    private boolean isValidFormat(String text) {
        if (text.isEmpty()) {
            return true;
        }

        if (text.startsWith(".") || text.endsWith(".")) {
            return false;
        }

        int dotIndex = text.indexOf('.');
        if (dotIndex != -1) {
            int decimalPlaces = text.length() - dotIndex - 1;
            if (decimalPlaces > 2) {
                return false;
            }
        }

        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
