package Util;

import javax.swing.text.*;

public class BoundDocument extends PlainDocument{
	private static final long serialVersionUID = 1L;
	
	protected int charLimit;
	protected JTextComponent textComp;
	
	public BoundDocument(int charLimit) { 
		this.charLimit = charLimit; 
	}
	public BoundDocument(int charLimit, JTextComponent textComp) { 
		this.charLimit = charLimit; this.textComp = textComp;
	}

	public void insertString (int offs, String str, AttributeSet a) throws BadLocationException {
		if (textComp.getText().getBytes().length + str.getBytes().length <= charLimit) { 
			super.insertString(offs, str, a); 
		}
	}
}
