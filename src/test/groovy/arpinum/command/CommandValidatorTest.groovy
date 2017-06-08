package arpinum.command

import org.hibernate.validator.constraints.NotEmpty

import spock.lang.Specification

import javax.validation.Validation
import java.util.function.Supplier

class CommandValidatorTest extends Specification {

    def factory = Validation.buildDefaultValidatorFactory()
    def validator = new CommandValidator(factory.validator)

    def "can validate a command"() {
        when:
        validator.validate(new FakeCommand(""))

        then:
        thrown(ValidationException)
    }

    def "gives error cause"() {
        when:
        validator.validate(new FakeCommand(""))

        then:
        ValidationException exception = thrown()
        !exception.messages().empty
        exception.messages()[0]
    }

    def "appelle la validation en début de synchronisation avec le bus"() {
        given:
        def next = Mock(Supplier)

        when:
        validator.intercept(Mock(CommandBus), new FakeCommand(""), next)

        then:
        thrown(ValidationException)
        0 * next.get()
    }

    private static class FakeCommand implements Command<String> {
        @NotEmpty
        String name

        FakeCommand(String name) {
            this.name = name
        }
    }
}
