package com.akaene.rdf4j;

import org.eclipse.rdf4j.common.iteration.CloseableIteration;
import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.algebra.evaluation.TripleSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsDateTest {

    private final IsDate sut = new IsDate();

    static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of(LocalDate.now().toString(), "uuuu-MM-dd", true),
                Arguments.of("2016-13-05", "uuuu-MM-dd", false),
                Arguments.of("5.10.2016", "uuuu-MM-dd", false),
                Arguments.of("05.10.2016", "dd.MM.uuuu", true)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestValues")
    void evaluateReturnsBooleanLiteralCorrespondingToValueBeingDate(String value, String format,
                                                                    boolean expectedResult) {
        final ValueFactory vf = SimpleValueFactory.getInstance();
        final TripleSource ts = new TripleSource() {
            @Override
            public CloseableIteration<? extends Statement, QueryEvaluationException> getStatements(Resource resource,
                                                                                                   IRI iri, Value value,
                                                                                                   Resource... resources) throws QueryEvaluationException {
                return null;
            }

            @Override
            public ValueFactory getValueFactory() {
                return vf;
            }
        };
        assertEquals(vf.createLiteral(expectedResult), sut.evaluate(ts, vf.createLiteral(value), vf.createLiteral(format)));
    }
}