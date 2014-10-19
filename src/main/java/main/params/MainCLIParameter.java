package main.params;

public enum MainCLIParameter {
    get("Retrieves passwords from a provided key"),
    update("Updates passwords for provided keys"),
    add("Adds passwords for provided keys"),
    change("Changes the keys for provided keys"),
    generate("Generates a password for a given charset and length"),
    remove("Removes the passwords for provided keys");

    private final String description;
    private MainCLIParameter(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;

    }
    private static final String USAGE;
    static{
        String usage_str = "Usage: padlock-cli";
        usage_str += "\n\tOptions:";
        for(MainCLIParameter param : MainCLIParameter.values()){
            usage_str+="\n\t\t" + param.name();
            usage_str+="\n\t\t\t" + param.getDescription();
        }
        USAGE=usage_str;
    }

    public static String getUsage(){
        return USAGE;
    }
}
