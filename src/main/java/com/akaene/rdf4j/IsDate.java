package com.akaene.rdf4j;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TemporalAccessor;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.query.algebra.evaluation.TripleSource;
import org.eclipse.rdf4j.query.algebra.evaluation.ValueExprEvaluationException;
import org.eclipse.rdf4j.query.algebra.evaluation.function.Function;

/**
 * Checks if the specified value is a date corresponding to the specified format.
 */
public class IsDate implements Function {

    /**
     * Function namespace.
     */
    public static final String NAMESPACE = "http://akaene.com/ontologies/rdf4j/function/";

    @Override
    public String getURI() {
        return NAMESPACE + "isDate";
    }

    @Override
    public Value evaluate(ValueFactory valueFactory, Value... args) throws ValueExprEvaluationException {
        throw new UnsupportedOperationException("This version is deprecated.");
    }

    @Override
    public Value evaluate(TripleSource tripleSource, Value... args) throws ValueExprEvaluationException {
        if (args.length != 2) {
            throw new ValueExprEvaluationException("Required exactly one argument, got " + args.length);
        }
        if (!args[0].isLiteral()) {
            throw new ValueExprEvaluationException("Expected first argument to be a literal.");
        }
        if (!args[1].isLiteral()) {
            throw new ValueExprEvaluationException("Expected second argument to be a literal.");
        }
        final String format = args[1].stringValue();
        final String value = args[0].stringValue();
        final DateTimeFormatter df = DateTimeFormatterBuilder.ofPattern(format);
        try {
            final TemporalAccessor result = df.parse(value);
        } catch (DateTimeParseException ex) {
            return SimpleValueFactory().getInstance().createLiteral(false);
        }
        return SimpleValueFactory().getInstance().createLiteral(true);
    }
}
