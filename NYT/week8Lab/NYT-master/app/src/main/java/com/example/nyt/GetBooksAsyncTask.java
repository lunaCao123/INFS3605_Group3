package com.example.nyt;

import android.os.AsyncTask;

import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.Book;

import java.util.List;

public class GetBooksAsyncTask extends AsyncTask<Void,Integer, List<Book>> {
    private AsyncTaskDelegate delegate;
    private AppDatabase db;
    private List<Book>newBookList;

    public void setDelegate(AsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }

    protected void onPostExecute(List<Book> result) {
        // Once doInBackground is completed, this method will run.
        // The "result" comes from doInBackground.

        // This is where we give our result to the delegate and let them handle it.
        // Our delegate should be the original Fragment/Activity, so then it can use the result to
        // update the UI, for example.

        // TODO: Call the delegate's method with the results
        delegate.handleTaskResponse(result);
    }
    public void setDb(AppDatabase db){
        this.db = db;
    }
    @Override
    protected List<Book> doInBackground(Void... voids) {
        newBookList = db.bookDao().getAll();
        return newBookList;
    }
}
