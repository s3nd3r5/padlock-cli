package cli.params;

import com.beust.jcommander.Parameter;

public abstract class PadlockParameters {
    @Parameter(names = {"-f", "--file"},
            description = "The data file containing your passwords")
    public String fileName = System.getenv("PADLOCK_FILE");

    @Parameter(names = {"-p","--password"},
            description = "The password to the file you're reading from",
            password = true)
    public String password = System.getenv("PADLOCK_PASSWORD");

    @Parameter(names = {"-s","--secret"},
            description = "The secret to file you're reading from",
            password = true)
    public String secret = System.getenv("PADLOCK_SECRET");

}
