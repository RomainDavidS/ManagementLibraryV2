package mbooks.technical.state.books;

import mbooks.technical.state.reservation.State;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class BooksStateConverter implements AttributeConverter<BooksState,String> {

    @Override
    public String convertToDatabaseColumn(BooksState booksState) {
        if (booksState == null) {
            return null;
        }
        return booksState.getCode();
    }

    @Override
    public BooksState convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(BooksState.values())
                .filter(c -> c.getCode().equals( code ))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
