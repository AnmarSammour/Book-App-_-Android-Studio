package com.example.planetofbooks.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.planetofbooks.R;
import com.example.planetofbooks.adapter.BookAdapter;
import com.example.planetofbooks.apis.RetrofitClient;
import com.example.planetofbooks.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBookFragment extends Fragment {

    TextView txtbookname,txtAuthor,txtyear,txtDescription;
    ImageView imgbook;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_book_fragment,container,false);
        txtbookname = view.findViewById(R.id.txtbookname);
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtyear = view.findViewById(R.id.txtyear);
        txtDescription = view.findViewById(R.id.txtDescription);
        imgbook = view.findViewById(R.id.imgbook);

        Bundle bundle =this.getArguments();
        String dataNamebook =bundle.getString("Namebook");
        String dataNameauthor =bundle.getString("Nameauthor");
        String dataYear =bundle.getString("Year");
        String dataImgLink =bundle.getString("ImgLink");
        String dataDescription =bundle.getString("Description");

        txtbookname.setText(dataNamebook);
        txtAuthor.setText(dataNameauthor);
        txtyear.setText(dataYear);
        txtDescription.setText(dataDescription);


        Glide.with(this).load("http://10.0.2.2/books/img/"+dataImgLink)
                .apply(new RequestOptions().override(500,500))
                .error(R.drawable.notfound).into(imgbook);

        return view;
    }
}