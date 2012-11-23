package com.arena3.cor.photomail;

import java.io.*;
import java.text.DateFormat;
import java.util.*;
import javax.mail.*;

import com.sun.mail.util.BASE64DecoderStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class ReadMail extends AsyncTask<Void, Void, Void> {
    String host = "imap.gmail.com";
    String user = "cloaked.octo.robot@gmail.com";
    String password = "0c7oRobot!";
    
    Context _context = null;
    
    ProgressDialog mProgressDialog = null;
    
    public ReadMail (Context context)
    {
        _context = context;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(_context, "Loading...", "Data is Loading...");
    }
    
    @Override
    protected Void doInBackground(Void... params) {
        Properties props = System.getProperties();
        Session session = Session.getDefaultInstance(props); 
    
        try
        {
            Store store = session.getStore("imaps");
            store.connect(host, user, password);
            
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);
            
            Message messages[] = inbox.getMessages();
            
            for (Message message : messages) {
                
                String subject = message.getSubject();
                int msgNum = message.getMessageNumber();     
                Log.d("MSG", "SUBJECT: " + subject + " NUM: " + Integer.valueOf(msgNum).toString());
                if(subject.compareToIgnoreCase("COR Image") == 0)
                {
                    Log.d("COR", "Found COR Email");
                
                    handleContent(message.getContentType(), message.getContent(), message.getSize());
                    //message.setFlag(Flags.Flag.DELETED, true);
                }
            }
            
            inbox.close(true);
            store.close();
    
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            mProgressDialog.dismiss();
        }

        return null;
    }

    private void handleContent(String contentType, Object content, int contentSize) {
        
        if(contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart"))
        {
            Multipart multipart = (Multipart)content;
            try {
                for(int i = 0; i < multipart.getCount(); i ++)
                {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    
                    try {
                        handleContent(bodyPart.getContentType(), bodyPart.getContent(), bodyPart.getSize());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        else if(contentType.toLowerCase(Locale.ENGLISH).startsWith("image"))    
        {
            BASE64DecoderStream ds = (BASE64DecoderStream)content;
            File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "cor");
            
            storageDir.mkdirs();
            
            int index = contentType.toLowerCase(Locale.ENGLISH).indexOf("name=");
            
            if(index >= 0)
            {
                String[] tokens = contentType.substring(index+5).trim().split("\\.(?=[^\\.]+$)");
                Log.d("TOK", tokens[0] + "." + tokens[1] );
                try {
                    File imageFile = File.createTempFile(tokens[0], "." + tokens[1], storageDir);
                    Date start = new Date();
                    
                    Log.d("STOR", imageFile.getAbsolutePath() + " " + DateFormat.getDateTimeInstance().format(start));
                    
                    OutputStream os = new FileOutputStream(imageFile);
                    byte[] buffer = new byte[4096];
                    
                    while((ds.read(buffer)) > 0)
                    {
                        os.write(buffer);
                    }
                    
                    Log.d("STOR", "complete" + " " + (new Date().getTime() - start.getTime())/60000);
                    
                    MediaScannerConnection.scanFile(_context, new String[] {imageFile.getAbsolutePath()}, null, null);
                    
                    Log.d("SCAN", "requested" + " " + (new Date().getTime() - start.getTime())/60000);
                    
                    os.flush();
                    os.close();
                    ds.close();
                    buffer = null;
                    imageFile = null;
                    
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        }
    }
}
