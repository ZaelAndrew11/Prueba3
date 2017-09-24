package cl.aguzman.prueba3.views.main.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.aguzman.prueba3.R;
import cl.aguzman.prueba3.adapters.ContentCallback;
import cl.aguzman.prueba3.adapters.ListMyTalesFavoritesAdapter;
import cl.aguzman.prueba3.views.ContentActivity;

public class ListFavoritesFragment extends Fragment implements ContentCallback {

    ListMyTalesFavoritesAdapter adapter;
    public ListFavoritesFragment() {
    }

    public static ListFavoritesFragment newInstance() {

        return new ListFavoritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView notFavTv = (TextView) view.findViewById(R.id.notFavoritesTv);
        new ListMyTalesFavoritesAdapter(this).checkFavorites(notFavTv);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.favirtesListTaleRv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        adapter = new ListMyTalesFavoritesAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getContent(String uid, String key) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    @Override
    public void dataChange() {}

}
