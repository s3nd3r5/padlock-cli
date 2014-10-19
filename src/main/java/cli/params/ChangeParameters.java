package cli.params;

import com.beust.jcommander.DynamicParameter;

import java.util.HashMap;
import java.util.Map;

public class ChangeParameters extends PadlockParameters{
    @DynamicParameter(names = {"-k","--key-values"},
            description = "Changes the key to a particular password")
    public Map<String, String> changeKeys = new HashMap<>();
}
