package br.ufsc.ine.leb.roza.ui.shared;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class WrapLayout extends FlowLayout {

	private static final long serialVersionUID = 1L;

	public WrapLayout() {
		super(WrapLayout.LEFT);
	}

	@Override
	public Dimension preferredLayoutSize(Container target) {
		return layoutSize(target, true);
	}

	@Override
	public Dimension minimumLayoutSize(Container target) {
		Dimension minimum = layoutSize(target, false);
		minimum.width -= (getHgap() + 1);
		return minimum;
	}

	private Dimension layoutSize(Container target, boolean preferred) {
		synchronized (target.getTreeLock()) {
			Container container = target;
			while (container.getSize().width == 0 && container.getParent() != null) {
				container = container.getParent();
			}
			int targetWidth = container.getSize().width;
			if (targetWidth == 0) {
				targetWidth = Integer.MAX_VALUE;
			}
			int hgap = getHgap();
			int vgap = getVgap();
			Insets insets = target.getInsets();
			int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
			int maxWidth = targetWidth - horizontalInsetsAndGap;
			Dimension dim = new Dimension(0, 0);
			int rowWidth = 0;
			int rowHeight = 0;
			int nmembers = target.getComponentCount();
			for (int index = 0; index < nmembers; index++) {
				Component component = target.getComponent(index);
				if (component.isVisible()) {
					Dimension d = preferred ? component.getPreferredSize() : component.getMinimumSize();
					if (rowWidth + d.width > maxWidth) {
						addRow(dim, rowWidth, rowHeight);
						rowWidth = 0;
						rowHeight = 0;
					}
					if (rowWidth != 0) {
						rowWidth += hgap;
					}
					rowWidth += d.width;
					rowHeight = Math.max(rowHeight, d.height);
				}
			}
			addRow(dim, rowWidth, rowHeight);
			dim.width += horizontalInsetsAndGap;
			dim.height += insets.top + insets.bottom + vgap * 2;
			Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
			if (scrollPane != null && target.isValid()) {
				dim.width -= (hgap + 1);
			}
			return dim;
		}
	}

	private void addRow(Dimension dim, int rowWidth, int rowHeight) {
		dim.width = Math.max(dim.width, rowWidth);
		if (dim.height > 0) {
			dim.height += getVgap();
		}
		dim.height += rowHeight;
	}

}
