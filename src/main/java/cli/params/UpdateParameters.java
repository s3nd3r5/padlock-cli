package cli.params;

import com.beust.jcommander.DynamicParameter;

import java.util.HashMap;
import java.util.Map;

public class UpdateParameters extends PadlockParameters{
    @DynamicParameter(names = {"-k","--key-values"},
            description = "Update passwords in the form: key=value")
    public Map<String, String> updateKeyValuePairs = new HashMap<>();
}
