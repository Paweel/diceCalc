package com.example.dicecalc.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.text.MessageFormat;

public class CustomErrorListener extends BaseErrorListener {

    public static final CustomErrorListener INSTANCE = new CustomErrorListener();

    private CustomErrorListener() {
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new ParseCancellationException(MessageFormat.format("line: {0}, char position: {1}, {2}", line, charPositionInLine, msg));
    }
}
