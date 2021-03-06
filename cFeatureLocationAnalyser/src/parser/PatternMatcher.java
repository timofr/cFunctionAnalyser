package parser;

import java.util.LinkedList;
import java.util.List;

import lexer.Token;
import parser.pattern.AcquiresMacroPattern;
import parser.pattern.CloseCurlyBracketPattern;
import parser.pattern.CppDefinePattern;
import parser.pattern.CppElifPattern;
import parser.pattern.CppElsePattern;
import parser.pattern.CppEndifPattern;
import parser.pattern.CppIfDefinedPattern;
import parser.pattern.CppIfdefPattern;
import parser.pattern.CppIfndefPattern;
import parser.pattern.CppUndefinePattern;
import parser.pattern.FunctionDefinitionPattern;
import parser.pattern.OpenCurlyBracketPattern;
import parser.pattern.Pattern;
import parser.pattern.ReleasesMacroPattern;

public class PatternMatcher {

	
	
	public static PatternMatcher getCppDefinePatternMatcher() {
		return new PatternMatcher(CppDefinePattern.getInstance());
	}
	
	public static PatternMatcher getCppUndefinePatternMatcher() {
		return new PatternMatcher(CppUndefinePattern.getInstance());
	}
	
	public static PatternMatcher getCppIfDefinedPatternMatcher() {
		return new PatternMatcher(CppIfDefinedPattern.getInstance());
	}
	
	
	public static PatternMatcher getCppIfdefPatternMatcher() {
		return new PatternMatcher(CppIfdefPattern.getInstance());
	}
	
	public static PatternMatcher getCppIfndefPatternMatcher() {
		return new PatternMatcher(CppIfndefPattern.getInstance());
	}
	
	public static PatternMatcher getCppElifPatternMatcher() {
		return new PatternMatcher(CppElifPattern.getInstance());
	}
	
	public static PatternMatcher getCppElsePatternMatcher() {
		return new PatternMatcher(CppElsePattern.getInstance());
	}
	
	public static PatternMatcher getCppEndifPatternMatcher() {
		return new PatternMatcher(CppEndifPattern.getInstance());
	}
	
	public static PatternMatcher getFunctionDefinitionPatternMatcher() {
		return new PatternMatcher(FunctionDefinitionPattern.getInstance());
	}
	
	public static PatternMatcher getOpenCurlyBracketPatternMatcher() {
		return new PatternMatcher(OpenCurlyBracketPattern.getInstance());
	}
	
	public static PatternMatcher getCloseCurlyBracketPatternMatcher() {
		return new PatternMatcher(CloseCurlyBracketPattern.getInstance());
	}
	
	public static PatternMatcher getAcquiresMacroPatternMatcher() {
		return new PatternMatcher(AcquiresMacroPattern.getInstance());
	}
	
	public static PatternMatcher getReleasesMacroPatternMatcher() {
		return new PatternMatcher(ReleasesMacroPattern.getInstance());
	}
	
	Pattern pattern;
	List<PatternOccurance> currentPatterns = new LinkedList<PatternOccurance>();
	
	private PatternMatcher(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public List<PatternOccurance> match(Token lookahead) {
		List<PatternOccurance> occurance = new LinkedList<PatternOccurance>();
		List<PatternOccurance> oToRemove = new LinkedList<PatternOccurance>();
		for(PatternOccurance o : currentPatterns) {
			try {
				if(pattern.process(o, lookahead)) {
					oToRemove.add(o);
					occurance.add(o);
					o.setEndLine(lookahead.getEnd());
				}
			} catch (UnexpectedTokenException e) {
				oToRemove.add(o);
			} catch (SingleTokenPatternException e) {
				oToRemove.add(o);
				occurance.add(o);
				o.setEndLine(o.getStartLine());
			}
		}
		
		currentPatterns.removeAll(oToRemove);
		
		if(this.pattern.match(lookahead))
			currentPatterns.add(new PatternOccurance(this.pattern, lookahead));
		return occurance;
	}
}
