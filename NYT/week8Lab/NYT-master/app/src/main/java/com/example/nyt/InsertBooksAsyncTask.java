package com.example.nyt;

import android.os.AsyncTask;

import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.Book;

import java.util.Arrays;

public class InsertBooksAsyncTask extends AsyncTask<Book, Integer, String> {
    // This is just a scaffold example for a task that would handle inserting books into the database.
    // You need to complete the doInBackground and onPostExecute methods.
    // Then you will need to make your own class for a task that handles retrieving Books from the
    // database.
    // Refer to the tutorial slide for more information.

    // We store a variable for an object that implements our interface, so we know that whatever
    // is in here, knows how to handle the result of our task.
    private AsyncTaskDelegate delegate;
    private AppDatabase db;


    // This asynctask will also need to be given a database instance, so it can perform database
    // queries. If your Room database class is named something else, change this.

    public void setDelegate(AsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }


    @Override
    protected String doInBackground(Book... books) {
        // Do some task here that would take a long time... for example, database queries
        // Also note: Book... books.
        //                  ^ the ... notation means it accepts either a single book or an array (or nothing).

        // TODO: Write your code to perform the task
        db.bookDao().insertAll(Arrays.asList(books));


        // When the task is finished, it will return.
        // You would normally want to return the result of your task.
        // For example, if my task was to get books from DB, I would make this method return the list
        // of books. The return value goes straight to onPostExecute.
        return "Insert statement completed";
    }

    @Override
    protected void onPostExecute(String result) {
        // Once doInBackground is completed, this method will run.
        // The "result" comes from doInBackground.

        // This is where we give our result to the delegate and let them handle it.
        // Our delegate should be the original Fragment/Activity, so then it can use the result to
        // update the UI, for example.

        // TODO: Call the delegate's method with the results
        delegate.handleTaskResult(result);
    }
    public void setDb(AppDatabase db){
        this.db = db;
    }

}
