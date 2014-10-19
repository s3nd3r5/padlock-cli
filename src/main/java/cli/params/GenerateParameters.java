package cli.params;

import com.beust.jcommander.Parameter;
import com.senders.padlock.api.constants.CharacterSet;

public class GenerateParameters{
    @Parameter(names={"-ch","--character-set"},
            description = "The character set you wish to generate your passwords from")
    public String characterSet = CharacterSet.PRINTABLE.name();
    @Parameter(names={"-l","--length"},
            description = "The length of the password you wish to generate",
            required = true)
    public int length;

    @Parameter(names={"-sc","--set-case"},
            description = "Sets the case of the passwords: Upper, Lower, Random")
    public String setCase = "Random";

}
