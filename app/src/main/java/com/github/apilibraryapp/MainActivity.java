package com.github.apilibraryapp;

import static com.github.apilibraryapp.BookDetailsActivity.EXTRA_BOOK_DETAILS;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.book_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchBooksData(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void fetchBooksData(String query) {
        String finalQuery = prepareQuery(query);
        BookService bookService = RetrofitInstance.getRetrofitInstance().create(BookService.class);

        Call<BookContainer> booksApiCall = bookService.findBooks(finalQuery);

        booksApiCall.enqueue(new Callback<BookContainer>() {
            @Override
            public void onResponse(Call<BookContainer> call, Response<BookContainer> response) {
                if(response.body() != null) {
                    setupBookListView(response.body().getBooks());
                }
            }

            @Override
            public void onFailure(Call<BookContainer> call, Throwable t) {
                Snackbar.make(findViewById(R.id.main_view), "Something went wrong... Please try again later!",
                        BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    private String prepareQuery(String query) {
        String[] queryParts =  query.split("\\s+");
        return TextUtils.join("+", queryParts);
    }

    private void setupBookListView(List<Book> books) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final BookAdapter bookAdapter = new BookAdapter();
        bookAdapter.setBookList(books);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class BookHolder extends RecyclerView.ViewHolder{


        private Book book;
        private final TextView bookTitleTextView;
        private final TextView bookAuthorTextView;
        private final TextView numberOfPagesTextView;
        private final ImageView bookCover;

        public BookHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.book_list_item, parent, false));

            bookTitleTextView = itemView.findViewById(R.id.book_title);
            bookAuthorTextView = itemView.findViewById(R.id.book_author);
            numberOfPagesTextView = itemView.findViewById(R.id.number_of_pages);
            bookCover = itemView.findViewById(R.id.img_cover);
        }

        public void bind(Book book) {
            if (book != null && checkNullOrEmpty(book.getTitle()) && book.getAuthors() != null) {
                this.book = book;
                bookTitleTextView.setText(book.getTitle());
                bookAuthorTextView.setText(TextUtils.join(", ", book.getAuthors()));
                numberOfPagesTextView.setText(book.getNumberOfPages());
                View itemContainer = itemView.findViewById(R.id.book_item_container);
                itemContainer.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                    intent.putExtra(EXTRA_BOOK_DETAILS, book);
                    startActivity(intent);
                });
                if(book.getCover() != null) {
                    Picasso.with(itemView.getContext())
                            .load(IMAGE_URL_BASE + book.getCover() + "-S.jpg")
                            .placeholder(R.drawable.ic_book).into(bookCover);
                }
                else {
                    bookCover.setImageResource(R.drawable.ic_book);
                }
            }
        }

        private boolean checkNullOrEmpty(String str) {
            return !(str == null && str.isEmpty());
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {

        private List<Book> bookList;


        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            if (bookList != null) {
                Book book = bookList.get(position);
                holder.bind(book);
            } else {
                String LOG_TAG = "MainActivity";
                Log.d(LOG_TAG, "No books");
            }
        }

        @Override
        public int getItemCount() {
            return bookList == null ?
                    0 : bookList.size();
        }

        public void setBookList(List<Book> bookList) {
            this.bookList = bookList;
            notifyDataSetChanged();
        }
    }
}