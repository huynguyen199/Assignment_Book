package com.example.assignment_book.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_book.AsynTask.DeleteBook;
import com.example.assignment_book.AsynTask.UpdateBook;
import com.example.assignment_book.BookDetailsActivity;
import com.example.assignment_book.HandleJson.ApiService;
import com.example.assignment_book.HandleJson.RetroClient;
import com.example.assignment_book.Model.Book;
import com.example.assignment_book.Model.Categories;
import com.example.assignment_book.R;
import com.example.assignment_book.Response.CategoryEndpointResponse;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private ArrayList<Book> list_book;
    List<Categories> categories;

    private Context context;

    Spinner spinner;
    DialogPlus dialog;
    String id;
    ViewHolder viewHolder;

    public ViewHolder getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public ArrayList<Book> getList_book() {
        return list_book;
    }

    public void setList_book(ArrayList<Book> list_book) {
        this.list_book = list_book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BookAdapter(ArrayList<Book> list_book, Context context) {
        this.list_book = list_book;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        ViewHolder viewHolder =new BookAdapter.ViewHolder(view);
        setViewHolder(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {

        Book book = list_book.get(position);

        GetAllCategories(book);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailsActivity.class);
                Log.i("detail", "onClick: " + book.getId());
                intent.putExtra("id", list_book.get(position).getId());
                context.startActivity(intent);
            }
        });


        holder.tvName.setText(book.getName());


        //btn update
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                setId(book.getId());
            }
        });

        //btn delete
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i("hello", "onClick: " + book.getId());
                    DeleteBook deleteBook = new DeleteBook(context, book.getId());
                    deleteBook.execute();
                    list_book.remove(position);
                    notifyDataSetChanged();
                } catch (Exception e) {

                }

            }
        });
    }

    public void GetAllCategories(Book book) {
        ApiService apiService = RetroClient.getApiService();
        Call<CategoryEndpointResponse> call = apiService.listCategories();
        call.enqueue(new Callback<CategoryEndpointResponse>() {
            @Override
            public void onResponse(Call<CategoryEndpointResponse> call, Response<CategoryEndpointResponse> response) {
                CategoryEndpointResponse categoryEndpointResponse = response.body();
                categories = categoryEndpointResponse.getCategories();
                InitUpdateDialog(book,categories);
                Log.i("list", "onResponse: "+categories);
            }

            @Override
            public void onFailure(Call<CategoryEndpointResponse> call, Throwable t) {
                Log.d("fail", "onFailure: ");
            }
        });
    }

    private void InitUpdateDialog(Book book,List<Categories> list_categories) {
        dialog = DialogPlus.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_product))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        View view = dialog.getHolderView();
        EditText edtTitle = view.findViewById(R.id.edtTitle);
        EditText edtPrice = view.findViewById(R.id.edtPrice);
        EditText edtAuthors = view.findViewById(R.id.edtAuthors);
        EditText Edtname = view.findViewById(R.id.edtName);
        EditText edtImage = view.findViewById(R.id.edtImage);

        spinner = view.findViewById(R.id.spinner);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnClose = view.findViewById(R.id.btnClose);

        ArrayAdapter<Categories> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list_categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        btnAdd.setText("Cập nhật");




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getId();
                String title = edtTitle.getText().toString();
                String price = edtPrice.getText().toString();
                String authors = edtAuthors.getText().toString();
                String name = Edtname.getText().toString();
                String image = edtImage.getText().toString();
                Categories categories = (Categories) spinner.getSelectedItem();
                String categories_id = categories.getId().toString();

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("title", title);
                params.put("price", price);
                params.put("name", name);
                params.put("authors", authors);
                params.put("image", image);
                params.put("categories_id", categories_id);
                UpdateBook updateBook = new UpdateBook(context, params);
                updateBook.execute();

                viewHolder = getViewHolder();
                viewHolder.tvName.setText(name);

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        edtTitle.setText(book.getTitle());
//        edtPrice.setText(book.getPrice());
//        edtAuthors.setText(book.getAuthor());
//        Edtname.setText(book.getName());


    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);



    }

    @Override
    public int getItemCount() {
        return list_book.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout constraintLayout;
        private final TextView tvName;
        private final ImageButton btnDel, btnUpdate;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            constraintLayout = view.findViewById(R.id.constraint_layout);
            tvName = view.findViewById(R.id.tvName);
            btnDel = view.findViewById(R.id.btnDelete);
            btnUpdate = view.findViewById(R.id.btnUpdate);
        }


    }
}
