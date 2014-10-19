package cli;

import cli.params.*;
import com.beust.jcommander.JCommander;
import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.senders.padlock.api.DefaultPadlockModule;
import com.senders.padlock.api.Padlock;
import com.senders.padlock.api.constants.CharacterSet;
import com.senders.padlock.api.managers.ClipboardManager;
import com.senders.padlock.api.managers.SystemClipboardManager;
import com.senders.padlock.api.services.RandomStringService;
import com.senders.padlock.api.services.RandomStringServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

    private static Padlock buildPadlock(String password, String secret, String fileName){
        Preconditions.checkArgument(StringUtils.isNotBlank(fileName),
                "Filename is not provided - use the '-f, --file' flag " +
                        "or set a file in your environment using the PADLOCK_FILE variable");
        Preconditions.checkArgument(StringUtils.isNotBlank(password),"A password to your file must be provided (cannot be blank) " +
                "-use the flag '-p, --password' or provide a password using the PADLOCK_PASSWORD variable");
        Preconditions.checkArgument(StringUtils.isNotBlank(secret), "You must provide a secret for the file - use the '-s, --secret' flag " +
                "or set a secret in your environment using the PADLOCK_SECRET variable (hex string)");
        BigInteger secretInt = new BigInteger(secret,16);
        Injector injector = Guice.createInjector(new DefaultPadlockModule(password, secretInt));
        Padlock padlock = injector.getInstance(Padlock.class);
        padlock.loadPasswords(fileName);
        return padlock;
    }

    public static void update(String[] args){
        UpdateParameters params = new UpdateParameters();
        JCommander commander = new JCommander(params);
        try{
            commander.parse(args);
            Preconditions.checkArgument(StringUtils.isNotBlank(params.fileName),
                    "Filename is not provided - use the '-f, --file' flag " +
                            "or set a file in your environment using the PADLOCK_FILE variable");

            Padlock padlock = buildPadlock(params.password,params.secret,params.fileName);

            params.updateKeyValuePairs
                    .keySet()
                    .stream()
                    .filter(key -> StringUtils.isNotBlank(params.updateKeyValuePairs.get(key))
                            && padlock.getKeys().contains(key))
                    .forEach(key -> {
                        String value = params.updateKeyValuePairs.get(key);
                        padlock.updatePassword(key, value);
                    });
            padlock.savePasswords(params.fileName);
        }catch(Exception e){
            logger.warn("Unable to execute update",e);
            commander.usage();
            throw new RuntimeException("Cannot update password");
        }
    }
    public static void add(String[] args){
        AddParameters params = new AddParameters();
        JCommander commander = new JCommander(params);
        try{
            commander.parse(args);
            Padlock padlock = buildPadlock(params.password,params.secret,params.fileName);
            params.addKeyValuePairs
                    .keySet()
                    .stream()
                    .filter(key -> StringUtils.isNotBlank(params.addKeyValuePairs.get(key))
                            && !padlock.getKeys().contains(key))
                    .forEach(key -> {
                        String value = params.addKeyValuePairs.get(key);
                        padlock.addPassword(key, value);
                    });
            padlock.savePasswords(params.fileName);
        }catch(Exception e){
            logger.warn("Unable to execute add",e);
            commander.usage();
            throw new RuntimeException("Cannot add password");
        }
    }

    public static void change(String[] args){
        ChangeParameters params = new ChangeParameters();
        JCommander commander = new JCommander(params);
        try{
            commander.parse(args);
            Padlock padlock = buildPadlock(params.password,params.secret,params.fileName);
            params.changeKeys
                    .keySet()
                    .stream()
                    .filter(key -> StringUtils.isNotBlank(params.changeKeys.get(key))
                            && padlock.getKeys().contains(key))
                    .forEach(key -> {
                        String value = params.changeKeys.get(key);
                        padlock.updateKey(key, value);
                    });
            padlock.savePasswords(params.fileName);
        }catch(Exception e){
            logger.warn("Unable to execute change",e);
            commander.usage();
            throw new RuntimeException("Unable to change keys");
        }
    }

    public static void get(String[] args){
        CopyParameters params = new CopyParameters();
        JCommander commander = new JCommander(params);
        try{
            commander.parse(args);
            Padlock padlock = buildPadlock(params.password,params.secret,params.fileName);

            logger.debug(padlock.getKeys().toString());
            if(!padlock.getKeys().contains(params.key)) throw new RuntimeException("Key is not found");
            padlock.copyPassword(params.key);
        }catch(Exception e){
            logger.warn("Unable to execute get",e);
            commander.usage();
            throw new RuntimeException("Cannot get password");
        }
    }

    public static void remove(String[] args){
        RemoveParameters params = new RemoveParameters();
        JCommander commander = new JCommander(params);
        try{
            commander.parse(args);
            Padlock padlock = buildPadlock(params.password,params.secret,params.fileName);
            params.removeKeys
                    .stream()
                    .filter(key -> padlock.getKeys().contains(key))
                    .forEach(padlock::deletePassword);
            padlock.savePasswords(params.fileName);
        }catch(Exception e){
            logger.warn("Unable to execute remove",e);
            commander.usage();
            throw new RuntimeException("Unable to remove passwords for provided keys");
        }
    }

    public static void generate(String[] args){
        GenerateParameters params = new GenerateParameters();
        JCommander commander = new JCommander(params);
        try{
            commander.parse(args);
            RandomStringService randomStringService = new RandomStringServiceImpl();
            CharacterSet charSet =  CharacterSet.valueOf(params.characterSet);
            String password = randomStringService
                    .generateRandomString(params.length,charSet);
            switch(params.setCase.trim().toLowerCase()){
                case "upper": password = password.toUpperCase(); break;
                case "lower": password = password.toLowerCase(); break;
                default: break;
            }
            System.out.println(password);
            ClipboardManager clipboardManager = new SystemClipboardManager();
            clipboardManager.copy(password);
        }catch(Exception e){
            logger.warn("Unable to generate passwords",e);
            commander.usage();
            throw new RuntimeException("Unable to generate passwords for params");
        }
    }

}
