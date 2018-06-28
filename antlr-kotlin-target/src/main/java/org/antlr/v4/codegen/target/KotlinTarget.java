package org.antlr.v4.codegen.target;

import org.antlr.v4.codegen.CodeGenerator;
import org.antlr.v4.codegen.Target;
import org.antlr.v4.codegen.UnicodeEscapes;
import org.antlr.v4.tool.ast.GrammarAST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.StringRenderer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class KotlinTarget extends Target {

	private static final String VERSION = "4.7.1";

	/**
	 * The Kotlin target can cache the code generation templates.
	 */
	private static final ThreadLocal<STGroup> targetTemplates = new ThreadLocal<STGroup>();

	protected static final String[] kotlinKeywords = {
		"abstract", "catch",
		"class", "const", "else",
		"enum",  "false", "for",
		"if", "implements", "import", "is", "interface",
		"null", "package", "private", "internal",
		"public", "return",
		"this", "throw", "true", "try",
		"while"
	};

	/** Avoid grammar symbols in this set to prevent conflicts in gen'd code. */
	protected final Set<String> badWords = new HashSet<String>();

	public KotlinTarget(CodeGenerator gen) {
		super(gen, "Kotlin");
		targetCharValueEscape = new String[255];
		targetCharValueEscape['\n'] = "\\n";
		targetCharValueEscape['\r'] = "\\r";
		targetCharValueEscape['\t'] = "\\t";
		targetCharValueEscape['\b'] = "\\b";
		//targetCharValueEscape['\f'] = "\\u000c";
		targetCharValueEscape['\\'] = "\\\\";
		targetCharValueEscape['\''] = "\\'";
		targetCharValueEscape[10] = "\\u000a";
		targetCharValueEscape[11] = "\\u000b";
		targetCharValueEscape[12] = "\\u000c";
		targetCharValueEscape[13] = "\\u000d";
		targetCharValueEscape[14] = "\\u000e";
		targetCharValueEscape[15] = "\\u000f";
		targetCharValueEscape['"'] = "\\\"";
		targetCharValueEscape['$'] = "\\$";
	}

    @Override
    public String getVersion() {
        return VERSION;
    }

    public Set<String> getBadWords() {
		if (badWords.isEmpty()) {
			addBadWords();
		}

		return badWords;
	}

	protected void addBadWords() {
		badWords.addAll(Arrays.asList(kotlinKeywords));
		badWords.add("rule");
		badWords.add("parserRule");
	}

	@Override
	public int getSerializedATNSegmentLimit() {
		// 65535 is the class file format byte limit for a UTF-8 encoded string literal
		// 3 is the maximum number of bytes it takes to encode a value in the range 0-0xFFFF
		return 65535 / 3;
	}

	@Override
	protected boolean visibleGrammarSymbolCausesIssueInGeneratedCode(GrammarAST idNode) {
		return getBadWords().contains(idNode.getText());
	}

	@Override
	protected STGroup loadTemplates() {
		STGroup result = targetTemplates.get();
		if (result == null) {
			result = super.loadTemplates();
			result.registerRenderer(String.class, new StringRenderer(), true);
			targetTemplates.set(result);
		}

		return result;
	}

	@Override
	public String encodeIntAsCharEscape(int v) {
		return Integer.toString(v);
	}

	@Override
	protected void appendUnicodeEscapedCodePoint(int codePoint, StringBuilder sb) {
		//System.out.println("AAAA "+codePoint);
		UnicodeEscapes.appendJavaStyleEscapedCodePoint(codePoint, sb);
	}

	@Override
	public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal, boolean addQuotes) {
		return super.getTargetStringLiteralFromANTLRStringLiteral(generator, literal, addQuotes).replace("$", "\\$");
	}
}
