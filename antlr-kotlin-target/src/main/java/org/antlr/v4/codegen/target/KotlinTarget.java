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
		targetCharValueEscape['\f'] = null;
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
		// The kotlin compiler seems to like to stack overflow on big string literals full of escapes
		return 5000;
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
		if (v < Character.MIN_VALUE || v > Character.MAX_VALUE) {
			throw new IllegalArgumentException(String.format("Cannot encode the specified value: %d", v));
		}

		if (v >= 0 && v < targetCharValueEscape.length && targetCharValueEscape[v] != null) {
			return targetCharValueEscape[v];
		}

		if (v >= 0x20 && v < 127 && (!Character.isDigit(v) || v == '8' || v == '9')) {
			return String.valueOf((char)v);
		}

		String hex = Integer.toHexString(v|0x10000).substring(1,5);
		return "\\u"+hex;
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
