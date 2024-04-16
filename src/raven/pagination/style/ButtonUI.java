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
                button.repaint(); // Yêu cầu vẽ lại khi di chuột vào
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                button.repaint(); // Yêu cầu vẽ lại khi di chuột ra
            }
        });
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);
        button.setForeground(Color.WHITE); // Màu chữ là màu trắng
        button.setBackground(Color.BLACK); // Màu nền là màu đen
        button.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        if (button.isSelected() || hover) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (hover) {
                g2.setColor(Color.WHITE); // Màu chữ khi di chuột vào là màu trắng
            } else {
                g2.setColor(Color.WHITE); // Màu chữ khi không di chuột vào là màu trắng
            }
            int width = c.getWidth();
            int height = c.getHeight();
            if (button.isSelected()) {
                g2.setColor(Color.WHITE); // Màu chữ khi nút được chọn là màu trắng
                g2.fillRect(0, 0, width, height); // Vẽ màu nền là màu trắng khi nút được chọn
            }
            g2.dispose();
        }
        super.paint(g, c);
    }
}
