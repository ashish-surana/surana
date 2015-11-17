package cscie97.asn4.housemate.exe.util;

import cscie97.asn4.housemate.model.service.exception.InvalidCommandException;

import java.util.Arrays;

/**
 * This class provides mechanism for parsing tokens of the input command string.
 */
public class CommandParser {

    private final String inputCommand;

    private String remainingText;

    public CommandParser(String inputCommand){
        assert inputCommand != null : "Input command cannot be null";

        this.inputCommand = inputCommand;
        this.remainingText = inputCommand;
    }

    /**
     * This method returns the next token delimited by whitespace.
     * @return
     */
    public String getNextToken(String tokenDescription) throws InvalidCommandException {
        if(remainingText == null){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be null");
        }

        remainingText = remainingText.trim();

        if("".equals(remainingText)){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be empty string");
        }

        String[] splitText = remainingText.split(" ", 2);
        String nextToken = splitText[0];

        if(splitText.length == 2){
            remainingText = splitText[1];
        }else{
            remainingText = null;
        }

        if(nextToken == null || "".equals(nextToken)){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be null or empty string");
        }

        return nextToken;
    }

    public String getNextTokenInDoubleQuotes(String tokenDescription) throws InvalidCommandException {
        if(remainingText == null){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be null or empty string");
        }

        remainingText = remainingText.trim();

        if(remainingText.equals("") || !remainingText.startsWith("\"")){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be empty string, and must start with a double quote(\")");
        }

        String[] splitText = remainingText.split("\"", 3);
        String nextToken = splitText[1];

        if(splitText.length == 3){
            remainingText = splitText[2];
        }else{
            remainingText = null;
        }

        if(nextToken == null || "".equals(nextToken)){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be null or empty string");
        }

        return nextToken;
    }


    public String getNextTokenInSingleQuotes(String tokenDescription) throws InvalidCommandException {
        if(remainingText == null){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be null or empty string");
        }

        remainingText = remainingText.trim();

        if(remainingText.equals("") || !remainingText.startsWith("'")){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be empty string, and must start with a single quote(')");
        }

        String[] splitText = remainingText.split("'", 3);
        String nextToken = splitText[1];

        if(splitText.length == 3){
            remainingText = splitText[2];
        }else{
            remainingText = null;
        }

        if(nextToken == null || "".equals(nextToken)){
            throw new InvalidCommandException(getInputCommand(), tokenDescription + " cannot be null or empty string");
        }

        return nextToken;
    }

    /**
     * This method ensures that the next token is among the given expected tokens.
     * It returns the actual token found.
     */
    public String ensureNextToken(String... expectedNextTokens) throws InvalidCommandException{
        String actualToken = getNextToken(expectedNextTokens[0]);

        for(String expectedNextToken : expectedNextTokens){
            if(expectedNextToken.equals(actualToken)){
                return actualToken;
            }
        }

        throw new InvalidCommandException(getInputCommand(), "Invalid argument: '" + actualToken+
                "', expected one of the following values: "+Arrays.asList(expectedNextTokens)+"");
    }

    public String getInputCommand() {
        return inputCommand;
    }

    public Integer getNextIntegerToken(String tokenDescription) throws InvalidCommandException {
        String nextTokenString = getNextToken(tokenDescription);

        Integer nextToken = 0;
        try{
            nextToken = Integer.parseInt(nextTokenString);
        }catch (NumberFormatException e){
            throw new InvalidCommandException(getInputCommand(), tokenDescription+": '"+ nextTokenString + "' is not an integer");
        }

        return nextToken;
    }

    public void ensureTermination() throws InvalidCommandException {
        if(remainingText != null && !"".equals(remainingText)){
            throw new InvalidCommandException(getInputCommand(), "Too many arguments: '"+remainingText+"'");
        }
    }

    public String safeGetNextToken() {
        try {
            return getNextToken("");
        } catch (InvalidCommandException e) {
            return null;
        }
    }
}
