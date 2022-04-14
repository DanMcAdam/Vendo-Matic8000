package com.techelevator;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger
{
    private static PrintWriter logWriter;
    private static File log = new File("/temp/Log.txt");


    public static void log(String message)
    {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/uuuu HH:mm:ss a ");
        String dateTime = LocalDateTime.now().format(formatter);
        try
        {
            if (log.createNewFile()) System.out.println("file created!");
            else System.out.println("file exists!");
        }
        catch (IOException error)
        {
            System.err.println("Log file could not be created");
        }
        if (logWriter == null)
        {
            try (PrintWriter logWriter = new PrintWriter (new FileOutputStream(log, true)))
            {
                logWriter.println(">" + dateTime + message + "\n");
            }
            catch (FileNotFoundException error)
            {
                System.err.println("Log file could not be found");
            }
        }
        else logWriter.println(">" + dateTime + message + "\n");
    }
}