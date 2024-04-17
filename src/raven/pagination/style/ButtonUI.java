package raven.pagination.style;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class ButtonUI extends BasicButtonUI {

    private boolean hover;
    private JButton button;

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        button = (JButton) c;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
            }
        });
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);
        button.setForeground(Color.YELLOW); // Màu font luôn là màu vàng
        button.setBackground(Color.GRAY); // Màu nền luôn là màu xám
        button.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        if (button.isSelected() || hover) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color backgroundColor = button.isSelected() ? Color.DARK_GRAY : (hover ? Color.DARK_GRAY : button.getBackground());
            g2.setColor(backgroundColor);
            int width = c.getWidth();
            int height = c.getHeight();
            Shape shape = new RoundRectangle2D.Double(0, 0, width, height, 5, 5);
            g2.fill(shape);
            g2.dispose();
        }
        super.paint(g, c);
    }
}
