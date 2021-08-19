package com.example.assignment_book.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_book.Adapter.BookAdapter;
import com.example.assignment_book.Adapter.categoriesAdapter;
import com.example.assignment_book.AsynTask.GetBook;
import com.example.assignment_book.AsynTask.GetCategories;
import com.example.assignment_book.AsynTask.PostBook;
import com.example.assignment_book.Https.HttpHeaders;
import com.example.assignment_book.Model.Book;
import com.example.assignment_book.Model.Categories;
import com.example.assignment_book.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BottomNavigationView bottomnavigation;


    private RecyclerView recyler_categories, recyler_book;
    ImageView imgInsert;
    EditText edtSearch;

    GetBook getBook;
    GetCategories getCategories;

    private ArrayList<Categories> list_categories;
    private ArrayList<Book> BookList;

    //adapter
    categoriesAdapter categoriesAdapter;
    BookAdapter adapter;


    Spinner spinner;
    DialogPlus dialog;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        insertArrays();
        initViewFindId(view);
        InitInsertDialog();

        customRecyclerView();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        imgInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

//        ((BottomNavigationView)getActivity().findViewById(R.id.bottom_navigation)).setSelectedItemId(R.id.navigation_profile);
//        edttext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//
//
//                }
//            }
//        });


//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
////                    showInputMethod(view.findFocus());
//                }
//            }
//        });
    }
    private void filter(String s) {
        ArrayList<Book> filterlist = new ArrayList<>();
        Log.i("test", "filter: "+BookList.size());
        for(Book book : BookList){
            if(book.getName().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(book);

            }
        }
        adapter = getBook.getAdapter();
        adapter.setList_book(filterlist);
        recyler_book.setAdapter(adapter);
    }


    private void InitInsertDialog() {
        dialog = DialogPlus.newDialog(getContext())
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.dialog_product))
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





        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String price = edtPrice.getText().toString();
                String authors = edtAuthors.getText().toString();
                String name = Edtname.getText().toString();
                String image = edtImage.getText().toString();
                Categories categories = (Categories) spinner.getSelectedItem();
                String categories_id = categories.getId().toString();

                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("price", price);
                params.put("name", name);
                params.put("authors", authors);
                params.put("image", image);

                params.put("categories_id", categories_id);

                PostBook postBook = new PostBook(getContext(), params);
                postBook.execute();

                getBook = new GetBook(recyler_book, getContext());
                getBook.execute(HttpHeaders.GET);
            }
        });


    }
    private void displayCategoriesData(Categories categories) {
        String id = categories.getId();
        Log.i("test", "displayCategoriesData: "+id);
    }

    //model book , categories
    private void insertArrays() {
        list_categories = new ArrayList<Categories>();
//        list_categories.add(new Categories("213", "dsad"));
//        list_categories.add(new Categories("2123", "dsad21"));
//        list_categories.add(new Categories("2122113", "dsa12d"));
//        list_categories.add(new Categories("21213", "dsa12d"));
//        list_categories.add(new Categories("22113", "dsa12d"));
//        list_categories.add(new Categories("21213", "dsa12d"));
//        list_categories.add(new Categories("21213", "dsa12d"));
//        list_categories.add(new Categories("21123", "dsa12d"));
//
//        list_book = new ArrayList<Book>();
//        list_book.add(new Book("21","sdasdsa","csacas",20,"dsads","image01","dsadsad"));
//        list_book.add(new Book("21","sdasdsa","csacas",20,"dsads","image01","dsadsad"));
//        list_book.add(new Book("21","sdasdsa","csacas",20,"dsads","image01","dsadsad"));
//        list_book.add(new Book("21","sdasdsa","csacas",20,"dsads","image01","dsadsad"));
//        list_book.add(new Book("21","sdasdsa","csacas",20,"dsads","image01","dsadsad"));
//        list_book.add(new Book("21","sdasdsa","csacas",20,"dsads","image01","dsadsad"));


    }

    //handle recylerview ,arraylist categories,book
    private void customRecyclerView() {
        //categories
        categoriesAdapter = new categoriesAdapter(list_categories, getContext());
        recyler_categories.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyler_categories.setLayoutManager(layoutManager);
        recyler_categories.setAdapter(categoriesAdapter);


        //book


        //get all categories and spinner categories
        getCategories = new GetCategories(recyler_categories, spinner, getContext());
        getCategories.execute();

        getBook = new GetBook(recyler_book, getContext());
        getBook.setGetCategories(getCategories);
        getBook.execute(HttpHeaders.GET);
        BookList = getBook.getBookList();

//        bookAdapter = new BookAdapter(list_book,getContext());
//        recyler_book.setHasFixedSize(true);
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
//        recyler_book.setLayoutManager(layoutManager2);
//        recyler_book.setAdapter(bookAdapter);


    }


    private void initViewFindId(View view) {
        recyler_categories = view.findViewById(R.id.recyler_categories);
        recyler_book = view.findViewById(R.id.recyler_book);
        imgInsert = view.findViewById(R.id.imgAdd);
        edtSearch = view.findViewById(R.id.edtSearch);
    }

    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }
}