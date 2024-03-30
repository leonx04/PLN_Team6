package raven.pagination.style;

import javax.swing.JButton;
import pagination.DefaultPaginationItemRender;

public class PaginationItemRenderStyle extends DefaultPaginationItemRender {

    @Override
    public JButton createButton(Object value, boolean isPrevious, boolean isNext, boolean enable) {
        JButton button = super.createButton(value, isPrevious, isNext, enable);
        button.setUI(new ButtonUI());
        return button;
    }

    @Override
    public Object createPreviousIcon() {
        return "Previous";
    }

    @Override
    public Object createNextIcon() {
        return "Next";
    }
}
