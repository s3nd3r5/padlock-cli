package cli.params;

import com.beust.jcommander.DynamicParameter;

import java.util.HashMap;
import java.util.Map;

public class AddParameters extends PadlockParameters{
    @DynamicParameter(names = {"-k","--key-values"},
            description = "Update of passwords in the form: key=value")
    public Map<String, String> addKeyValuePairs = new HashMap<>();
}