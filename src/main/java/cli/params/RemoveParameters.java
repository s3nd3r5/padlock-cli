package cli.params;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class RemoveParameters extends PadlockParameters{
    @Parameter(names = {"-k","--keys"},
            description = "List the keys you wish to remove",
            variableArity = true)
    public List<String> removeKeys = new ArrayList<>();
}
