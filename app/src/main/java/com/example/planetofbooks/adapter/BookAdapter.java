package com.example.planetofbooks.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.planetofbooks.R;
import com.example.planetofbooks.SharedPref.SharedPrefManager;
import com.example.planetofbooks.activities.MainActivity;
import com.example.planetofbooks.activities.SignIn;
import com.example.planetofbooks.activities.SignUp;
import com.example.planetofbooks.apis.RetrofitFavInsert;
import com.example.planetofbooks.apis.RetrofitSignUp;
import com.example.planetofbooks.fragments.ViewBookFragment;
import com.example.planetofbooks.model.Book;
import com.example.planetofbooks.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>
{
    private List<Book> bookList;
    //the context object
    private Context mCtx;
    boolean flag = false;
    Fragment fragment;

    private static final String SHARED_PREF_NAME = "SharedPre";
    private static final String NUM = "num";


    public BookAdapter(Context context, List<Book> books,Fragment fragment){
        this.bookList = books;
        this.mCtx = context;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(bookList.get(position).getNamebook());
        Glide.with(mCtx).load("http://10.0.2.2/books/img/"+bookList.get(position).getImgLink())
                .apply(new RequestOptions().override(500,500))
                .error(R.drawable.notfound).into(holder.imageView);




        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("Namebook",bookList.get(position).getNamebook());
            bundle.putString("Nameauthor",bookList.get(position).getNameauthor());
            bundle.putString("Year",bookList.get(position).getYear());
            bundle.putString("ImgLink",bookList.get(position).getImgLink() );
            bundle.putString("Description",bookList.get(position).getDescription());

            AppCompatActivity activity =(AppCompatActivity) view.getContext();
            ViewBookFragment viewBookFragment = new ViewBookFragment();
            viewBookFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,viewBookFragment).commit();

        });



        holder.favIcon.setOnClickListener((view -> {
            bookList.get(position).getNamebook();


            if(holder.count.getText().equals("0"))
            {
                String email = SharedPrefManager.getInstance(fragment.getActivity()).getUsers().getEmail();
                insertFav(bookList.get(position).getNamebook().toString().trim() ,
                        bookList.get(position).getNameauthor().toString().trim()  ,
                        bookList.get(position).getYear().toString().trim() ,
                        bookList.get(position).getDescription().toString().trim()  ,
                        bookList.get(position).getImgLink().toString().trim()  ,
                        bookList.get(position).getPdfLink().toString().trim() ,
                        email.toString().trim()  ,
                        bookList.get(position).getCategories().toString().trim() );

                holder.count.setText("1");
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putString(NUM, holder.count.getText().toString());
                editor.apply();

                holder.favIcon.setImageResource(R.drawable.ic_fav_red);
            }
            else
            {
                holder.count.setText("0");
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putString(NUM, holder.count.getText().toString());
                editor.apply();


                holder.favIcon.setImageResource(R.drawable.ic_favorite);
            }


        }));

        //download pdf
        String getlink = ("http://10.0.2.2/books/img/"+bookList.get(position).getPdfLink());

        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getlink));
                String title = URLUtil.guessFileName(getlink,null,null);
                request.setTitle(title);
                request.setDescription("Downloading File please wait...");
                String cookie = CookieManager.getInstance().getCookie(getlink);
                request.addRequestHeader("cookie",cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
                DownloadManager downloadManager = (DownloadManager) mCtx.getSystemService(Context.DOWNLOAD_SERVICE);

                downloadManager.enqueue(request);

                Toast.makeText(mCtx.getApplicationContext(), "Downloading Started",Toast.LENGTH_SHORT).show();

            }
        });
    }//End onBindViewHolder


    private void insertFav(String namebook, String nameauthor, String year, String description, String imgLink, String pdfLink, String email, String categories) {


            //Here we will handle the http request to insert user to mysql db

            Call<Result> call = RetrofitFavInsert.getInstance().getMyApi().insertFav(namebook, nameauthor,year , description , imgLink , pdfLink , email ,categories);

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {


                    if (response.body().getError() == true) {

                        Log.d("something goes wrong --- > ", response.body().getMessage());
                        Toast.makeText(fragment.getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else if (response.body().getError() == false) {

                        Log.d("Response ---> ", "Added to favourites");

                        Toast.makeText(fragment.getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();



                    }


                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                    Log.d("Failed to Insert Data ---> ", t.getMessage());
                    Toast.makeText(fragment.getActivity(), "Failed to Insert Data --> " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public interface ItemClickListener
    {
        void onItemClickListener(Book book);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,count;
        ImageView imageView,favIcon,downloadBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bookname);
            imageView =itemView.findViewById(R.id.imageView2);
            favIcon = itemView.findViewById(R.id.favBtn);
            downloadBtn = itemView.findViewById(R.id.downloadBtn);
            count = itemView.findViewById(R.id.count);
        }
    }
}
