package lexer;

public class LexerException extends Exception {

	private static final long serialVersionUID = 1L;

	public LexerException(String msg, int line) {
		super("Line:" + line + " " + msg);
	}

}
