package main;

import cli.CommandLineRunner;
import main.params.MainCLIParameter;

import java.util.Arrays;

public class CommandLineMain {
    public static void main(String[] args) {
        try{
            if(null == args || args.length < 1){
                System.out.println(MainCLIParameter.getUsage());
                System.exit(-1);
            }

            MainCLIParameter mainParam = MainCLIParameter.valueOf(args[0].toLowerCase());
            if(args.length > 1){
                args = Arrays.copyOfRange(args,1,args.length);
            }
            switch(mainParam){
                case get:{
                    CommandLineRunner.get(args);
                    break;
                }
                case update:{
                    CommandLineRunner.update(args);
                    break;
                }
                case change:{
                    CommandLineRunner.change(args);
                    break;
                }
                case remove:{
                    CommandLineRunner.remove(args);
                    break;
                }
                case add:{
                    CommandLineRunner.add(args);
                    break;
                }
                case generate:{
                    CommandLineRunner.generate(args);
                    break;
                }
                default: System.out.println(MainCLIParameter.getUsage());
            }
        }catch(Exception e){
            System.out.println("Unable to process arguments: " + Arrays.toString(args));
            System.out.println(MainCLIParameter.getUsage());
        }
    }
}
