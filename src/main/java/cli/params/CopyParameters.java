package cli.params;

import com.beust.jcommander.Parameter;

public class CopyParameters extends PadlockParameters{
    @Parameter(names = {"-k", "--key"},
            description = "The key of the password you wish to load",
            required = true)
    public String key;
}
