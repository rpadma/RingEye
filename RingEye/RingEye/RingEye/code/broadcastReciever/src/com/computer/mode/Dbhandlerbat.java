package com.computer.mode;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Rohit on 6/2/2014.
 */
public class Dbhandlerbat extends SQLiteOpenHelper {

    private static String DB_PATH="/data/data/com.computer.mode/databases/";
    private static String DB_NAME = "Ringmode.sqlite";
    private static String TABLENAME = "battery";
    private SQLiteDatabase myDataBase;
    int trainid ;
   private final Context myContext;
    private SQLiteDatabase db;
    Cursor c1,c2;
    String a[];

    public Dbhandlerbat(Context context) {
        super(context,DB_NAME,null,1);
        this.myContext=context;

    }

public int onCreateDatabase() throws IOException
{
    boolean dbExist=checkDatabase();
if(dbExist)
{
    return 0;
}
    else
{
System.out.println("onCreateDatabase started Excuting");
this.getReadableDatabase();
    copyDataBase();
    return 1;


}
}

private boolean checkDatabase()
{
    SQLiteDatabase checkDb=null;

try
{
    String myPath=DB_PATH+DB_NAME;
    checkDb=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
}
catch (Exception e)
{

}



    return checkDb!=null?true:false;
}

    private void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(DB_NAME);


        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);

        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
      myDataBase  = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public ArrayList<String> getModes(SQLiteDatabase db){
        System.out.println("$$$$$$$$$$$$########"+DB_NAME);
        System.out.println("$$$$$$$$$$$$########"+myDataBase);
        String P_query123=" SELECT * FROM battery ";

        ArrayList<String> list=new ArrayList<String>();
        try{
            Cursor c=db.rawQuery(P_query123, null);
            if(c.moveToFirst())
            {
                do{
                    list.add(c.getString(c.getColumnIndex("text")));
                }while(c.moveToNext());
            }
            c.close();
        }catch(SQLiteException se){
            myDataBase.close();


        }

        return list;


    }
}
