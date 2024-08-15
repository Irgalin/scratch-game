import com.assignment.args.ArgumentParser;
import com.assignment.args.ParsedArguments;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    private static final String CONFIG_PATH = Paths.get("src", "test", "resources", "config.json").toString();
    private static final String NON_JSON_FILE_PATH = Paths.get("src", "test", "resources", "non-json-file.txt").toString();

    @Test
    void testValidArguments() {
        String[] args = {"--config", CONFIG_PATH, "--betting-amount", "100"};
        try {
            ParsedArguments parsedArgs = ArgumentParser.parseArguments(args);
            assertEquals(CONFIG_PATH, parsedArgs.configFilePath());
            assertEquals(100, parsedArgs.bettingAmount());
        } catch (ParseException e) {
            fail("Parsing failed for valid arguments");
        }
    }

    @Test
    void testMissingConfigArgument() {
        String[] args = {"--betting-amount", "100"};
        assertThrows(ParseException.class, () -> ArgumentParser.parseArguments(args));
    }

    @Test
    void testMissingBettingAmountArgument() {
        String[] args = {"--config", CONFIG_PATH};
        assertThrows(ParseException.class, () -> ArgumentParser.parseArguments(args));
    }

    @Test
    void testWrongBettingAmountArgument() {
        String[] args = {"--config", CONFIG_PATH, "--betting-amount", "abc"};
        assertThrows(ParseException.class, () -> ArgumentParser.parseArguments(args));
    }

    @Test
    void testWrongConfigArgument() {
        String[] args = {"--config", "wrong/path/config.json", "--betting-amount", "100"};
        assertThrows(ParseException.class, () -> ArgumentParser.parseArguments(args));
    }

    @Test
    void testNonJsonConfigArgument() {
        String[] args = {"--config", NON_JSON_FILE_PATH, "--betting-amount", "100"};
        assertThrows(ParseException.class, () -> ArgumentParser.parseArguments(args));
    }

    @Test
    void testExtraArguments() {
        String[] args = {"--config", CONFIG_PATH, "--betting-amount", "100", "--extra", "value"};
        try {
            ParsedArguments parsedArgs = ArgumentParser.parseArguments(args);
            assertEquals(CONFIG_PATH, parsedArgs.configFilePath());
            assertEquals(100, parsedArgs.bettingAmount());
        } catch (ParseException e) {
            fail("Parsing failed for extra arguments");
        }
    }
}