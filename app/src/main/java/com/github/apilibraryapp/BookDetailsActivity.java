package com.github.apilibraryapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class BookDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_DETAILS = "EXTRA_BOOK_DETAILS";
    private ImageView bookCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        TextView bookTitleTextView = findViewById(R.id.book_title);
        TextView bookAuthorTextView = findViewById(R.id.book_author);
        TextView bookSubtitleTextView = findViewById(R.id.book_subtitle);
        TextView bookWeightTextView = findViewById(R.id.book_weight);
        TextView bookLangTextView = findViewById(R.id.book_languages);

        Book book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK_DETAILS);


        bookTitleTextView.setText(book.getTitle());
        bookAuthorTextView.setText(TextUtils.join(", ", book.getAuthors()));
        bookLangTextView.setText(TextUtils.join(", ", book.getLanguages()));
        bookSubtitleTextView.setText(book.getSubtitle());
        System.out.println(book.getSubtitle());
        System.out.println(book.getWeight());
        System.out.println(book.getLanguages());
        bookWeightTextView.setText(book.getWeight());

//        if (book.getCover() != null) {
//            Picasso.with(getApplicationContext())
//                    .load(IMAGE_URL_BASE + book.getCover() + "-L.jpg")
//                    .placeholder(R.drawable.ic_book).into(bookCover);
//        } else {
//            bookCover.setImageResource(R.drawable.ic_book);
//        }

    }
}