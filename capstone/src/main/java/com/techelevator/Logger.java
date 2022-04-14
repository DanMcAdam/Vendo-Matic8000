package com.techelevator;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger
{
    private static PrintWriter logWriter;
    private static final File log = new File(System.getProperty("user.dir")+"/capstone/Log.txt");


    public static void log(String message)
    {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/MM/uuuu HH:mm:ss a ");
        String timeStamp = LocalDateTime.now().format(dateTimeFormat);

        try
        {
            if(log.createNewFile());
        }
        catch (IOException error)
        {
            System.err.println("Log file could not be created");
        }

        if (logWriter == null)
        {
            try (PrintWriter logWriter = new PrintWriter (new FileOutputStream(log, true)))
            {
                logWriter.println(">" + timeStamp + message + "\n");
            }
            catch (FileNotFoundException error)
            {
                System.err.println("Log file could not be found");
            }
        }
        else logWriter.println(">" + timeStamp + message + "\n");
    }
}
