/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen
 *     - Initial API and implementation
 *******************************************************************************/
package net.sf.robocode.ui.editor;


import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.InputMap;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;


/**
 * Editor pane used for editing source code.
 *
 * @author Flemming N. Larsen (original)
 */
@SuppressWarnings("serial")
public class EditorPane extends JTextPane {

	private int tabSize = 4; // Default

	// Key bindings
	private static final KeyStroke CUT_KEYSTROKE = KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK);
	private static final KeyStroke COPY_KEYSTROKE = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK);
	private static final KeyStroke PASTE_KEYSTROKE = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK);
	private static final KeyStroke UNDO_KEYSTROKE = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
	private static final KeyStroke REDO_KEYSTROKE = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);

	private final JavaDocument document = new JavaDocument(this);

	private final CompoundUndoManager undoManager = new CompoundUndoManager();

	private final TextTool textTool = new TextTool();

	public EditorPane() {
		super();
		new HighlightLinePainter(this);

		EditorKit editorKit = new StyledEditorKit() {
			@Override
			public Document createDefaultDocument() {
				return document;
			}
		};

		setEditorKitForContentType("text/java", editorKit);
		setContentType("text/java");

		addPropertyChangeListener("font", new FontHandler());
		addKeyListener(new KeyHandler());

		setKeyBindings();
		setActionBindings();

		getDocument().addUndoableEditListener(undoManager);		
	}
	
	// No line wrapping!
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return getUI().getPreferredSize(this).width <= getParent().getSize().width;
	}

	// Don't enforce height -> Avoid line height changes
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	// Make sure to discard all undo/redo edits when text completely replaced
	@Override
	public void setText(String text) {
		super.setText(text);
		undoManager.discardAllEdits();
	}

	public void undo() {
		if (undoManager.canUndo()) {
			undoManager.undo();
		}
	}
	
	public void redo() {
		if (undoManager.canRedo()) {
			undoManager.redo();
		}
	}

	private void setKeyBindings() {
		InputMap inputMap = this.getInputMap();

		inputMap.put(CUT_KEYSTROKE, DefaultEditorKit.cutAction);
		inputMap.put(COPY_KEYSTROKE, DefaultEditorKit.copyAction);
		inputMap.put(PASTE_KEYSTROKE, DefaultEditorKit.pasteAction);
		inputMap.put(UNDO_KEYSTROKE, undoManager.getUndoAction());
		inputMap.put(REDO_KEYSTROKE, undoManager.getRedoAction());
	}

	private void setActionBindings() {
		getActionMap().put("undo-keystroke", undoManager.getUndoAction());
		getActionMap().put("redo-keystroke", undoManager.getRedoAction());
	}

	private void setTabSize(int tabSize) {
		document.setTabSize(tabSize);
		
		FontMetrics fm = getFontMetrics(getFont());
		int charWidth = fm.charWidth('#');
		int tabWidth = charWidth * tabSize;

		TabStop[] tabs = new TabStop[100];

		for (int j = 0; j < tabs.length; j++) {
			tabs[j] = new TabStop((j + 1) * tabWidth);
		}

		SimpleAttributeSet attributes = new SimpleAttributeSet();

		StyleConstants.setTabSet(attributes, new TabSet(tabs));

		getDocument().removeUndoableEditListener(undoManager); // Avoid this change to be undone
		getStyledDocument().setParagraphAttributes(0, getDocument().getLength(), attributes, false);
		getDocument().addUndoableEditListener(undoManager);
	}

	private void onTabCharPressed(boolean isUnindent) {

		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();

		if (selectionStart == selectionEnd) {
			// No selection -> Handle normal tab indentation

			if (isUnindent) {
				textTool.removeLastTab(getCaretPosition());
			} else {
				textTool.insertString(getCaretPosition(), "\t");
			}
		} else {
			// Start block indentation

			// Make sure that the start selection is lesser than the end selection
			if (selectionStart > selectionEnd) {
				int storedSelectionStart = selectionStart;

				selectionStart = selectionEnd;
				selectionEnd = storedSelectionStart;
			}

			String text = textTool.getText();

			int startOffset = selectionStart;
			int endOffset = selectionEnd;

			StringBuilder newText = new StringBuilder();
			int count = 0;
			int selectStart = selectionStart;
			int selectEnd = selectionEnd;
			boolean stopUnindent = false;

			// Expand to nearest newlines
			startOffset = textTool.getLineStart(startOffset);
			endOffset = textTool.getLineStop(endOffset) - 1; // -1 -> Ignore newline character

			// Handle each line

			String split = text.substring(startOffset, endOffset);
			String[] lines = split.split(String.valueOf('\n'));

			// iterate lines, rebuilding tabs and newlines
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i];

				if (isUnindent) {
					if (line.charAt(0) != '\t') {
						stopUnindent = true;
						break;
					} else {
						newText.append(line.substring(1, line.length()));
						count++;
					}
				} else {
					newText.append('\t' + line);
					count++;
				}
				if (i != lines.length - 1) {
					newText.append('\n');
				}
			}
			if (!stopUnindent) {
				try {
					getDocument().remove(startOffset, split.length());
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				textTool.insertString(getCaretPosition(), newText.toString());

				// compute new selection
				int mod = isUnindent ? -1 : 1;

				if (count > 0) {
					selectStart = selectStart + mod;
					selectEnd = selectEnd + count * mod;
				}
				setSelectionStart(selectStart);
				setSelectionEnd(selectEnd);
			}
		}
	}

	private class FontHandler implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent e) {
			setTabSize(tabSize);
		}
	}


	private class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_TAB:
				onTabCharPressed(e.isShiftDown());
				e.consume();
				break;
			// case KeyEvent.VK_ENTER :
			// snapshot ();
			// insertNewLine ();
			// e.consume ();
			// break;
			}
		}
	}


	/**
	 * Handy text tool.
	 */
	private class TextTool {

		String getText() {
			try {
				return getDocument().getText(0, getDocument().getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			return null;
		}

		Element getLine(int offset) {
			Element root = getDocument().getDefaultRootElement();

			return root.getElement(root.getElementIndex(offset));
		}

		int getLineStart(int offset) {
			return getLine(offset).getStartOffset();
		}

		int getLineStop(int offset) {
			return getLine(offset).getEndOffset();
		}

		void insertString(int offset, String str) {
			insertString(offset, str, null);
		}

		void insertString(final int offset, final String str, final AttributeSet a) {
			try {
				getDocument().insertString(offset, str, a);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}

		void removeLastTab(int offset) {
			int pos = offset;

			while (--pos >= 0) {
				try {
					char ch = getDocument().getText(pos, 1).charAt(0);

					if (ch == '\t') {
						remove(pos, 1);
						return;
					}
					if (ch == '\n') {
						return;
					}
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		}
		
		void remove(final int offset, final int len) {
			try {
				getDocument().remove(offset, len);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
