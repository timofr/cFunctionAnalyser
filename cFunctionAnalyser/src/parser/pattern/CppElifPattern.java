package parser.pattern;

import java.util.List;

import lexer.Token;
import lexer.Token.TokenType;
import parser.PatternOccurance;
import parser.UnexpectedTokenException;

public class CppElifPattern extends CppPattern{

	private static Pattern pattern;
	
	private CppElifPattern() {}
	
	public static Pattern getInstance() {
        return pattern == null ? pattern = new CppElifPattern() : pattern;
	}
	
	
	@Override
	public boolean process(PatternOccurance occurance, Token lookahead) throws UnexpectedTokenException {
		List<Token> content = occurance.getContent();
		if(content.size() == 1) {
			if(lookahead.getType() == TokenType.IDENTIFIER && lookahead.getContent().equals("elif")) {
				content.add(lookahead);
				return false;
			}
		}
		else {
			if(lookahead.getType() == TokenType.NEWLINE)
				return true;
			content.add(lookahead);
			return false;
		}
		throw new UnexpectedTokenException();
	}
}
