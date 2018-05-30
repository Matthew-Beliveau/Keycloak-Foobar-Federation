package org.keycloak.examples.userstorage.foobar;

import org.keycloak.models.UserModel;
import org.keycloak.models.utils.UserModelDelegate;
import org.keycloak.storage.ReadOnlyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class WritableFOOBARUserModelDelegate extends UserModelDelegate implements UserModel {

    private final FOOBARUserStorageProvider provider;
    protected Properties properties;

    public WritableFOOBARUserModelDelegate(UserModel delegate, FOOBARUserStorageProvider provider){
        super(delegate);
        this.provider = provider;
    }

    @Override
    public void setUsername(String username){
        //I don't think I can change the username from the CLI
        throw new ReadOnlyException("Federated storage can't write username");
    }


    @Override
    public void setLastName(String lastName){
        try{
            System.out.println("It runs\n");
            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    "ipa user-mod " + properties.getProperty(delegate.getUsername()) + " --last=" + lastName
            }; //needs a username
            Runtime rt = Runtime.getRuntime();

            Process p = rt.exec(cmd);

            showOutPut(p);
        }
        catch(java.io.IOException e){
            System.out.println(e.getMessage());
        }
        catch(java.lang.InterruptedException e){
            System.out.println(e.getMessage());
        }

    }



    @Override
    public void setFirstName(String firstName) {
        try{
            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    "ipa user-mod " + properties.getProperty(delegate.getUsername()) + " --first=" + firstName
            }; //needs a username
            Runtime rt = Runtime.getRuntime();

            Process p = rt.exec(cmd);

            showOutPut(p);
        }
        catch(java.io.IOException e){
            System.out.println(e.getMessage());
        }
        catch(java.lang.InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setEmail(String email) {
        try{
            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    "ipa user-mod " + properties.getProperty(delegate.getUsername()) + " --email=" + email
            }; //needs a username
            Runtime rt = Runtime.getRuntime();

            Process p = rt.exec(cmd);

            showOutPut(p);
        }
        catch(java.io.IOException e){
            System.out.println(e.getMessage());
        }
        catch(java.lang.InterruptedException e){
            System.out.println(e.getMessage());
        }
    }


    private static void showOutPut(Process p) throws java.io.IOException, InterruptedException {
        p.waitFor();
        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String s = null;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
        is.close();

    }


}
