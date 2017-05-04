package info.openrpg.telegram;

import java.util.Arrays;
import java.util.Optional;

public class UserInput {
    private String command;
    private String[] arguments;

    public UserInput(String inputString) {
        this.arguments = Optional.of(inputString)
                .map(String::trim)
                .map(s -> s.split(" "))
                .map(arr -> {
                    this.command = arr[0];
                    return arr;
                })
                .filter(arr -> arr.length > 1)
                .map(arr -> Arrays.copyOfRange(arr, 1, arr.length))
                .orElse( null);
    }

    public String getCommand() {
        return command;
    }

    public boolean hasArguments() {
        return arguments != null;
    }

    public boolean hasArguments(int numberOfArguments) {
        return arguments.length >= numberOfArguments;
    }

    public String getArgument(int index) {
        return arguments[index-1];
    }

    public int size() {
        return arguments != null ? arguments.length : 0;
    }

}
